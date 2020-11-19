package org.yeepay.core.service;

import org.yeepay.core.entity.MchQrCode;

import java.util.List;

/**
 * @author: yf
 * @date: 17/12/21
 * @description:
 */
public interface IMchQrCodeService {

    List<MchQrCode> select(int pageIndex, int pageSize, MchQrCode mchQrCode);

    int count(MchQrCode mchQrCode);

    MchQrCode findById(Long id);

    MchQrCode find(MchQrCode mchQrCode);

    MchQrCode findByMchIdAndAppId(Long mchId, String appId);

    int add(MchQrCode mchQrCode);

    int update(MchQrCode mchQrCode);

    int delete(Long id);

}
