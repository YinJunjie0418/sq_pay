package org.yeepay.manage.order.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.domain.YeePayPageRes;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.common.util.DateUtil;
import org.yeepay.core.entity.RefundOrder;
import org.yeepay.manage.common.service.RpcCommonService;
import org.yeepay.manage.common.ctrl.BaseController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author: yf
 * @date: 17/12/6
 * @description:
 */
@RestController
@RequestMapping(Constant.MGR_CONTROLLER_ROOT_PATH + "/refund_order")
public class RefundOrderController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    /**
     * 查询单条退款记录
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?> get(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        String refundOrderId = getStringRequired(param, "refundOrderId");
        RefundOrder refundOrder = rpcCommonService.rpcRefundOrderService.findByRefundOrderId(refundOrderId);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(refundOrder));
    }

    /**
     * 退款订单记录列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        RefundOrder refundOrder = getObject(param, RefundOrder.class);
        // 订单起止时间
        Date createTimeStart = null;
        Date createTimeEnd = null;
        String createTimeStartStr = getString(param, "createTimeStart");
        if(StringUtils.isNotBlank(createTimeStartStr)) createTimeStart = DateUtil.str2date(createTimeStartStr);
        String createTimeEndStr = getString(param, "createTimeEnd");
        if(StringUtils.isNotBlank(createTimeEndStr)) createTimeEnd = DateUtil.str2date(createTimeEndStr);

        int count = rpcCommonService.rpcRefundOrderService.count(refundOrder, createTimeStart, createTimeEnd);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<RefundOrder> refundOrderList = rpcCommonService.rpcRefundOrderService.select((getPageIndex(param) -1) * getPageSize(param),
                getPageSize(param), refundOrder, createTimeStart, createTimeEnd);
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(refundOrderList, count));
    }

}
