package org.yeepay.manage.order.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.domain.YeePayPageRes;
import org.yeepay.core.entity.PayOrderCashCollRecord;
import org.yeepay.manage.common.ctrl.BaseController;
import org.yeepay.manage.common.service.RpcCommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(Constant.MGR_CONTROLLER_ROOT_PATH + "/pay_cash_coll_record")
public class PayOrderCashCollRecordController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        PayOrderCashCollRecord record = getObject(param, PayOrderCashCollRecord.class);
        int count = rpcCommonService.rpcPayOrderCashCollRecordService.count(record);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<PayOrderCashCollRecord> result = rpcCommonService.rpcPayOrderCashCollRecordService.select((getPageIndex(param) -1) * getPageSize(param), getPageSize(param), record);
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(result, count));
    }
}
