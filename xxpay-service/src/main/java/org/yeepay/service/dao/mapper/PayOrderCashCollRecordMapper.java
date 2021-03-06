package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.PayOrderCashCollRecord;
import org.yeepay.core.entity.PayOrderCashCollRecordExample;

import java.util.List;

public interface PayOrderCashCollRecordMapper {
    int countByExample(PayOrderCashCollRecordExample example);

    int deleteByExample(PayOrderCashCollRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayOrderCashCollRecord record);

    int insertSelective(PayOrderCashCollRecord record);

    List<PayOrderCashCollRecord> selectByExample(PayOrderCashCollRecordExample example);

    PayOrderCashCollRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayOrderCashCollRecord record, @Param("example") PayOrderCashCollRecordExample example);

    int updateByExample(@Param("record") PayOrderCashCollRecord record, @Param("example") PayOrderCashCollRecordExample example);

    int updateByPrimaryKeySelective(PayOrderCashCollRecord record);

    int updateByPrimaryKey(PayOrderCashCollRecord record);
}