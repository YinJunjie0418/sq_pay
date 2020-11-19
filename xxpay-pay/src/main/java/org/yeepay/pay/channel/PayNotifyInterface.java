package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.entity.PayOrder;

/**
 * @author: yf
 * @date: 17/12/24
 * @description:
 */
public interface PayNotifyInterface {

    JSONObject doNotify(Object notifyData);

    JSONObject doReturn(Object notifyData);

}
