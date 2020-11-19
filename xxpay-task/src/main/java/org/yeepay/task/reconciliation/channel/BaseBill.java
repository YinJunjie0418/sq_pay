package org.yeepay.task.reconciliation.channel;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeepay.core.entity.CheckBatch;
import org.yeepay.task.common.service.RpcCommonService;

/**
 * @author: yf
 * @date: 17/12/24
 * @description:
 */
@Component
public abstract class BaseBill extends BaseService implements BillInterface {

    @Autowired
    public RpcCommonService rpcCommonService;

    public String channelName;

    public abstract String getChannelName();

    public JSONObject downloadBill(JSONObject param, CheckBatch batch) {
        return null;
    }

}
