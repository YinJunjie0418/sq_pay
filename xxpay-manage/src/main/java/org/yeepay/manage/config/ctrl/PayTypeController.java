package org.yeepay.manage.config.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.core.common.annotation.MethodLog;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.domain.BizResponse;
import org.yeepay.core.common.domain.YeePayPageRes;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.entity.PayType;
import org.yeepay.manage.common.ctrl.BaseController;
import org.yeepay.manage.common.service.RpcCommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: yifu
 * @date: 18/08/28
 * @description: 支付类型
 */
@RestController
@RequestMapping(Constant.MGR_CONTROLLER_ROOT_PATH + "/config/pay_type")
public class PayTypeController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        PayType payType = getObject(param, PayType.class);
        int count = rpcCommonService.rpcPayTypeService.count(payType);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<PayType> payTypeList = rpcCommonService.rpcPayTypeService.select((getPageIndex(param) -1) * getPageSize(param), getPageSize(param), payType);
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(payTypeList, count));
    }

    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?> get(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        String payTypeCode = getStringRequired(param, "payTypeCode");
        PayType payType = rpcCommonService.rpcPayTypeService.findByPayTypeCode(payTypeCode);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(payType));
    }

    @RequestMapping("/update")
    @ResponseBody
    @MethodLog( remark = "修改支付类型" )
    public ResponseEntity<?> update(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        PayType payType = getObject(param, PayType.class);
        int count = rpcCommonService.rpcPayTypeService.update(payType);
        if(count == 1) return ResponseEntity.ok(BizResponse.buildSuccess());
        return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_OPERATION_FAIL));
    }

    @RequestMapping("/add")
    @ResponseBody
    @MethodLog( remark = "新增支付类型" )
    public ResponseEntity<?> add(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        PayType payType = getObject(param, PayType.class);
        int count = rpcCommonService.rpcPayTypeService.add(payType);
        if(count == 1) return ResponseEntity.ok(BizResponse.buildSuccess());
        return ResponseEntity.ok(BizResponse.build(RetEnum.RET_COMM_OPERATION_FAIL));
    }

}
