package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;

/**
 * @author: yf
 * @date: 18/08/16
 * @description: 转账通知接口
 */
public interface TransNotifyInterface {

    JSONObject doNotify(Object notifyData);

}
