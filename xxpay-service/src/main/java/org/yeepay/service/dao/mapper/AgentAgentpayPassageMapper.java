package org.yeepay.service.dao.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.AgentAgentpayPassage;
import org.yeepay.core.entity.AgentAgentpayPassageExample;

public interface AgentAgentpayPassageMapper {
    int countByExample(AgentAgentpayPassageExample example);

    int deleteByExample(AgentAgentpayPassageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AgentAgentpayPassage record);

    int insertSelective(AgentAgentpayPassage record);

    List<AgentAgentpayPassage> selectByExample(AgentAgentpayPassageExample example);

    AgentAgentpayPassage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AgentAgentpayPassage record, @Param("example") AgentAgentpayPassageExample example);

    int updateByExample(@Param("record") AgentAgentpayPassage record, @Param("example") AgentAgentpayPassageExample example);

    int updateByPrimaryKeySelective(AgentAgentpayPassage record);

    int updateByPrimaryKey(AgentAgentpayPassage record);
}