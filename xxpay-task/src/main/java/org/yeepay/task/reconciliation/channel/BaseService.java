package org.yeepay.task.reconciliation.channel;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.common.constant.PayConstant;

/**
 * @author: yf
 * @date: 17/12/24
 * @description:
 */
public class BaseService {

    protected JSONObject buildRetObj() {
        JSONObject retObj = new JSONObject();
        retObj.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_SUCCESS);
        return retObj;
    }

    protected JSONObject buildFailRetObj() {
        JSONObject retObj = new JSONObject();
        retObj.put(PayConstant.RETURN_PARAM_RETCODE, PayConstant.RETURN_VALUE_FAIL);
        return retObj;
    }

    protected JSONObject buildRetObj(String retValue, String retMsg) {
        JSONObject retObj = new JSONObject();
        retObj.put(PayConstant.RETURN_PARAM_RETCODE, retValue);
        retObj.put(PayConstant.RETURN_PARAM_RETMSG, retMsg);
        return retObj;
    }

}
