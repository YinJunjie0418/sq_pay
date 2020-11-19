package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.MchSettRecord;
import org.yeepay.core.entity.MchSettRecordExample;

import java.util.List;

public interface MchSettRecordMapper {
    int countByExample(MchSettRecordExample example);

    int deleteByExample(MchSettRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MchSettRecord record);

    int insertSelective(MchSettRecord record);

    List<MchSettRecord> selectByExample(MchSettRecordExample example);

    MchSettRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MchSettRecord record, @Param("example") MchSettRecordExample example);

    int updateByExample(@Param("record") MchSettRecord record, @Param("example") MchSettRecordExample example);

    int updateByPrimaryKeySelective(MchSettRecord record);

    int updateByPrimaryKey(MchSettRecord record);
}