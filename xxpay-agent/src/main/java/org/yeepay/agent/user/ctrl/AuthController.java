package org.yeepay.agent.user.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.agent.common.ctrl.BaseController;
import org.yeepay.agent.secruity.JwtAuthenticationRequest;
import org.yeepay.agent.secruity.JwtTokenUtil;
import org.yeepay.agent.user.service.UserService;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.annotation.MethodLog;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.domain.BizResponse;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.common.util.CookieUtil;
import org.yeepay.core.common.util.MD5Util;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.common.util.RandomValidateCodeUtil;
import org.yeepay.core.entity.AgentInfo;
import org.yeepay.core.entity.MchInfo;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(Constant.AGENT_CONTROLLER_ROOT_PATH)
@RestController
public class AuthController extends BaseController {

    @Value("${jwt.cookie}")
    private String tokenCookie;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String SMS_VERIFY_CODE = "SMS_VERIFY_CODE";

    private static Map<String, Integer> mobileSendMap = new HashMap<>();

    private static final MyLog _log = MyLog.getLog(AuthController.class);

    /**
     * ????????????
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/auth")
    @MethodLog( remark = "??????" )
    public ResponseEntity<?> authToken(HttpServletRequest request,
            HttpServletResponse response) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        String username = getStringRequired(param, "username");
        String password = getStringRequired(param, "password");
        JwtAuthenticationRequest authenticationRequest = new JwtAuthenticationRequest(username, password);
        String token;
        try {
           token = userService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        }catch (ServiceException e) {
            return ResponseEntity.ok(BizResponse.build(e.getRetEnum()));
        }

        AgentInfo agentInfo = userService.findByLoginName(username);
        JSONObject data = new JSONObject();
        data.put("access_token", token);
        data.put("agentId", agentInfo.getAgentId());
        data.put("loginSecurityType", agentInfo.getLoginSecurityType());
        return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
    }

    /**
     * ????????????
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/google_auth")
    public ResponseEntity<?> authGoogle(HttpServletRequest request,
                                        HttpServletResponse response) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        Long agentId = getLongRequired(param, "agentId");
        Long googleCode = getLongRequired(param, "googleCode");
        // ????????????
        AgentInfo agentInfo = userService.findByAgentId(agentId);
        if(agentInfo == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_AGENT_NOT_EXIST));
        }
        if(MchConstant.PUB_YES != agentInfo.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_AGENT_STATUS_STOP));
        }
        // ?????????????????????
        boolean checkResult = checkGoogleCode(agentInfo.getGoogleAuthSecretKey(), googleCode);
        if(!checkResult) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_GOOGLECODE_NOT_MATCH));
        }
        return ResponseEntity.ok(YeePayResponse.buildSuccess());
    }

    /**
     * ????????????(???????????????????????????????????????)
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/mgr_auth")
    public ResponseEntity<?> mgrAuthToken(HttpServletRequest request,
                                       HttpServletResponse response) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        Long agentId = getLongRequired(param, "agentId");
        String token = getStringRequired(param, "token");

        AgentInfo agentInfo = userService.findByAgentId(agentId);
        if(agentInfo == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_AGENT_NOT_EXIST));
        }
        if(MchConstant.PUB_YES != agentInfo.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_AGENT_STATUS_STOP));
        }

        // ?????????????????????????????????token,????????????
        // ?????????ID+????????????+?????? ???32???MD5???????????????
        String password = agentInfo.getPassword();
        String secret = "Abc%$G&!!!128G";
        String rawToken = agentId + password + secret;
        String myToken = MD5Util.string2MD5(rawToken).toUpperCase();
        if(!myToken.equals(token)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_ILLEGAL_LOGIN));
        }

        // ??????jwtToken??????
        String jwtToken = jwtTokenUtil.generateToken(agentId, String.valueOf(agentId));
        JSONObject data = new JSONObject();
        data.put("access_token", jwtToken);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
    }

    /**
     * ??????token
     * @param request
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/refresh")
    public ResponseEntity<?> refreshToken(
            HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String token = CookieUtil.getCookieByName(request, tokenCookie);
        String refreshedToken;
        try {
            refreshedToken = userService.refreshToken(token);
        }catch (ServiceException e) {
            return ResponseEntity.ok(BizResponse.build(e.getRetEnum()));
        }
        if(refreshedToken == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_OPERATION_FAIL));
        } else {
            JSONObject data = new JSONObject();
            data.put("access_token", token);
            // ??????cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setDomain("yeepay.org");
            cookie.setMaxAge(expiration);// ???
            response.addCookie(cookie);
            return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
        }
    }

    /**
     * ???????????????
     */
    @RequestMapping(value = "/auth/auth_code_get")
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map randomMap = RandomValidateCodeUtil.getRandcode(120, 40, 6, 20);
        // TODO ??????????????????,????????????
        String randomString = randomMap.get("randomString").toString();
        BufferedImage randomImage = (BufferedImage)randomMap.get("randomImage");
        ImageIO.write(randomImage, "JPEG", response.getOutputStream());
    }

}
