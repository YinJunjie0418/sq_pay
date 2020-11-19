package org.yeepay.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.core.service.IYeePayNotifyService;
import org.yeepay.pay.mq.BaseNotify4MchPay;
import org.yeepay.pay.mq.Mq4MchAgentpayNotify;
import org.yeepay.pay.mq.Mq4MchPayNotify;
import org.yeepay.pay.service.RpcCommonService;

/**
 * @author: yf
 * @date: 2018/5/29
 * @description:
 */
@Service(interfaceName = "org.yeepay.core.service.IYeePayNotifyService", version = "1.0.0", retries = -1)
public class YeePayNotifyServiceImpl implements IYeePayNotifyService {

    private static final MyLog _log = MyLog.getLog(YeePayNotifyServiceImpl.class);

    @Autowired
    private RpcCommonService rpcCommonService;

	@Autowired
	public BaseNotify4MchPay baseNotify4MchPay;
    
	/**
	 * 发送支付订单通知
	 * @param payOrderId
	 */
	public void executePayNotify(String payOrderId) {
		_log.info(">>>>>> 调取rpc补发支付通知,payOrderId：{}", payOrderId);
		PayOrder payOrder = rpcCommonService.rpcPayOrderService.findByPayOrderId(payOrderId);
		baseNotify4MchPay.doNotify(payOrder, true);
		_log.info(">>>>>> 调取rpc补发支付通知完成  <<<<<<");
	}



}
