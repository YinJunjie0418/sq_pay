package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.PayPassageAccount;
import org.yeepay.core.entity.PayPassageAccountExample;

import java.util.List;

public interface PayPassageAccountMapper {
    int countByExample(PayPassageAccountExample example);

    int deleteByExample(PayPassageAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayPassageAccount record);

    int insertSelective(PayPassageAccount record);

    List<PayPassageAccount> selectByExample(PayPassageAccountExample example);

    PayPassageAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayPassageAccount record, @Param("example") PayPassageAccountExample example);

    int updateByExample(@Param("record") PayPassageAccount record, @Param("example") PayPassageAccountExample example);

    int updateByPrimaryKeySelective(PayPassageAccount record);

    int updateByPrimaryKey(PayPassageAccount record);
}