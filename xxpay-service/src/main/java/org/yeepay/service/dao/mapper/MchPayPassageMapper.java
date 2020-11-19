package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.MchPayPassage;
import org.yeepay.core.entity.MchPayPassageExample;

import java.util.List;

public interface MchPayPassageMapper {
    int countByExample(MchPayPassageExample example);

    int deleteByExample(MchPayPassageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MchPayPassage record);

    int insertSelective(MchPayPassage record);

    List<MchPayPassage> selectByExample(MchPayPassageExample example);

    MchPayPassage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MchPayPassage record, @Param("example") MchPayPassageExample example);

    int updateByExample(@Param("record") MchPayPassage record, @Param("example") MchPayPassageExample example);

    int updateByPrimaryKeySelective(MchPayPassage record);

    int updateByPrimaryKey(MchPayPassage record);
}