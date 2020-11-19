package org.yeepay.core.service;

/**
 * @author: yf
 * @date: 2018/5/29
 * @description:
 */
public interface IYeePayNotifyService {

	/**
	 * 发送支付订单通知
	 * @param payOrderId
	 */
	void executePayNotify(String payOrderId);

}
