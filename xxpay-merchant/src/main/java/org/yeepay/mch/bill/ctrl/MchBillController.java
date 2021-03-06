package org.yeepay.mch.bill.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.yeepay.core.entity.MchBill;
import org.yeepay.mch.common.ctrl.BaseController;
import org.yeepay.mch.common.service.RpcCommonService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author: yf
 * @date: 18/02/07
 * @description:
 */
@RestController
@RequestMapping(Constant.MCH_CONTROLLER_ROOT_PATH + "/bill")
@PreAuthorize("hasRole('"+ MchConstant.MCH_ROLE_NORMAL+"')")
public class MchBillController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    @Value("${config.downMchBillUrl}")
    private String downBillUrl;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        MchBill mchBill = getObject(param, MchBill.class);
        if(mchBill == null) mchBill = new MchBill();
        mchBill.setMchId(getUser().getId());
        int count = rpcCommonService.rpcMchBillService.count(mchBill);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<MchBill> mchBillList = rpcCommonService.rpcMchBillService.select((getPageIndex(param) - 1) * getPageSize(param), getPageSize(param), mchBill);
        for(MchBill bill : mchBillList) {
            // ????????????,??????????????????URL
            if(MchConstant.MCH_BILL_STATUS_COMPLETE == bill.getStatus()) {
                bill.setBillPath(downBillUrl + File.separator + DateUtil.date2Str(bill.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD) + File.separator + bill.getMchId() + ".csv");
            }
        }
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(mchBillList, count));
    }

    /**
     * ??????????????????
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?> get(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        Long id = getLongRequired(param, "id");
        MchBill mchBill = rpcCommonService.rpcMchBillService.findByMchIdAndId(getUser().getId(), id);
        if(MchConstant.MCH_BILL_STATUS_COMPLETE == mchBill.getStatus()) {
            mchBill.setBillPath(downBillUrl + File.separator + DateUtil.date2Str(mchBill.getBillDate(), DateUtil.FORMAT_YYYY_MM_DD) + File.separator + mchBill.getMchId() + ".csv");
        }
        return ResponseEntity.ok(YeePayResponse.buildSuccess(mchBill));
    }

}
