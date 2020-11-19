package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.entity.PayOrder;

/**
 * 资金归集
 */
public interface CashCollInterface {

    JSONObject coll(PayOrder payOrder);

}
