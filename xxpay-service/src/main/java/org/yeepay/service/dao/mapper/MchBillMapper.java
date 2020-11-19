package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.MchBill;
import org.yeepay.core.entity.MchBillExample;

import java.util.List;

public interface MchBillMapper {
    int countByExample(MchBillExample example);

    int deleteByExample(MchBillExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MchBill record);

    int insertSelective(MchBill record);

    List<MchBill> selectByExample(MchBillExample example);

    MchBill selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MchBill record, @Param("example") MchBillExample example);

    int updateByExample(@Param("record") MchBill record, @Param("example") MchBillExample example);

    int updateByPrimaryKeySelective(MchBill record);

    int updateByPrimaryKey(MchBill record);
}