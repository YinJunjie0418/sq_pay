package org.yeepay.core.service;

import org.yeepay.core.entity.PayInterface;

import java.util.List;

/**
 * @author: yf
 * @date: 18/5/3
 * @description: 支付接口
 */
public interface IPayInterfaceService {

    int add(PayInterface payInterface);

    int update(PayInterface payInterface);

    PayInterface findByCode(String ifCode);

    List<PayInterface> select(int offset, int limit, PayInterface payInterface);

    Integer count(PayInterface payInterface);

    List<PayInterface> selectAll(PayInterface payInterface);

    /**
     * 根据接口类型代码查询接口列表
     * @param ifTypeCode
     * @return
     */
    List<PayInterface> selectAllByTypeCode(String ifTypeCode);

}
