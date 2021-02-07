package org.yeepay.pay.channel.wxpay;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.request.WxPayMicropayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayOrderReverseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayMicropayResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.util.SignUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yeepay.core.common.constant.PayConstant;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.pay.channel.BasePayment;
import org.yeepay.pay.mq.BaseNotify4MchPay;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/12/24
 * @description:
 */
@Service
public class WxpayPaymentService extends BasePayment {

    private static final MyLog _log = MyLog.getLog(WxpayPaymentService.class);

    @Override
    public String getChannelName() {
        return PayConstant.CHANNEL_NAME_WXPAY;
    }

    @Autowired
    public BaseNotify4MchPay baseNotify4MchPay;

    @Override
    public JSONObject pay(PayOrder payOrder) {
        String logPrefix = "【微信支付统一下单】";
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        try{
            String channelId = payOrder.getChannelId();
            String tradeType = channelId.substring(channelId.indexOf("_") + 1).toUpperCase();   // 转大写,与微信一致
            WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(getPayParam(payOrder), tradeType, payConfig.getCertRootPath() + File.separator + getChannelName(), payConfig.getNotifyUrl(getChannelName()));
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayUnifiedOrderRequest wxPayUnifiedOrderRequest = buildUnifiedOrderRequest(payOrder, wxPayConfig);
            String payOrderId = payOrder.getPayOrderId();
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult;
            try {
                wxPayUnifiedOrderResult = wxPayService.unifiedOrder(wxPayUnifiedOrderRequest);
                _log.info("{} >>> 下单成功", logPrefix);
                map.put("payOrderId", payOrderId);
                int result = rpcCommonService.rpcPayOrderService.updateStatus4Ing(payOrderId, null);
                _log.info("更新第三方支付订单号:payOrderId={},prepayId={},result={}", payOrderId, wxPayUnifiedOrderResult.getPrepayId(), result);
                switch (tradeType) {
                    case PayConstant.WxConstant.TRADE_TYPE_NATIVE : {
                        JSONObject payInfo = new JSONObject();
                        payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("codeUrl", wxPayUnifiedOrderResult.getCodeURL()); // 二维码支付链接
                        payInfo.put("codeImgUrl", payConfig.getPayUrl() + "/qrcode_img_get?url=" + wxPayUnifiedOrderResult.getCodeURL() + "&widht=200&height=200");
                        payInfo.put("payMethod", PayConstant.PAY_METHOD_CODE_IMG);
                        map.put("payParams", payInfo);
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_APP : {
                        Map<String, String> payInfo = new HashMap<>();
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        // APP支付绑定的是微信开放平台上的账号，APPID为开放平台上绑定APP后发放的参数
                        String wxAppId = wxPayConfig.getAppId();
                        Map<String, String> configMap = new HashMap<>();
                        // 此map用于参与调起sdk支付的二次签名,格式全小写，timestamp只能是10位,格式固定，切勿修改
                        String partnerId = wxPayConfig.getMchId();
                        configMap.put("prepayid", wxPayUnifiedOrderResult.getPrepayId());
                        configMap.put("partnerid", partnerId);
                        String packageValue = "Sign=WXPay";
                        configMap.put("package", packageValue);
                        configMap.put("timestamp", timestamp);
                        configMap.put("noncestr", nonceStr);
                        configMap.put("appid", wxAppId);
                        // 此map用于客户端与微信服务器交互
                        payInfo.put("sign", SignUtils.createSign(configMap, wxPayConfig.getMchKey(), null));
                        payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("partnerId", partnerId);
                        payInfo.put("appId", wxAppId);
                        payInfo.put("package", packageValue);
                        payInfo.put("timeStamp", timestamp);
                        payInfo.put("nonceStr", nonceStr);
                        map.put("payParams", JSONObject.parseObject(JSON.toJSONString(payInfo)));
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_JSPAI : {
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String nonceStr = String.valueOf(System.currentTimeMillis());
                        Map<String, String>  payInfo = new HashMap<>(); // 如果用JsonObject会出现签名错误
                        payInfo.put("appId", wxPayUnifiedOrderResult.getAppid());
                        // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                        payInfo.put("timeStamp", timestamp);
                        payInfo.put("nonceStr", nonceStr);
                        payInfo.put("package", "prepay_id=" + wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("signType", WxPayConstants.SignType.MD5);
                        payInfo.put("paySign", SignUtils.createSign(payInfo, wxPayConfig.getMchKey(), null));
                        // 签名以后在增加prepayId参数
                        payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                        map.put("payParams",  JSONObject.parseObject(JSON.toJSONString(payInfo)));
                        break;
                    }
                    case PayConstant.WxConstant.TRADE_TYPE_MWEB : {
                        JSONObject payInfo = new JSONObject();
                        payInfo.put("prepayId", wxPayUnifiedOrderResult.getPrepayId());
                        payInfo.put("payUrl", wxPayUnifiedOrderResult.getMwebUrl()); // h5支付链接地址
                        map.put("payParams", payInfo);
                        break;
                    }
                }
            } catch (WxPayException e) {
                _log.error(e, "下单失败");
                //出现业务错误
                _log.info("{}下单返回失败", logPrefix);
                _log.info("err_code:{}", e.getErrCode());
                _log.info("err_code_des:{}", e.getErrCodeDes());
                map.put("errDes", e.getErrCodeDes());
                map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
            }
        }catch (Exception e) {
            _log.error(e, "微信支付统一下单异常");
            map.put("errDes", "微信支付统一下单异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }

    @Override
    public JSONObject micropay(PayOrder payOrder, String authCode) {
        String logPrefix = "【微信付款码支付】";
        JSONObject map = new JSONObject();
        map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        try{
            String channelId = payOrder.getChannelId();
            String tradeType = channelId.substring(channelId.indexOf("_") + 1).toUpperCase();   // 转大写,与微信一致
            WxPayConfig wxPayConfig = WxPayUtil.getWxPayConfig(getPayParam(payOrder), tradeType, payConfig.getCertRootPath() + File.separator + getChannelName(), payConfig.getNotifyUrl(getChannelName()));
            WxPayService wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
            WxPayMicropayRequest wxPayMicropayRequest = buildMicropayRequest(payOrder, authCode, wxPayConfig);
            String payOrderId = payOrder.getPayOrderId();
            WxPayMicropayResult wxPayMicropayResult;
            try {
                wxPayMicropayResult = wxPayService.micropay(wxPayMicropayRequest);
                _log.info("{} >>> 付款码下单成功", logPrefix);
                map.put("payOrderId", payOrderId);
                int result = rpcCommonService.rpcPayOrderService.updateStatus4Ing(payOrderId, null);
                _log.info("更新第三方支付订单号:payOrderId={},authCode={},result={}", payOrderId, authCode, result);

                JSONObject payInfo = new JSONObject();
                // 修改支付成功状态
                payOrder.setMchOrderNo(wxPayMicropayResult.getTransactionId());
                Boolean success = paySuccess(payOrder);
                if (success) {
                    _log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                } else {
                    _log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                    map.put(PayConstant.RESPONSE_RESULT, WxPayNotifyResponse.fail("处理订单失败"));
                    return map;
                }

                payInfo.put("transactionId", wxPayMicropayResult.getTransactionId());
                map.put("payParams", payInfo);
            } catch (WxPayException e) {
                _log.error(e, "下单失败");
                //出现业务错误
                _log.info("{}下单返回失败", logPrefix);
                _log.info("err_code:{}", e.getErrCode());
                _log.info("err_code_des:{}", e.getErrCodeDes());
                if (e.getErrCode().equals("USERPAYING")) {
                    int result = rpcCommonService.rpcPayOrderService.updateStatus4Ing(payOrderId, null);
                    _log.info("更新第三方支付订单号:payOrderId={},authCode={},result={}", payOrderId, authCode, result);
                    // 商户系统再轮询调用查询订单接口来确认当前用户是否已经支付成功。
                    for (int i = 10; i > 0; i--) {
                        _log.info("{}轮询{}", logPrefix, i);
                        Thread.sleep(3000);
                        try {
                            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayService.queryOrder(null, wxPayMicropayRequest.getOutTradeNo());
                            JSONObject payInfo = new JSONObject();
                            if (wxPayOrderQueryResult.getTradeState().equals("SUCCESS")) {
                                map.put("payOrderId", payOrderId);

                                // 修改支付成功状态
                                payOrder.setMchOrderNo(wxPayOrderQueryResult.getTransactionId());
                                Boolean success = paySuccess(payOrder);
                                if (success) {
                                    _log.error("{}更新支付状态成功,将payOrderId={},更新payStatus={}成功", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                                } else {
                                    _log.error("{}更新支付状态失败,将payOrderId={},更新payStatus={}失败", logPrefix, payOrder.getPayOrderId(), PayConstant.PAY_STATUS_SUCCESS);
                                    map.put(PayConstant.RESPONSE_RESULT, WxPayNotifyResponse.fail("处理订单失败"));
                                    return map;
                                }

                                payInfo.put("transactionId", wxPayOrderQueryResult.getTransactionId());
                                map.put("payParams", payInfo);
                                break;
                            }
                            if (wxPayOrderQueryResult.getTradeState().equals("USERPAYING") && i > 1) {
                                continue;
                            }

                            WxPayOrderReverseRequest wxPayOrderReverseRequest = buildOrderReverseRequest(payOrder, wxPayConfig);
                            try {
                                _log.info("{}撤销单号{}", logPrefix, payOrder.getPayOrderId());
                                wxPayService.reverseOrder(wxPayOrderReverseRequest);
                            } catch (WxPayException eor) {
                                // 撤销单失败
                                _log.info("{}撤销单号失败{}", logPrefix, payOrder.getPayOrderId());
                            }
                            map.put("errDes", "下单失败[未支付]");
                            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
                            break;
                        } catch (WxPayException eq) {
                            map.put("errDes", eq.getErrCodeDes());
                            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
                            break;
                        }
                    }
                } else {
                    map.put("errDes", e.getErrCodeDes());
                    map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
                }
            }
        }catch (Exception e) {
            _log.error(e, "微信支付统一下单异常");
            map.put("errDes", "微信支付统一下单异常");
            map.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        }
        return map;
    }

    private Boolean paySuccess(PayOrder payOrder) {

        int updatePayOrderRows = rpcCommonService.rpcPayOrderService.updateStatus4Success(payOrder.getPayOrderId(), payOrder.getMchOrderNo());
        if (updatePayOrderRows != 1) {
            return false;
        }
        payOrder.setStatus(PayConstant.PAY_STATUS_SUCCESS);
        // 业务系统后端通知
        baseNotify4MchPay.doNotify(payOrder, true);
        _log.info("====== 完成处理微信支付回调通知 ======");
        return true;
    }

    @Override
    public JSONObject query(PayOrder payOrder) {
        return null;
    }

    @Override
    public JSONObject close(PayOrder payOrder) {
        return null;
    }

    /**
     * 构建微信统一下单请求数据
     * @param payOrder
     * @param wxPayConfig
     * @return
     */
    WxPayUnifiedOrderRequest buildUnifiedOrderRequest(PayOrder payOrder, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getPayOrderId();
        Integer totalFee = payOrder.getAmount().intValue();// 支付金额,单位分
        String deviceInfo = payOrder.getDevice();
        String body = payOrder.getBody();
        String detail = null;
        String attach = null;
        String outTradeNo = payOrderId;
        String feeType = "CNY";
        String spBillCreateIP = payOrder.getClientIp();
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = payConfig.getNotifyUrl(getChannelName());
        String productId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE)) productId = System.currentTimeMillis()+"";
        String limitPay = null;
        String openId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI)) openId = JSON.parseObject(payOrder.getExtra()).getString("openId");
        String sceneInfo = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB)) sceneInfo = JSON.parseObject(payOrder.getExtra()).getString("sceneInfo");
        // 微信统一下单请求对象
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        request.setDeviceInfo(deviceInfo);
        request.setBody(body);
        request.setDetail(detail);
        request.setAttach(attach);
        request.setOutTradeNo(outTradeNo);
        request.setFeeType(feeType);
        request.setTotalFee(totalFee);
        request.setSpbillCreateIp(spBillCreateIP);
        request.setTimeStart(timeStart);
        request.setTimeExpire(timeExpire);
        request.setGoodsTag(goodsTag);
        request.setNotifyURL(notifyUrl);
        request.setTradeType(tradeType);
        request.setProductId(productId);
        request.setLimitPay(limitPay);
        request.setOpenid(openId);
        request.setSceneInfo(sceneInfo);
        return request;
    }


    /**
     * 构建微信付款码请求数据
     * @param payOrder
     * @param wxPayConfig
     * @return
     */
    WxPayMicropayRequest buildMicropayRequest(PayOrder payOrder, String authCode, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getPayOrderId();
        Integer totalFee = payOrder.getAmount().intValue();// 支付金额,单位分
        String deviceInfo = payOrder.getDevice();
        String body = payOrder.getBody();
        String detail = null;
        String attach = null;
        String outTradeNo = payOrderId;
        String feeType = "CNY";
        String spBillCreateIP = payOrder.getClientIp();
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = payConfig.getNotifyUrl(getChannelName());
        String productId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE)) productId = System.currentTimeMillis()+"";
        String limitPay = null;
        String openId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI)) openId = JSON.parseObject(payOrder.getExtra()).getString("openId");
        String sceneInfo = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB)) sceneInfo = JSON.parseObject(payOrder.getExtra()).getString("sceneInfo");
        // 微信统一下单请求对象
        WxPayMicropayRequest request = WxPayMicropayRequest.newBuilder().build();
//        request.setDeviceInfo(deviceInfo);
        request.setAuthCode(authCode);
        request.setBody(body);
        request.setDetail(detail);
        request.setAttach(attach);
        request.setOutTradeNo(outTradeNo);
        request.setFeeType(feeType);
        request.setTotalFee(totalFee);
        request.setSpbillCreateIp(spBillCreateIP);
//        request.setTimeStart(timeStart);
//        request.setTimeExpire(timeExpire);
        request.setGoodsTag(goodsTag);
//        request.setNotifyURL(notifyUrl);
//        request.setTradeType(tradeType);
//        request.setProductId(productId);
        request.setLimitPay(limitPay);
//        request.setOpenid(openId);
//        request.setSceneInfo(sceneInfo);
        return request;
    }

    /**
     * 构建微信撤销单请求数据
     * @param payOrder
     * @param wxPayConfig
     * @return
     */
    WxPayOrderReverseRequest buildOrderReverseRequest(PayOrder payOrder, WxPayConfig wxPayConfig) {
        String tradeType = wxPayConfig.getTradeType();
        String payOrderId = payOrder.getPayOrderId();
        Integer totalFee = payOrder.getAmount().intValue();// 支付金额,单位分
        String deviceInfo = payOrder.getDevice();
        String body = payOrder.getBody();
        String detail = null;
        String attach = null;
        String outTradeNo = payOrderId;
        String feeType = "CNY";
        String spBillCreateIP = payOrder.getClientIp();
        String timeStart = null;
        String timeExpire = null;
        String goodsTag = null;
        String notifyUrl = payConfig.getNotifyUrl(getChannelName());
        String productId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_NATIVE)) productId = System.currentTimeMillis()+"";
        String limitPay = null;
        String openId = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_JSPAI)) openId = JSON.parseObject(payOrder.getExtra()).getString("openId");
        String sceneInfo = null;
        if(tradeType.equals(PayConstant.WxConstant.TRADE_TYPE_MWEB)) sceneInfo = JSON.parseObject(payOrder.getExtra()).getString("sceneInfo");
        // 微信统一下单请求对象
        WxPayOrderReverseRequest request = WxPayOrderReverseRequest.newBuilder().build();
//        request.setDeviceInfo(deviceInfo);
//        request.setAuthCode(authCode);
//        request.setBody(body);
//        request.setDetail(detail);
//        request.setAttach(attach);
        request.setOutTradeNo(outTradeNo);
//        request.setFeeType(feeType);
//        request.setTotalFee(totalFee);
//        request.setSpbillCreateIp(spBillCreateIP);
//        request.setTimeStart(timeStart);
//        request.setTimeExpire(timeExpire);
//        request.setGoodsTag(goodsTag);
//        request.setNotifyURL(notifyUrl);
//        request.setTradeType(tradeType);
//        request.setProductId(productId);
//        request.setLimitPay(limitPay);
//        request.setOpenid(openId);
//        request.setSceneInfo(sceneInfo);
        return request;
    }
}
