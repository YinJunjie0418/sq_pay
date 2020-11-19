package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.SysConfig;
import org.yeepay.core.entity.SysConfigExample;

import java.util.List;

public interface SysConfigMapper {
    int countByExample(SysConfigExample example);

    int deleteByExample(SysConfigExample example);

    int deleteByPrimaryKey(String code);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    List<SysConfig> selectByExample(SysConfigExample example);

    SysConfig selectByPrimaryKey(String code);

    int updateByExampleSelective(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    int updateByExample(@Param("record") SysConfig record, @Param("example") SysConfigExample example);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}