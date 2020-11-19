package org.yeepay.pay.channel;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.core.entity.RefundOrder;
import org.yeepay.core.entity.TransOrder;

/**
 * @author: yf
 * @date: 17/12/24
 * @description: 退款接口
 */
public interface RefundInterface {

    /**
     * 申请退款
     * @param refundOrder
     * @return
     */
    JSONObject refund(RefundOrder refundOrder);

    /**
     * 查询退款
     * @param refundOrder
     * @return
     */
    JSONObject query(RefundOrder refundOrder);

}
