package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.entity.AgentpayPassageAccount;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.core.entity.PayPassageAccount;
import org.yeepay.core.entity.TransOrder;
import org.yeepay.pay.mq.BaseNotify4MchPay;
import org.yeepay.pay.mq.BaseNotify4MchTrans;
import org.yeepay.pay.service.RpcCommonService;
import org.yeepay.pay.service.RpcCommonService;

import java.io.File;

/**
 * @author: yf
 * @date: 18/08/16
 * @description:
 */
@Component
public abstract class BaseTransNotify extends BaseService implements TransNotifyInterface {

    @Autowired
    public RpcCommonService rpcCommonService;

    @Autowired
    public PayConfig payConfig;

    @Autowired
    public BaseNotify4MchTrans baseNotify4MchTrans;

    public abstract String getChannelName();

    public JSONObject doNotify(Object notifyData) {
        return null;
    }

    /**
     * 获取三方支付配置信息
     * 如果是平台账户,则使用平台对应的配置,否则使用商户自己配置的渠道
     * @param transOrder
     * @return
     */
    public String getTransParam(TransOrder transOrder) {
        String payParam = "";
        AgentpayPassageAccount agentpayPassageAccount = rpcCommonService.rpcAgentpayPassageAccountService.findById(transOrder.getPassageAccountId());
        if(agentpayPassageAccount != null && agentpayPassageAccount.getStatus() == MchConstant.PUB_YES) {
            payParam = agentpayPassageAccount.getParam();
        }
        if(StringUtils.isBlank(payParam)) {
            throw new ServiceException(RetEnum.RET_MGR_PAY_PASSAGE_ACCOUNT_NOT_EXIST);
        }
        return payParam;
    }

}
