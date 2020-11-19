package org.yeepay.core.service;

import org.yeepay.core.entity.AgentAccountHistory;
import org.yeepay.core.entity.MchAccountHistory;

import java.util.List;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/12/4
 * @description: 代理商账户流水记录
 */
public interface IAgentAccountHistoryService {

    List<AgentAccountHistory> select(int offset, int limit, AgentAccountHistory agentAccountHistory);

    int count(AgentAccountHistory agentAccountHistory);

    AgentAccountHistory findById(Long id);

    AgentAccountHistory findByAgentIdAndId(Long agentId, Long id);

    /**
     * 统计代理商分润
     * @return
     */
    List<Map> count4AgentProfit(Long agentId);
}
