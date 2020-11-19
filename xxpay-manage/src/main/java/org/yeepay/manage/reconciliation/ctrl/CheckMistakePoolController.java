package org.yeepay.manage.reconciliation.ctrl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.domain.YeePayPageRes;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.entity.CheckBatch;
import org.yeepay.core.entity.CheckMistakePool;
import org.yeepay.manage.common.ctrl.BaseController;
import org.yeepay.manage.common.service.RpcCommonService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: yf
 * @date: 18/1/21
 * @description:
 */
@Controller
@RequestMapping(Constant.MGR_CONTROLLER_ROOT_PATH + "/bill/check_mistake_pool")
public class CheckMistakePoolController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    /**
     * 查询单条
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?> get(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        Long id = getLongRequired(param, "id");
        CheckMistakePool checkMistakePool = rpcCommonService.rpcCheckService.findByMistakePoolId(id);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(checkMistakePool));
    }

    /**
     * 记录列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        Integer page = getInteger(param, "page");
        Integer limit = getInteger(param, "limit");
        CheckMistakePool checkMistakePool = getObject(param, CheckMistakePool.class);
        int count = rpcCommonService.rpcCheckService.countCheckMistakePool(checkMistakePool);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<CheckMistakePool> checkMistakePoolList = rpcCommonService.rpcCheckService.selectCheckMistakePool(
                (getPageIndex(page) -1) * getPageSize(limit), getPageSize(limit), checkMistakePool);
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(checkMistakePoolList, count));
    }

}
