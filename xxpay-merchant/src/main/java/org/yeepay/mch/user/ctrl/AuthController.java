package org.yeepay.mch.user.ctrl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import me.chanjar.weixin.common.util.crypto.PKCS7Encoder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.annotation.MethodLog;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.domain.BizResponse;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.common.util.*;
import org.yeepay.core.entity.EmpMch;
import org.yeepay.core.entity.MchInfo;
import org.yeepay.mch.common.ctrl.BaseController;
import org.yeepay.mch.common.util.AliSmsUtil;
import org.yeepay.mch.secruity.JwtAuthenticationRequest;
import org.yeepay.mch.secruity.JwtTokenUtil;
import org.yeepay.mch.user.service.EmpMchService;
import org.yeepay.mch.user.service.UserService;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.*;

@RequestMapping(Constant.MCH_CONTROLLER_ROOT_PATH)
@RestController
public class AuthController extends BaseController {

    @Value("${jwt.cookie}")
    private String tokenCookie;

    @Value("${jwt.expiration}")
    private Integer expiration;

    @Autowired
    private UserService userService;
    @Autowired
    private EmpMchService empMchService;



    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private static final String SMS_VERIFY_CODE = "SMS_VERIFY_CODE";

    private static Map<String, Integer> mobileSendMap = new HashMap<>();

    private static final MyLog _log = MyLog.getLog(AuthController.class);

    /**
     * 登录鉴权
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/auth")
    @MethodLog( remark = "登录" )
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

        MchInfo mchInfo = userService.findByLoginName(username);

        // 判断IP是否允许登录
        String clintIp = IPUtility.getClientIp(request);
        boolean isAllow = YeePayUtil.ipAllow(clintIp, mchInfo.getLoginWhiteIp(), mchInfo.getLoginBlackIp());
        if(!isAllow) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_IP_NOT_LOGIN));
        }

        JSONObject data = new JSONObject();
        data.put("access_token", token);
        data.put("mchId", mchInfo.getMchId());
        data.put("loginSecurityType", mchInfo.getLoginSecurityType());
        return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
    }

    /**
     * 登录鉴权
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/google_auth")
    public ResponseEntity<?> authGoogle(HttpServletRequest request,
                                       HttpServletResponse response) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        Long mchId = getLongRequired(param, "mchId");
        Long googleCode = getLongRequired(param, "googleCode");
        // 判断商户
        MchInfo mchInfo = userService.findByMchId(mchId);
        if(mchInfo == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }
        if(MchConstant.PUB_YES != mchInfo.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_STOP));
        }
        // 验证谷歌验证码
        boolean checkResult = checkGoogleCode(mchInfo.getGoogleAuthSecretKey(), googleCode);
        if(!checkResult) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_GOOGLECODE_NOT_MATCH));
        }
        return ResponseEntity.ok(YeePayResponse.buildSuccess());
    }

    /**
     * 登录鉴权(运营平台登录商户系统鉴权)
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/mgr_auth")
    public ResponseEntity<?> mgrAuthToken(HttpServletRequest request,
                                       HttpServletResponse response) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        Long mchId = getLongRequired(param, "mchId");
        String token = getStringRequired(param, "token");

        MchInfo mchInfo = userService.findByMchId(mchId);
        if(mchInfo == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }
        if(MchConstant.PUB_YES != mchInfo.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_STOP));
        }

        // 先校验运营平台传过来的token,是否合法
        // 将商户ID+商户密码+密钥 做32位MD5加密转大写
        String password = mchInfo.getPassword();
        String secret = "Abc%$G&!!!128G";
        String rawToken = mchId + password + secret;
        String myToken = MD5Util.string2MD5(rawToken).toUpperCase();
        if(!myToken.equals(token)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_ILLEGAL_LOGIN));
        }

        // 生成jwtToken返回
        String jwtToken = jwtTokenUtil.generateToken(mchId, String.valueOf(mchId));
        JSONObject data = new JSONObject();
        data.put("access_token", jwtToken);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
    }

    /**
     * 员工登录鉴权(运营平台登录商户系统鉴权)
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/emp_auth")
    public ResponseEntity<?> empAuthToken(HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        JSONObject param = getJsonParam(request);
        String code = URLDecoder.decode(getStringRequired(param, "code"),"UTF-8");
        _log.info(code);
        String paramJson = decryptAES(code);
        if (null == paramJson) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }
        JSONObject paramCode = JSONObject.parseObject(paramJson);
        if (null == paramCode.get("orgId")) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }

        EmpMch empMch = empMchService.findByEmpId(Long.parseLong(paramCode.get("orgId").toString()));
        if(empMch == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }
        MchInfo mchInfo = userService.findByMchId(empMch.getMchId());
        if(mchInfo == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_SERVICE_MCH_NOT_EXIST));
        }
        if(MchConstant.PUB_YES != mchInfo.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_STOP));
        }

        // 生成jwtToken返回
        String jwtToken = jwtTokenUtil.generateToken(mchInfo.getMchId(), String.valueOf(mchInfo.getMchId()));
        JSONObject data = new JSONObject();
        data.put("access_token", jwtToken);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
    }

 public static void main(String[] args) throws Exception {

     String a = encrypt("qwertyuiopasdfgh");
     _log.info(a);
     String b = decrypt(a);
     _log.info(b);
     String c = encryptAES("asdfve");
     _log.info(c);
     String d = decryptAES(c);
     _log.info(d);
     String e = decryptAES("2/SFtKz0VYUfo1Fz3leJlg==");
     _log.info(e);


//     Long empId = Long.parseLong(a);
}
    /**
     * 对密文进行解密.
     *
     * @param content 需要解密的密文
     * @return 解密得到的明文
     */
    static String decrypt(String content) {
        String aesKey = "asdfghjklzxcvbnm";

        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(aesKey.getBytes()));
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
//            secureRandom.setSeed(aesKey.getBytes());
//            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = Base64.decodeBase64(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对密文进行加密.
     *
     * @param content 需要解密的密文
     * @return 解密得到的明文
     */
    static String encrypt(String content) {
        String aesKey = "asdfghjklzxcvbnm";

        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            keygen.init(128, new SecureRandom(aesKey.getBytes()));
//            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG") ;
//            secureRandom.setSeed(aesKey.getBytes());
//            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法：
            // 在项目的Build path中先移除JRE System Library，再添加库JRE System Library，重新编译后就一切正常了。
            String AES_encode = new String(Base64.encodeBase64String(byte_AES));
            // 11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    public static String encryptAES(String data) throws Exception {
        String aesKey = "qwertyuiopasdfgh";
        String ivString = "asdfghjklzxcvbnm";

        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");   //参数分别代表 算法名称/加密模式/数据填充方式
            int blockSize = cipher.getBlockSize();

            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(aesKey.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] encrypted = cipher.doFinal(plaintext);

            return new sun.misc.BASE64Encoder().encode(encrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String decryptAES(String data) throws Exception {
        String aesKey = "qwertyuiopasdfgh";
        String ivString = "asdfghjklzxcvbnm";

        try {

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");   //参数分别代表 算法名称/加密模式/数据填充方式
            byte[] encrypted1 = Base64.decodeBase64(data);

            SecretKeySpec keyspec = new SecretKeySpec(aesKey.getBytes("UTF-8"), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

            cipher.init(Cipher.DECRYPT_MODE, keyspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 刷新token
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
            // 添加cookie
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setDomain("yeepay.org");
            cookie.setMaxAge(expiration);// 秒
            response.addCookie(cookie);
            return ResponseEntity.ok(YeePayResponse.buildSuccess(data));
        }
    }

    /**
     * 申请注册
     * @return
     * @throws AuthenticationException
     */
    @MethodLog( remark = "申请注册" )
    @RequestMapping(value = "/auth/register")
    public ResponseEntity<?> register(HttpServletRequest request) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        MchInfo mchInfo = getObject(param, MchInfo.class);
        if(mchInfo == null) mchInfo = new MchInfo();
        // 验证参数
        if (ObjectValidUtil.isInvalid(mchInfo.getName(), mchInfo.getEmail(), mchInfo.getMobile(), mchInfo.getPassword(), mchInfo.getType())) {
           return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_NOT_FOUND));
        }
        // 判断密码
        if(!StrUtil.checkPassword(mchInfo.getPassword())) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_PASSWORD_FORMAT_FAIL));
        }

        String smsCode = getStringRequired(param, "vercode");
        Object obj = request.getSession().getAttribute(SMS_VERIFY_CODE);
        if(obj == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_SMS_VERIFY_FAIL));
        }

        JSONObject codeObj = (JSONObject) obj;
        String mobile = codeObj.getString("mobile");
        String code = codeObj.getString("code");
        Long time = codeObj.getLong("time");
        // 判断与发送验证码时手机是否一致
        if(!mchInfo.getMobile().toString().equals(mobile)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_SMS_VERIFY_FAIL));
        }
        // 超过10分钟
        if(System.currentTimeMillis() > time + 1000 * 60 * 10) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_SMS_VERIFY_OVER_TIME));
        }
        // 判断验证码
        if(!smsCode.equals(code)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_SMS_VERIFY_FAIL));
        }

        int count;
        try {
            count = userService.register(mchInfo);
        }catch (ServiceException e) {
            return ResponseEntity.ok(BizResponse.build(e.getRetEnum()));
        }
        if(count != 1) return ResponseEntity.ok(YeePayResponse.build(RetEnum.RET_MCH_REGISTER_FAIL));
        return ResponseEntity.ok(BizResponse.buildSuccess());
    }

    /**
     * 发送验证短信
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/auth/sms_send")
    public ResponseEntity<?> sendSms(HttpServletRequest request) throws AuthenticationException{
        JSONObject param = getJsonParam(request);
        // 验证参数
        String mobile = param.getString("phone");
        if(!StrUtil.checkMobileNumber(mobile)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_MOBILE_FORMAT_ERROR));
        }
        // 验证手机号是否发送超限,一天5次
        // 暂时用内存(重启或分布式部署会防不住),真正使用时应改为redis
        String key = mobile + DateUtil.date2Str(new Date(), DateUtil.FORMAT_YYYY_MM_DD2);
        Integer times = mobileSendMap.get(mobile);
        if(times == null ) {
            mobileSendMap.put(key, 1);
        }else if(times <= 5) {
            mobileSendMap.put(key, times+1);
        }else {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_MOBILE_SEND_TOO_MUCH));
        }

        // 验证手机号是否被使用
        MchInfo mchInfo = userService.findByMobile(Long.parseLong(mobile));
        if(mchInfo != null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_MOBILE_USED));
        }

        // 发送短信服务
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        Map smsMap = new HashMap<>();
        smsMap.put("phoneNumbers", mobile);
        smsMap.put("signName", "yeepay聚合支付");
        smsMap.put("templateCode", "SMS_127164863");
        smsMap.put("templateParam", "{\"code\":\""+verifyCode+"\"}");
        try {
            SendSmsResponse response = AliSmsUtil.sendSms(smsMap);
            _log.info("调用阿里短信服务完成.mobile={},code={},message={},requestId={},bizId={}", mobile,
                    response.getCode(), response.getMessage(), response.getRequestId(), response.getBizId());
        } catch (ClientException e) {
            _log.error(e, "");
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_MOBILE_SEND_ERROR));
        }
        // 将短信验证码存在session中,分布式部署考虑放到redis
        JSONObject codeObj = new JSONObject();
        codeObj.put("mobile", mobile);
        codeObj.put("code", verifyCode);
        codeObj.put("time", System.currentTimeMillis());
        request.getSession().setAttribute(SMS_VERIFY_CODE, codeObj);
        return ResponseEntity.ok(BizResponse.buildSuccess());
    }

    /**
     * 获取验证码
     */
    @RequestMapping(value = "/auth/auth_code_get")
    public void getAuthCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map randomMap = RandomValidateCodeUtil.getRandcode(120, 40, 6, 20);
        // TODO 将验证码存储,用于验证
        String randomString = randomMap.get("randomString").toString();
        BufferedImage randomImage = (BufferedImage)randomMap.get("randomImage");
        ImageIO.write(randomImage, "JPEG", response.getOutputStream());
    }

}
