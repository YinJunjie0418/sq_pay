package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.core.entity.PayPassageAccount;
import org.yeepay.pay.service.RpcCommonService;
import org.yeepay.pay.service.RpcCommonService;

import java.io.File;

/**
 * @author: yf
 * @date: 17/12/24
 * @description:
 */
@Component
public abstract class BasePayment extends BaseService implements PaymentInterface {

    @Autowired
    public RpcCommonService rpcCommonService;

    @Autowired
    public PayConfig payConfig;

    public abstract String getChannelName();

    public String getOrderId(PayOrder payOrder) {
        return null;
    }

    public Long getAmount(PayOrder payOrder) {
        return null;
    }

    public JSONObject pay(PayOrder payOrder) {
        return null;
    }

    public JSONObject query(PayOrder payOrder) {
        return null;
    }

    public JSONObject close(PayOrder payOrder) {
        return null;
    }

    /**
     * 获取三方支付配置信息
     * 如果是平台账户,则使用平台对应的配置,否则使用商户自己配置的渠道
     * @param payOrder
     * @return
     */
    public String getPayParam(PayOrder payOrder) {
        String payParam = "";
        PayPassageAccount payPassageAccount = rpcCommonService.rpcPayPassageAccountService.findById(payOrder.getPassageAccountId());
        if(payPassageAccount != null && payPassageAccount.getStatus() == MchConstant.PUB_YES) {
            payParam = payPassageAccount.getParam();
        }
        if(StringUtils.isBlank(payParam)) {
            throw new ServiceException(RetEnum.RET_MGR_PAY_PASSAGE_ACCOUNT_NOT_EXIST);
        }
        return payParam;
    }

}
