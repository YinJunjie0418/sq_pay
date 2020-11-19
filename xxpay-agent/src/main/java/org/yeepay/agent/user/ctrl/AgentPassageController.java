package org.yeepay.agent.user.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.agent.common.ctrl.BaseController;
import org.yeepay.agent.common.service.RpcCommonService;
import org.yeepay.core.common.constant.Constant;
import org.yeepay.core.common.domain.YeePayResponse;
import org.yeepay.core.entity.AgentPassage;
import org.yeepay.core.entity.PayProduct;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: yf
 * @date: 18/05/06
 * @description: 代理商支付通道配置
 */
@RestController
@RequestMapping(Constant.AGENT_CONTROLLER_ROOT_PATH + "/agent_passage")
public class AgentPassageController extends BaseController {

    @Autowired
    private RpcCommonService rpcCommonService;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<?> list(HttpServletRequest request) {
        Long agentId = getUser().getId();
        // 得到代理商已经配置的支付通道
        List<AgentPassage> agentPassageList = rpcCommonService.rpcAgentPassageService.selectAllByAgentId(agentId);
        // 支付产品很多时,要考虑内存溢出问题
        List<PayProduct> payProductList = rpcCommonService.rpcPayProductService.selectAll();
        Map<String, PayProduct> payProductMap = new HashMap<>();
        for(PayProduct product : payProductList) {
            payProductMap.put(String.valueOf(product.getId()), product);
        }
        List<JSONObject> objects = new LinkedList<>();
        for(AgentPassage agentPassage : agentPassageList) {
            JSONObject object = (JSONObject) JSON.toJSON(agentPassage);
            if(payProductMap.get(String.valueOf(agentPassage.getProductId())) != null) {
                object.put("productName", payProductMap.get(String.valueOf(agentPassage.getProductId())).getProductName());
            }
            objects.add(object);
        }
        return ResponseEntity.ok(YeePayResponse.buildSuccess(objects));
    }

}
