package org.yeepay.service.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.AgentInfo;
import org.yeepay.core.entity.AgentInfoExample;

public interface AgentInfoMapper {
    int countByExample(AgentInfoExample example);

    int deleteByExample(AgentInfoExample example);

    int deleteByPrimaryKey(Long agentId);

    int insert(AgentInfo record);

    int insertSelective(AgentInfo record);

    List<AgentInfo> selectByExample(AgentInfoExample example);

    AgentInfo selectByPrimaryKey(Long agentId);

    int updateByExampleSelective(@Param("record") AgentInfo record, @Param("example") AgentInfoExample example);

    int updateByExample(@Param("record") AgentInfo record, @Param("example") AgentInfoExample example);

    int updateByPrimaryKeySelective(AgentInfo record);

    int updateByPrimaryKey(AgentInfo record);

    /**
     * 统计代理商信息
     * @param param
     * @return
     */
    Map count4Agent(Map param);
}