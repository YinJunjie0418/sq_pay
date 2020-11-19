package org.yeepay.service.dao.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.AgentAccount;
import org.yeepay.core.entity.AgentAccountExample;

public interface AgentAccountMapper {
    int countByExample(AgentAccountExample example);

    int deleteByExample(AgentAccountExample example);

    int deleteByPrimaryKey(Long agentId);

    int insert(AgentAccount record);

    int insertSelective(AgentAccount record);

    List<AgentAccount> selectByExample(AgentAccountExample example);

    AgentAccount selectByPrimaryKey(Long agentId);

    int updateByExampleSelective(@Param("record") AgentAccount record, @Param("example") AgentAccountExample example);

    int updateByExample(@Param("record") AgentAccount record, @Param("example") AgentAccountExample example);

    int updateByPrimaryKeySelective(AgentAccount record);

    int updateByPrimaryKey(AgentAccount record);

    /**
     * 更新代理商账户结算金额
     * @param map
     * @return
     */
    int updateSettAmount(Map map);

    BigDecimal sumAgentBalance(AgentAccountExample exa);

}