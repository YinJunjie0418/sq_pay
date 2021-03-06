package org.yeepay.mch.order.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.PayConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.domain.BizResponse;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.common.util.*;
import org.yeepay.core.entity.*;
import org.yeepay.mch.common.ctrl.BaseController;
import org.yeepay.mch.common.service.RpcCommonService;
import org.yeepay.mch.common.util.CodeImgUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/12/27
 * @description:
 */
@Controller
@RequestMapping(Constant.MCH_CONTROLLER_ROOT_PATH + "/payment")
public class PaymentController extends BaseController {

    private static final MyLog _log = MyLog.getLog(PaymentController.class);

    @Autowired
    private RpcCommonService rpcCommonService;
    @Autowired
    protected WxMpConfigStorage configStorage;
    @Autowired
    protected WxMpService wxMpService;

    /**
     * ????????????openid
     */
    @RequestMapping("/wx_openid_get")
    public void getWxOpenid(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        if(StringUtils.isBlank(code)) {
            String redirectUrl = mainConfig.getMchApiUrl() + "/payment/wx_openid_get";
            String url1 = wxMpService.oauth2buildAuthorizationUrl(redirectUrl, WxConsts.OAuth2Scope.SNSAPI_BASE, null);
            try {
                response.sendRedirect(url1);
            } catch (IOException e) {
                _log.error(e, "");
            }
            return;
        }

        WxMpOAuth2AccessToken accessToken;
        try {
            accessToken = this.wxMpService.oauth2getAccessToken(code);
            String openId = accessToken.getOpenId();
            _log.info("return openId={}", openId);
        } catch (WxErrorException e) {

        }
    }

    /**
     * ??????????????????openid??????????????????,???????????????????????????????????????
     * ????????????????????????????????????????????????,???????????????????????????
     * @return
     */
    @RequestMapping("/wx_openid_redirect")
    public String redirectWxOpenid(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String logPrefix = "[wx_openid_redirect]";
        _log.info("{} start", logPrefix);
        String redirectUrl = request.getParameter("redirectUrl");
        String wx = request.getParameter("wx");
        JSONObject paramObj = JSONObject.parseObject(new String(MyBase64.decode(wx)));
        String code = request.getParameter("code");
        String openId = "";
        _log.info("{} wx={},redirectUrl={},code={},openId={}", logPrefix, paramObj, redirectUrl, code, openId);
        if(StringUtils.isNotBlank(code)){//??????request?????????code?????????????????????
            try {
                // ???????????????????????????????????????
                WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
                wxMpConfigStorage.setAppId(paramObj.getString("wxAppId"));
                wxMpConfigStorage.setSecret(paramObj.getString("wxAppSecret"));
                WxMpService wxMpService = new WxMpServiceImpl();
                wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
                WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
                openId = accessToken.getOpenId();
                _log.info("{}return openId={}", logPrefix, openId);
            } catch (WxErrorException e) {
                _log.error(e, "");
                model.put("errMsg", RetEnum.RET_MCH_GET_WX_OPENID_FAIL);
                return PAGE_COMMON_ERROR;
            }
            if(redirectUrl.indexOf("?") > 0) {
                redirectUrl += "&openId=" + openId;
            }else {
                redirectUrl += "?openId=" + openId;
            }
            _log.info("{}redirectUrl={}", logPrefix, redirectUrl);
            response.sendRedirect(redirectUrl);
        }else{
            // ????????????,??????code
            String redirectUrl4Vx = mainConfig.getMchApiUrl() + "/payment/wx_openid_redirect?wx=" + wx + "&redirectUrl=" + redirectUrl;
            String url = String.format(mainConfig.getRedirectWxCodeUrl() + "?appid=%s&scope=snsapi_base&state=hello-world&redirect_uri=%s", paramObj.getString("wxAppId"), StrUtil.urlEnodeUTF8(redirectUrl4Vx));
            _log.info("{}get code, redirectUrl={}", logPrefix, url);
            response.sendRedirect(url);
        }
        return null;
    }

    @RequestMapping("/qrcode")
    public String openPayment(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String ua = request.getHeader("User-Agent");
        _log.info("scan qrcode, ua={}", ua);
        if(StringUtils.isBlank(ua)) {
            // ???????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_UA_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        String mchIdStr = request.getParameter("mchId");
        String appId = request.getParameter("appId");
        String codeIdStr = request.getParameter("codeId");
        //String passageId = request.getParameter("passageId");
        // ??????????????????,?????????????????????,????????????????????????
        String p = request.getParameter("p");
        if(StringUtils.isNotBlank(p)) {
            p = new String(MyBase64.decode(p));
            JSONObject param = JSON.parseObject(p);
            mchIdStr = param.getString("mchId");
            appId = param.getString("appId");
            codeIdStr = param.getString("codeId");
            //passageId = param.getString("passageId");
        }

        // ????????????
        if(!NumberUtils.isDigits(mchIdStr) || !NumberUtils.isDigits(codeIdStr)) {
            model.put("errMsg", RetEnum.RET_COMM_PARAM_ERROR);
            return PAGE_COMMON_ERROR;
        }
        Long mchId = Long.parseLong(mchIdStr);
        Long codeId = Long.parseLong(codeIdStr);
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        MchApp mchApp = rpcCommonService.rpcMchAppService.findByMchIdAndAppId(mchId, appId);
        MchQrCode mchQrCode = rpcCommonService.rpcMchQrCodeService.findById(codeId);

        // ???????????????????????????
        if(mchInfo == null || mchApp == null || mchQrCode == null) {
            model.put("errMsg", RetEnum.RET_MCH_CONFIG_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        // ????????????????????????
        if(MchConstant.PUB_YES != mchInfo.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchApp.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchQrCode.getStatus()) {
            model.put("errMsg", RetEnum.RET_MCH_STATUS_CLOSE);
            return PAGE_COMMON_ERROR;
        }

        // ???????????????????????????????????????
        String channels = mchQrCode.getChannels();
        JSONArray channelArray = JSONArray.parseArray(channels);
        if(StringUtils.isBlank(channels) || CollectionUtils.isEmpty(channelArray)) {
            // ????????????????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_QR_CHANNEL_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        String channelId = getChannelId(ua);
        //channelId = "ALIPAY_WAP";
        if(channelId == null) {
            model.put("errMsg", RetEnum.RET_MCH_UA_NOT_SUPPORT);
            return PAGE_COMMON_ERROR;
        }

        // ????????????????????????????????????????????????
        boolean flag = false;
        String productId = null;
        for(int i = 0; i < channelArray.size(); i++) {
            JSONObject c = channelArray.getJSONObject(i);
            String ci = c.getString("channelId");
            if(channelId.equalsIgnoreCase(ci)) {
                flag = true;
                productId = c.getString("productId");
                break;
            }
        }
        if(!flag) {
            // ?????????????????????????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_QR_UA_NOT_CONFIG);
            return PAGE_COMMON_ERROR;
        }
        if(StringUtils.isBlank(productId)) {
            // ??????????????????ID
            model.put("errMsg", RetEnum.RET_MCH_QR_UA_NOT_CONFIG);
            return PAGE_COMMON_ERROR;
        }

        String openId = request.getParameter("openId");

        // ??????????????????????????????,????????????openId
        if(PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
            if(StringUtils.isBlank(openId)) {
                JSONObject param = new JSONObject();
                param.put("mchId", mchId);
                param.put("appId", appId);
                param.put("codeId", codeId);

                MchPayPassage mchPayPassage = rpcCommonService.rpcMchPayPassageService.findByMchIdAndProductId(mchId, Integer.parseInt(productId));
                if(mchPayPassage == null) {
                    // ????????????????????????????????????
                    model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                    return PAGE_COMMON_ERROR;
                }

                if(mchPayPassage.getIfMode() == 1) {
                    // ????????????ID
                    Integer payPassageId = mchPayPassage.getPayPassageId();
                    List<PayPassageAccount> payPassageAccountList = rpcCommonService.rpcPayPassageAccountService.selectAllByPassageId(payPassageId);
                    if(CollectionUtils.isEmpty(payPassageAccountList)) {
                        model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                        return PAGE_COMMON_ERROR;
                    }
                    // ??????????????????????????????????????????
                    PayPassageAccount payPassageAccount = payPassageAccountList.get(0);

                    JSONObject paramObj = JSON.parseObject(payPassageAccount.getParam());
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");
                    //param.put("passageId", payChannel.getPassageId());
                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);

                }else {
                    // ????????????

                }

                /*if(MchConstant.MCH_TYPE_PLATFORM == mchInfo.getType()) {
                    List<PayChannel> payChannelList = rpcCommonService.rpcPayChannelService.selectByMch(mchId, PayConstant.PAY_CHANNEL_WX_JSAPI);
                    if(CollectionUtils.isEmpty(payChannelList)) {
                        // ????????????????????????????????????
                        model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                        return PAGE_COMMON_ERROR;
                    }
                    PayChannel payChannel = payChannelList.get(0);
                    String configParam = payChannel.getParam();
                    JSONObject paramObj = JSON.parseObject(configParam);
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");
                    param.put("passageId", payChannel.getPassageId());
                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);
                }else if(MchConstant.MCH_TYPE_PRIVATE == mchInfo.getType()) {
                    MchChannel mchChannel = rpcCommonService.rpcMchChannelService.findByMACId(mchId, appId, PayConstant.PAY_CHANNEL_WX_JSAPI);
                    String configParam = mchChannel.getParam();
                    JSONObject paramObj = JSON.parseObject(configParam);
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");

                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);
                }*/

                String str = MyBase64.encode(param.toJSONString().getBytes());

                String redirectUrl = String.format("%s/payment/qrcode?p=%s", mainConfig.getMchApiUrl(), str);
                String url = String.format("%s/payment/wx_openid_redirect?wx=%s&redirectUrl=%s", mainConfig.getMchApiUrl(), str, redirectUrl);
                try {
                    response.sendRedirect(url);
                } catch (IOException e) {
                    _log.error("", e);
                }
                return null;
            }
        }

        // ??????????????????????????????
        model.put("mchId", mchId);
        model.put("appId", appId);
        model.put("codeId", codeId);
        model.put("channelId", channelId);
        model.put("productId", productId);
        model.put("codeName", mchQrCode.getCodeName());
        model.put("openId", openId == null ? "" : openId);
        return "payment/qrcode";
    }

    @RequestMapping("/qrcode_custom")
    public String openPaymentCustom(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String ua = request.getHeader("User-Agent");
        _log.info("scan qrcode, ua={}", ua);
        if(StringUtils.isBlank(ua)) {
            // ???????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_UA_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        String mchIdStr = request.getParameter("mchId");
        String amountStr  = request.getParameter("amount");
        String mchOrderNo  = request.getParameter("mchOrderNo");
        String notifyUrl  = request.getParameter("notifyUrl");
        String appId = request.getParameter("appId");
        String codeIdStr = request.getParameter("codeId");
        //String passageId = request.getParameter("passageId");
        // ??????????????????,?????????????????????,????????????????????????
        String p = request.getParameter("p");
        if(StringUtils.isNotBlank(p)) {
            p = new String(MyBase64.decode(p));
            JSONObject param = JSON.parseObject(p);
            mchIdStr = param.getString("mchId");
            appId = param.getString("appId");
            amountStr = param.getString("amount");
            mchOrderNo = param.getString("mchOrderNo");
            notifyUrl = param.getString("notifyUrl");
            codeIdStr = param.getString("codeId");
            //passageId = param.getString("passageId");
        }

        // ????????????
        if(!NumberUtils.isDigits(mchIdStr) || !NumberUtils.isDigits(codeIdStr)) {
            model.put("errMsg", RetEnum.RET_COMM_PARAM_ERROR);
            return PAGE_COMMON_ERROR;
        }
        Long mchId = Long.parseLong(mchIdStr);
        Double amount = Double.parseDouble(amountStr);
        Long codeId = Long.parseLong(codeIdStr);
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        MchApp mchApp = rpcCommonService.rpcMchAppService.findByMchIdAndAppId(mchId, appId);
        MchQrCode mchQrCode = rpcCommonService.rpcMchQrCodeService.findById(codeId);

        // ???????????????????????????
        if(mchInfo == null || mchApp == null || mchQrCode == null) {
            model.put("errMsg", RetEnum.RET_MCH_CONFIG_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        // ????????????????????????
        if(MchConstant.PUB_YES != mchInfo.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchApp.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchQrCode.getStatus()) {
            model.put("errMsg", RetEnum.RET_MCH_STATUS_CLOSE);
            return PAGE_COMMON_ERROR;
        }

        // ???????????????????????????????????????
        String channels = mchQrCode.getChannels();
        JSONArray channelArray = JSONArray.parseArray(channels);
        if(StringUtils.isBlank(channels) || CollectionUtils.isEmpty(channelArray)) {
            // ????????????????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_QR_CHANNEL_NOT_EXIST);
            return PAGE_COMMON_ERROR;
        }

        String channelId = getChannelId(ua);
        //channelId = "ALIPAY_WAP";
        if(channelId == null) {
            model.put("errMsg", RetEnum.RET_MCH_UA_NOT_SUPPORT);
            return PAGE_COMMON_ERROR;
        }

        // ????????????????????????????????????????????????
        boolean flag = false;
        String productId = null;
        for(int i = 0; i < channelArray.size(); i++) {
            JSONObject c = channelArray.getJSONObject(i);
            String ci = c.getString("channelId");
            if(channelId.equalsIgnoreCase(ci)) {
                flag = true;
                productId = c.getString("productId");
                break;
            }
        }
        if(!flag) {
            // ?????????????????????????????????????????????
            model.put("errMsg", RetEnum.RET_MCH_QR_UA_NOT_CONFIG);
            return PAGE_COMMON_ERROR;
        }
        if(StringUtils.isBlank(productId)) {
            // ??????????????????ID
            model.put("errMsg", RetEnum.RET_MCH_QR_UA_NOT_CONFIG);
            return PAGE_COMMON_ERROR;
        }

        String openId = request.getParameter("openId");

        // ??????????????????????????????,????????????openId
        if(PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId)) {
            if(StringUtils.isBlank(openId)) {
                JSONObject param = new JSONObject();
                param.put("mchId", mchId);
                param.put("amount", amount);
                param.put("mchOrderNo", mchOrderNo);
                param.put("notifyUrl", notifyUrl);
                param.put("appId", appId);
                param.put("codeId", codeId);



                MchPayPassage mchPayPassage = rpcCommonService.rpcMchPayPassageService.findByMchIdAndProductId(mchId, Integer.parseInt(productId));
                if(mchPayPassage == null) {
                    // ????????????????????????????????????
                    model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                    return PAGE_COMMON_ERROR;
                }

                if(mchPayPassage.getIfMode() == 1) {
                    // ????????????ID
                    Integer payPassageId = mchPayPassage.getPayPassageId();
                    List<PayPassageAccount> payPassageAccountList = rpcCommonService.rpcPayPassageAccountService.selectAllByPassageId(payPassageId);
                    if(CollectionUtils.isEmpty(payPassageAccountList)) {
                        model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                        return PAGE_COMMON_ERROR;
                    }
                    // ??????????????????????????????????????????
                    PayPassageAccount payPassageAccount = payPassageAccountList.get(0);

                    JSONObject paramObj = JSON.parseObject(payPassageAccount.getParam());
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");
                    //param.put("passageId", payChannel.getPassageId());
                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);

                }else {
                    // ????????????

                }

                /*if(MchConstant.MCH_TYPE_PLATFORM == mchInfo.getType()) {
                    List<PayChannel> payChannelList = rpcCommonService.rpcPayChannelService.selectByMch(mchId, PayConstant.PAY_CHANNEL_WX_JSAPI);
                    if(CollectionUtils.isEmpty(payChannelList)) {
                        // ????????????????????????????????????
                        model.put("errMsg", RetEnum.RET_MCH_PASSAGE_NOT_EXIST);
                        return PAGE_COMMON_ERROR;
                    }
                    PayChannel payChannel = payChannelList.get(0);
                    String configParam = payChannel.getParam();
                    JSONObject paramObj = JSON.parseObject(configParam);
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");
                    param.put("passageId", payChannel.getPassageId());
                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);
                }else if(MchConstant.MCH_TYPE_PRIVATE == mchInfo.getType()) {
                    MchChannel mchChannel = rpcCommonService.rpcMchChannelService.findByMACId(mchId, appId, PayConstant.PAY_CHANNEL_WX_JSAPI);
                    String configParam = mchChannel.getParam();
                    JSONObject paramObj = JSON.parseObject(configParam);
                    String wxAppId = paramObj.getString("appId");
                    String wxAppSecret = paramObj.getString("appSecret");

                    param.put("wxAppId", wxAppId);
                    param.put("wxAppSecret", wxAppSecret);
                }*/

                String str = MyBase64.encode(param.toJSONString().getBytes());

                String redirectUrl = String.format("%s/payment/qrcode_custom?p=%s", mainConfig.getMchApiUrl(), str);
                String url = String.format("%s/payment/wx_openid_redirect?wx=%s&redirectUrl=%s", mainConfig.getMchApiUrl(), str, redirectUrl);
                try {
                    response.sendRedirect(url);
                } catch (IOException e) {
                    _log.error("", e);
                }
                return null;
            }
        }

        // ??????????????????????????????
        model.put("mchId", mchId);
        model.put("amount", amount);
        model.put("mchOrderNo", mchOrderNo);
        model.put("notifyUrl", notifyUrl);
        model.put("appId", appId);
        model.put("codeId", codeId);
        model.put("channelId", channelId);
        model.put("productId", productId);
        model.put("codeName", mchQrCode.getCodeName());
        model.put("openId", openId == null ? "" : openId);
        model.put("mchName", mchInfo.getName());
        return "payment/qrcodecustom";
    }

    /**
     * ???????????????????????????
     * @param request
     * @return
     */
    @RequestMapping("/scan_custom_pay")
    @ResponseBody
    public ResponseEntity<?> scanCustomPay(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String mchIdStr = request.getParameter("mchId");
        String appId = request.getParameter("appId");
        String codeIdStr = request.getParameter("codeId");
        String channelId = request.getParameter("channelId");
        String productId = request.getParameter("productId");
        String openId = request.getParameter("openId");
        String amount = request.getParameter("amount");
        String mchOrderNo = request.getParameter("mchOrderNo");
        String notifyUrl = request.getParameter("notifyUrl");

        Float amountF = Float.parseFloat(amount) * 100;
        Long amountL = amountF.longValue();
        if(!NumberUtils.isDigits(mchIdStr) || !NumberUtils.isDigits(codeIdStr)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        }
        if(StringUtils.isBlank(productId) || !NumberUtils.isDigits(productId)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        }
        Long mchId = Long.parseLong(mchIdStr);
        Long codeId = Long.parseLong(codeIdStr);
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        MchApp mchApp = rpcCommonService.rpcMchAppService.findByMchIdAndAppId(mchId, appId);
        MchQrCode mchQrCode = rpcCommonService.rpcMchQrCodeService.findById(codeId);

        if(PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId) && StringUtils.isBlank(openId)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_WX_OPENID_NOT_EXIST));
        }

        // ???????????????????????????
        if(mchInfo == null || mchApp == null || mchQrCode == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CONFIG_NOT_EXIST));
        }

        // ????????????????????????
        if(MchConstant.PUB_YES != mchInfo.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchApp.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchQrCode.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_CLOSE));
        }
        // ??????????????????
        String orderId = mchOrderNo;
        MchTradeOrder mchTradeOrder = new MchTradeOrder();
        mchTradeOrder.setMchId(mchId);
        mchTradeOrder.setAppId(appId);
        mchTradeOrder.setAmount(amountL);
        //mchTradeOrder.setChannelId(channelId);
        mchTradeOrder.setProductId(productId);
        mchTradeOrder.setGoodsId(codeId+"");
        mchTradeOrder.setSubject(mchQrCode.getCodeName());
        mchTradeOrder.setTradeOrderId(orderId);
        mchTradeOrder.setChannelUserId(openId);
        mchTradeOrder.setBody(mchQrCode.getCodeName());
//        int result = rpcCommonService.rpcMchTradeOrderService.add(mchTradeOrder);
//        _log.info("create tradeOrder, orderId={}, result={}", orderId, result);
//        if(result != 1) {
//            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_TRADE_ORDER_FAIL));
//        }
        // ??????????????????
        try {
            Map resMap = createCustomPayOrder(mchInfo, mchApp, mchTradeOrder, notifyUrl);
//            String payOrderId = resMap.get("payOrderId").toString();
//            MchTradeOrder updateMchTradeOrder = new MchTradeOrder();
//            updateMchTradeOrder.setTradeOrderId(orderId);
//            updateMchTradeOrder.setPayOrderId(payOrderId);
//            updateMchTradeOrder.setStatus(MchConstant.TRADE_ORDER_STATUS_ING);
//            result = rpcCommonService.rpcMchTradeOrderService.update(updateMchTradeOrder);
//            _log.info("update tradeOrder, orderId={},payOrderId={},result={}", orderId, payOrderId, result);
            if(null == resMap.get("payOrderId")) {
                return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_PAY_ORDER_FAIL));
            }
            jsonObject.put("payParams", resMap.get("payParams"));
            return ResponseEntity.ok(YeePayResponse.buildSuccess(jsonObject));
        }catch (Exception e) {
            _log.error(e, "??????????????????");
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_PAY_ORDER_FAIL));
        }
    }

    /**
     * ??????????????????
     * @param request
     * @return
     */
    @RequestMapping("/scan_pay")
    @ResponseBody
    public ResponseEntity<?> scanPay(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String mchIdStr = request.getParameter("mchId");
        String appId = request.getParameter("appId");
        String codeIdStr = request.getParameter("codeId");
        String channelId = request.getParameter("channelId");
        String productId = request.getParameter("productId");
        String openId = request.getParameter("openId");
        String amount = request.getParameter("amount");
        Float amountF = Float.parseFloat(amount) * 100;
        Long amountL = amountF.longValue();
        if(!NumberUtils.isDigits(mchIdStr) || !NumberUtils.isDigits(codeIdStr)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        }
        if(StringUtils.isBlank(productId) || !NumberUtils.isDigits(productId)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        }
        Long mchId = Long.parseLong(mchIdStr);
        Long codeId = Long.parseLong(codeIdStr);
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        MchApp mchApp = rpcCommonService.rpcMchAppService.findByMchIdAndAppId(mchId, appId);
        MchQrCode mchQrCode = rpcCommonService.rpcMchQrCodeService.findById(codeId);

        if(PayConstant.PAY_CHANNEL_WX_JSAPI.equalsIgnoreCase(channelId) && StringUtils.isBlank(openId)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_WX_OPENID_NOT_EXIST));
        }

        // ???????????????????????????
        if(mchInfo == null || mchApp == null || mchQrCode == null) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CONFIG_NOT_EXIST));
        }

        // ????????????????????????
        if(MchConstant.PUB_YES != mchInfo.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchApp.getStatus().byteValue() ||
                MchConstant.PUB_YES != mchQrCode.getStatus()) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_CLOSE));
        }
        // ??????????????????
        String orderId = MySeq.getTrade();
        MchTradeOrder mchTradeOrder = new MchTradeOrder();
        mchTradeOrder.setMchId(mchId);
        mchTradeOrder.setAppId(appId);
        mchTradeOrder.setAmount(amountL);
        //mchTradeOrder.setChannelId(channelId);
        mchTradeOrder.setProductId(productId);
        mchTradeOrder.setGoodsId(codeId+"");
        mchTradeOrder.setSubject(mchQrCode.getCodeName());
        mchTradeOrder.setTradeOrderId(orderId);
        mchTradeOrder.setChannelUserId(openId);
        mchTradeOrder.setBody(mchQrCode.getCodeName());
        int result = rpcCommonService.rpcMchTradeOrderService.add(mchTradeOrder);
        _log.info("create tradeOrder, orderId={}, result={}", orderId, result);
        if(result != 1) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_TRADE_ORDER_FAIL));
        }
        // ??????????????????
        try {
            Map resMap = createPayOrder(mchInfo, mchApp, mchTradeOrder);
            String payOrderId = resMap.get("payOrderId").toString();
            MchTradeOrder updateMchTradeOrder = new MchTradeOrder();
            updateMchTradeOrder.setTradeOrderId(orderId);
            updateMchTradeOrder.setPayOrderId(payOrderId);
            updateMchTradeOrder.setStatus(MchConstant.TRADE_ORDER_STATUS_ING);
            result = rpcCommonService.rpcMchTradeOrderService.update(updateMchTradeOrder);
            _log.info("update tradeOrder, orderId={},payOrderId={},result={}", orderId, payOrderId, result);
            if(result != 1) {
                return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_UPDATE_TRADE_ORDER_FAIL));
            }
            jsonObject.put("payParams", resMap.get("payParams"));
            return ResponseEntity.ok(YeePayResponse.buildSuccess(jsonObject));
        }catch (Exception e) {
            _log.error(e, "??????????????????");
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_PAY_ORDER_FAIL));
        }
    }

    /**
     * ????????????
     * @param request
     * @return
     */
    @RequestMapping("/recharge")
    @ResponseBody
    public ResponseEntity<?> recharge(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        Long mchId = getUser().getId();
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        if(mchInfo.getStatus() != MchConstant.PUB_YES) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_STATUS_STOP));
        }

        String productId = request.getParameter("productId");
        String amount = request.getParameter("amount");
        Float amountF = Float.parseFloat(amount) * 100;
        Long amountL = amountF.longValue();

        if(StringUtils.isBlank(productId) || !NumberUtils.isDigits(productId)) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        }

        PayProduct payProduct = rpcCommonService.rpcPayProductService.findById(Integer.parseInt(productId));
        if(payProduct == null || payProduct.getStatus() != MchConstant.PUB_YES) {
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_PAY_PRODUCT_NOT_EXIST));
        }

        // ??????????????????
        String orderId = MySeq.getTrade();
        MchTradeOrder mchTradeOrder = new MchTradeOrder();
        mchTradeOrder.setTradeOrderId(orderId);
        mchTradeOrder.setTradeType(MchConstant.TRADE_TYPE_RECHARGE);    // ??????
        mchTradeOrder.setMchId(mchId);
        mchTradeOrder.setAmount(amountL);
        mchTradeOrder.setProductId(productId);
        mchTradeOrder.setProductType(payProduct.getProductType());
        mchTradeOrder.setSubject("??????" + amountL/100.00 + "???");
        mchTradeOrder.setBody("??????" + amountL/100.00 + "???");
        mchTradeOrder.setClientIp(IPUtility.getClientIp(request));  // ?????????IP

        // ??????????????????
        try {
            String extra = "{\"bank\":"+productId+"}";
            Map resMap = createCashier(mchInfo, null, mchTradeOrder, extra);
            if(resMap == null) {
                return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_PAY_ORDER_FAIL, "????????????"));
            }
            int result = rpcCommonService.rpcMchTradeOrderService.add(mchTradeOrder);
            _log.info("??????????????????, orderId={}, result={}", orderId, result);
            if(result != 1) {
                return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_TRADE_ORDER_FAIL));
            }

            // ??????????????????
            rpcCommonService.rpcMchTradeOrderService.updateStatus4Ing(mchTradeOrder.getTradeOrderId());

            jsonObject.put("payUrl", resMap.get("payUrl"));
            return ResponseEntity.ok(YeePayResponse.buildSuccess(jsonObject));
        }catch (Exception e) {
            _log.error(e, "??????????????????");
            return ResponseEntity.ok(BizResponse.build(RetEnum.RET_MCH_CREATE_PAY_ORDER_FAIL));
        }
    }

    /**
     * ???????????????????????????
     * @return
     */
    @RequestMapping("/recharge_redirect")
    public String redirectRecharge(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        String logPrefix = "[wx_openid_redirect]";
        _log.info("{} start", logPrefix);
        String payUrl = request.getParameter("payUrl");
        if(StringUtils.isBlank(payUrl)) {
            model.put("errMsg", RetEnum.RET_COMM_PARAM_ERROR);
            return PAGE_COMMON_ERROR;
        }
        model.put("payUrl", payUrl);
        return "payment/redirect";
    }

    /**
     * ????????????
     * @param request
     * @return
     */
    @RequestMapping("/trade_query")
    @ResponseBody
    public ResponseEntity<?> queryTrade(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        String tradeOrderId = request.getParameter("tradeOrderId");
        if(StringUtils.isBlank(tradeOrderId)) return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_PARAM_ERROR));
        MchTradeOrder mchTradeOrder = rpcCommonService.rpcMchTradeOrderService.findByTradeOrderId(tradeOrderId);
        if(mchTradeOrder == null) return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_RECORD_NOT_EXIST));
        byte status = mchTradeOrder.getStatus();
        jsonObject.put("status", status);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(jsonObject));
    }

    /**
     * ????????????????????????
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/notify")
    @ResponseBody
    public String payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        _log.info("====== ?????????????????????????????? ======");
        Map<String,Object> paramMap = request2payResponseMap(request, new String[]{
                "payOrderId", "mchId", "appId", "productId", "mchOrderNo", "amount", "income", "status",
                "channelOrderNo", "channelAttach", "param1",
                "param2","paySuccTime","backType", "sign"
        });
        String resStr;
        _log.info("[yeepay_Notify], paramMap={}", paramMap);
        String payOrderId = (String) paramMap.get("payOrderId");
        String mchOrderNo = (String) paramMap.get("mchOrderNo");
        Long mchIncome = paramMap.get("income") == null ? 0l : Long.parseLong(paramMap.get("income").toString());
        if (!verifyPayResponse(paramMap)) {
            String errorMessage = "verify request param failed.";
            _log.warn(errorMessage);
            resStr = "fail";
            _log.info("====== ?????????????????????????????? ======1");
        }else {
            try {
                MchTradeOrder mchTradeOrder = rpcCommonService.rpcMchTradeOrderService.findByTradeOrderId(mchOrderNo);
                if(mchTradeOrder != null && mchTradeOrder.getStatus() == MchConstant.TRADE_ORDER_STATUS_SUCCESS) {
                    return "success";
                }
                // ??????????????????
                int ret = rpcCommonService.rpcMchTradeOrderService.updateStatus4Success(mchOrderNo, payOrderId, mchIncome);

                // ret????????????
                // ??????1??????????????????,??????????????????success
                // ?????????,??????????????????fail,??????????????????
                if(ret == 1) {
                    resStr = "success";
                }else {
                    resStr = "fail";
                    _log.info("====== ?????????????????????????????? ======2");
                }
            }catch (Exception e) {
                resStr = "fail";
                _log.error(e, "??????????????????,payOrderId=%s.mchOrderNo=%s", payOrderId, mchOrderNo);
            }
        }

        _log.info("[yeepay_Notify]: response={},payOrderId={},mchOrderNo={}", resStr, payOrderId, mchOrderNo);
        _log.info("====== ?????????????????????????????? ======");
        return resStr;
    }

    /**
     * ??????????????????
     * @param mchInfo
     * @param mchApp
     * @param mchTradeOrder
     * @return
     */
    private Map createPayOrder(MchInfo mchInfo, MchApp mchApp, MchTradeOrder mchTradeOrder) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchInfo.getMchId());                          // ??????ID
        if(mchApp != null) paramMap.put("appId", mchApp.getAppId());                           // ??????ID
        paramMap.put("mchOrderNo", mchTradeOrder.getTradeOrderId());        // ??????????????????
        paramMap.put("productId", mchTradeOrder.getProductId());            // ????????????ID
        paramMap.put("amount", mchTradeOrder.getAmount());                  // ????????????,?????????
        paramMap.put("currency", "cny");                                    // ??????, cny-?????????
        paramMap.put("clientIp", mchTradeOrder.getClientIp());              // ????????????,IP????????????
        paramMap.put("device", "WEB");                                      // ??????
        paramMap.put("subject", mchTradeOrder.getSubject());
        paramMap.put("body", mchTradeOrder.getBody());
        paramMap.put("notifyUrl", mainConfig.getNotifyUrl());               // ??????URL
        paramMap.put("param1", "");                                         // ????????????1
        paramMap.put("param2", "");                                         // ????????????2
        // ???????????????????????????????????????openId
        if("8004".equalsIgnoreCase(mchTradeOrder.getProductId())) {
            JSONObject extra = new JSONObject();
            extra.put("openId", mchTradeOrder.getChannelUserId());              // ??????openId
            paramMap.put("extra", extra.toJSONString());                        // ????????????
        }

        String reqSign = PayDigestUtil.getSign(paramMap, mchInfo.getPrivateKey());
        paramMap.put("sign", reqSign);   // ??????
        String reqData = "params=" + paramMap.toJSONString();
        _log.info("yeepay_req:{}", reqData);
        String url = mainConfig.getPayUrl() + "/pay/create_order?";
        String result = YeePayUtil.call4Post(url + reqData);
        _log.info("yeepay_res:{}", result);
        Map retMap = JSON.parseObject(result);
        if(YeePayUtil.isSuccess(retMap)) {
            // ??????
            String checkSign = PayDigestUtil.getSign(retMap, mchInfo.getPrivateKey(), "sign");
            String retSign = (String) retMap.get("sign");
            //if(checkSign.equals(retSign)) return retMap;
            //_log.info("????????????:retSign={},checkSign={}", retSign, checkSign);
            return retMap;
        }
        return retMap;
    }

    /**
     * ???????????????????????????
     * @param mchInfo
     * @param mchApp
     * @param mchTradeOrder
     * @return
     */
    private Map createCustomPayOrder(MchInfo mchInfo, MchApp mchApp, MchTradeOrder mchTradeOrder, String notifyUrl) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchInfo.getMchId());                          // ??????ID
        if(mchApp != null) paramMap.put("appId", mchApp.getAppId());                           // ??????ID
        paramMap.put("mchOrderNo", mchTradeOrder.getTradeOrderId());        // ??????????????????
        paramMap.put("productId", mchTradeOrder.getProductId());            // ????????????ID
        paramMap.put("amount", mchTradeOrder.getAmount());                  // ????????????,?????????
        paramMap.put("currency", "cny");                                    // ??????, cny-?????????
        paramMap.put("clientIp", mchTradeOrder.getClientIp());              // ????????????,IP????????????
        paramMap.put("device", "WEB");                                      // ??????
        paramMap.put("subject", mchTradeOrder.getSubject());
        paramMap.put("body", mchTradeOrder.getBody());
        paramMap.put("notifyUrl", notifyUrl);               // ??????URL
        paramMap.put("param1", "");                                         // ????????????1
        paramMap.put("param2", "");                                         // ????????????2
        // ???????????????????????????????????????openId
        if("8004".equalsIgnoreCase(mchTradeOrder.getProductId())) {
            JSONObject extra = new JSONObject();
            extra.put("openId", mchTradeOrder.getChannelUserId());              // ??????openId
            paramMap.put("extra", extra.toJSONString());                        // ????????????
        }

        String reqSign = PayDigestUtil.getSign(paramMap, mchInfo.getPrivateKey());
        paramMap.put("sign", reqSign);   // ??????
        String reqData = "params=" + paramMap.toJSONString();
        _log.info("yeepay_req:{}", reqData);
        String url = mainConfig.getPayUrl() + "/pay/create_order?";
        String result = YeePayUtil.call4Post(url + reqData);
        _log.info("yeepay_res:{}", result);
        Map retMap = JSON.parseObject(result);
        if(YeePayUtil.isSuccess(retMap)) {
            // ??????
            String checkSign = PayDigestUtil.getSign(retMap, mchInfo.getPrivateKey(), "sign");
            String retSign = (String) retMap.get("sign");
            //if(checkSign.equals(retSign)) return retMap;
            //_log.info("????????????:retSign={},checkSign={}", retSign, checkSign);
            return retMap;
        }
        return retMap;
    }

    private Map createCashier(MchInfo mchInfo, MchApp mchApp, MchTradeOrder mchTradeOrder, String extra) {
        JSONObject paramMap = new JSONObject();
        paramMap.put("mchId", mchInfo.getMchId());                          // ??????ID
        if(mchApp != null) paramMap.put("appId", mchApp.getAppId());                           // ??????ID
        paramMap.put("mchOrderNo", mchTradeOrder.getTradeOrderId());        // ??????????????????
        paramMap.put("productId", mchTradeOrder.getProductId());            // ????????????ID
        paramMap.put("amount", mchTradeOrder.getAmount());                  // ????????????,?????????
        paramMap.put("currency", "cny");                                    // ??????, cny-?????????
        paramMap.put("clientIp", mchTradeOrder.getClientIp());              // ????????????,IP????????????
        paramMap.put("device", "WEB");                                      // ??????
        paramMap.put("subject", mchTradeOrder.getSubject());
        paramMap.put("body", mchTradeOrder.getBody());
        paramMap.put("notifyUrl", mainConfig.getNotifyUrl());               // ??????URL
        paramMap.put("param1", "");                                         // ????????????1
        paramMap.put("param2", "");                                         // ????????????2
        paramMap.put("extra", extra);

        String reqSign = PayDigestUtil.getSign(paramMap, mchInfo.getPrivateKey());
        paramMap.put("sign", reqSign);   // ??????
        String reqData = "params=" + paramMap.toJSONString();
        _log.info("yeepay_req:{}", reqData);
        String url = mainConfig.getPayUrl() + "/cashier/pc_build?";
        String result = YeePayUtil.call4Post(url + reqData);
        _log.info("yeepay_res:{}", result);
        Map retMap = JSON.parseObject(result);
        if(YeePayUtil.isSuccess(retMap)) {
            // ??????
            String checkSign = PayDigestUtil.getSign(retMap, mchInfo.getPrivateKey(), "sign");
            String retSign = (String) retMap.get("sign");
            //if(checkSign.equals(retSign)) return retMap;
            //_log.info("????????????:retSign={},checkSign={}", retSign, checkSign);
            return retMap;
        }
        return retMap;
    }

    /**
     * ??????UA????????????????????????
     * @param ua
     * @return
     */
    String getChannelId(String ua) {
        String channelId = null;
        if(ua.contains("Alipay")) {
            channelId = PayConstant.PAY_CHANNEL_ALIPAY_WAP;
        }else if(ua.contains("MicroMessenger")) {
            channelId = PayConstant.PAY_CHANNEL_WX_JSAPI;
        }
        return channelId;
    }

    public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray) {
        Map<String, Object> responseMap = new HashMap<>();
        for (int i = 0;i < paramArray.length; i++) {
            String key = paramArray[i];
            String v = request.getParameter(key);
            if (v != null) {
                responseMap.put(key, v);
            }
        }
        return responseMap;
    }


    /**
     * ????????????????????????
     */
    @RequestMapping("/qrcode_img_get")
    public void getQrCodeImg(HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
        JSONObject param = getJsonParam(request);
        String url = getStringRequired(param, "url");
        int width = getIntegerDefault(param, "width", 200);
        int height = getIntegerDefault(param, "height", 200);
        CodeImgUtil.writeQrCode(response.getOutputStream(), url, width, height);
    }

    public boolean verifyPayResponse(Map<String,Object> map) {
        String mchId = (String) map.get("mchId");
        String appId = (String) map.get("appId");
        String payOrderId = (String) map.get("payOrderId");
        String mchOrderNo = (String) map.get("mchOrderNo");
        String amount = (String) map.get("amount");
        String income = (String) map.get("income");
        String sign = (String) map.get("sign");

        if (StringUtils.isEmpty(mchId)) {
            _log.warn("Params error. mchId={}", mchId);
            return false;
        }
        if (StringUtils.isEmpty(payOrderId)) {
            _log.warn("Params error. payOrderId={}", payOrderId);
            return false;
        }
        if (StringUtils.isEmpty(amount) || !NumberUtils.isDigits(amount)) {
            _log.warn("Params error. amount={}", amount);
            return false;
        }
        if (StringUtils.isEmpty(income) || !NumberUtils.isDigits(income)) {
            _log.warn("Params error. income={}", income);
            return false;
        }

        if (StringUtils.isEmpty(sign)) {
            _log.warn("Params error. sign={}", sign);
            return false;
        }

        // ????????????
        if (!verifySign(map)) {
            _log.warn("verify params sign failed. payOrderId={}", payOrderId);
            return false;
        }

        // ??????????????????,????????????????????????
        MchTradeOrder mchTradeOrder = rpcCommonService.rpcMchTradeOrderService.findByTradeOrderId(mchOrderNo);
        if(mchTradeOrder == null) {
            _log.warn("?????????????????????,payOrderId={},mchOrderNo={}", payOrderId, mchOrderNo);
            return false;
        }
        // ????????????
        if(mchTradeOrder.getAmount() != Long.parseLong(amount)) {
            _log.warn("?????????????????????,dbPayPrice={},payPrice={}", mchTradeOrder.getAmount(), amount);
            return false;
        }
        return true;
    }

    public boolean verifySign(Map<String, Object> map) {
        Long mchId = Long.parseLong(map.get("mchId").toString());
        MchInfo mchInfo = rpcCommonService.rpcMchInfoService.findByMchId(mchId);
        if(mchInfo == null) {
            _log.warn("mchInfo not exist. mchId={}", mchId);
            return false;
        }
        String key = mchInfo.getPrivateKey();
        if(StringUtils.isBlank(key)) {
            _log.warn("key is null. mchId={}", mchId);
            return false;
        }
        String localSign = PayDigestUtil.getSign(map, key, "sign");
        String sign = (String) map.get("sign");
        return localSign.equalsIgnoreCase(sign);
    }


}
