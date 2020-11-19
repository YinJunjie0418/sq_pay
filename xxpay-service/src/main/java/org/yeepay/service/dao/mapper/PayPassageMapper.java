package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.PayPassage;
import org.yeepay.core.entity.PayPassageExample;

import java.util.List;

public interface PayPassageMapper {
    int countByExample(PayPassageExample example);

    int deleteByExample(PayPassageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayPassage record);

    int insertSelective(PayPassage record);

    List<PayPassage> selectByExample(PayPassageExample example);

    PayPassage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayPassage record, @Param("example") PayPassageExample example);

    int updateByExample(@Param("record") PayPassage record, @Param("example") PayPassageExample example);

    int updateByPrimaryKeySelective(PayPassage record);

    int updateByPrimaryKey(PayPassage record);
}