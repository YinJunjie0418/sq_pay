package org.yeepay.core.service;

import org.yeepay.core.entity.AgentpayPassage;

import java.util.List;

/**
 * @author: yf
 * @date: 18/5/3
 * @description: 代付通道
 */
public interface IAgentpayPassageService {

    int add(AgentpayPassage agentpayPassage);

    int update(AgentpayPassage agentpayPassage);

    AgentpayPassage findById(Integer id);

    List<AgentpayPassage> select(int offset, int limit, AgentpayPassage agentpayPassage);

    Integer count(AgentpayPassage agentpayPassage);

    List<AgentpayPassage> selectAll(AgentpayPassage agentpayPassage);

}
