package org.yeepay.task.reconciliation.channel;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.entity.CheckBatch;

/**
 * @author: yf
 * @date: 18/1/18
 * @description:
 */
public interface BillInterface {

    JSONObject downloadBill(JSONObject param, CheckBatch batch);

}
