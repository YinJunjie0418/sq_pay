package org.yeepay.agent.user.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.agent.common.ctrl.BaseController;
import org.yeepay.agent.common.service.RpcCommonService;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.domain.YeePayPageRes;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.entity.AgentAccount;
import org.yeepay.core.entity.AgentAccountHistory;
import org.yeepay.core.entity.MchAccount;
import org.yeepay.core.entity.MchAccountHistory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: yf
 * @date: 17/12/6
 * @description:
 */
@RestController
@RequestMapping(Constant.AGENT_CONTROLLER_ROOT_PATH + "/account")
@PreAuthorize("hasRole('"+ MchConstant.AGENT_ROLE_NORMAL+"')")
public class AccountController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;
    /**
     * 查询账户信息
     * @return
     */
    @RequestMapping("/get")
    @ResponseBody
    public ResponseEntity<?> get() {
        AgentAccount agentAccount = rpcCommonService.rpcAgentAccountService.findByAgentId(getUser().getId());
        JSONObject object = (JSONObject) JSON.toJSON(agentAccount);
        object.put("availableBalance", agentAccount.getAvailableBalance());       // 可用余额
        object.put("availableSettAmount", agentAccount.getAvailableSettAmount()); // 可结算金额
        return ResponseEntity.ok(YeePayResponse.buildSuccess(object));
    }

    /**
     * 查询资金流水列表
     * @return
     */
    @RequestMapping("/history_list")
    @ResponseBody
    public ResponseEntity<?> historyList(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        AgentAccountHistory agentAccountHistory = getObject(param, AgentAccountHistory.class);
        if(agentAccountHistory == null) agentAccountHistory = new AgentAccountHistory();
        agentAccountHistory.setAgentId(getUser().getId());
        int count = rpcCommonService.rpcAgentAccountHistoryService.count(agentAccountHistory);
        if(count == 0) return ResponseEntity.ok(YeePayPageRes.buildSuccess());
        List<AgentAccountHistory> agentAccountHistoryList = rpcCommonService.rpcAgentAccountHistoryService
                .select((getPageIndex(param) -1) * getPageSize(param), getPageSize(param), agentAccountHistory);
        return ResponseEntity.ok(YeePayPageRes.buildSuccess(agentAccountHistoryList, count));
    }

    /**
     * 查询资金流水
     * @return
     */
    @RequestMapping("/history_get")
    @ResponseBody
    public ResponseEntity<?> historyGet(HttpServletRequest request) {
        JSONObject param = getJsonParam(request);
        Long id = getLongRequired(param, "id");
        AgentAccountHistory agentAccountHistory = rpcCommonService.rpcAgentAccountHistoryService
                .findByAgentIdAndId(getUser().getId(), id);
        return ResponseEntity.ok(YeePayResponse.buildSuccess(agentAccountHistory));
    }

}
