package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.PayType;
import org.yeepay.core.entity.PayTypeExample;

import java.util.List;

public interface PayTypeMapper {
    int countByExample(PayTypeExample example);

    int deleteByExample(PayTypeExample example);

    int deleteByPrimaryKey(String payTypeCode);

    int insert(PayType record);

    int insertSelective(PayType record);

    List<PayType> selectByExample(PayTypeExample example);

    PayType selectByPrimaryKey(String payTypeCode);

    int updateByExampleSelective(@Param("record") PayType record, @Param("example") PayTypeExample example);

    int updateByExample(@Param("record") PayType record, @Param("example") PayTypeExample example);

    int updateByPrimaryKeySelective(PayType record);

    int updateByPrimaryKey(PayType record);
}