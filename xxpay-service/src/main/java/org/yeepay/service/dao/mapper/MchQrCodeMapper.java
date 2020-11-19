package org.yeepay.service.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.yeepay.core.entity.MchQrCode;
import org.yeepay.core.entity.MchQrCodeExample;

import java.util.List;

public interface MchQrCodeMapper {
    int countByExample(MchQrCodeExample example);

    int deleteByExample(MchQrCodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MchQrCode record);

    int insertSelective(MchQrCode record);

    List<MchQrCode> selectByExample(MchQrCodeExample example);

    MchQrCode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MchQrCode record, @Param("example") MchQrCodeExample example);

    int updateByExample(@Param("record") MchQrCode record, @Param("example") MchQrCodeExample example);

    int updateByPrimaryKeySelective(MchQrCode record);

    int updateByPrimaryKey(MchQrCode record);
}