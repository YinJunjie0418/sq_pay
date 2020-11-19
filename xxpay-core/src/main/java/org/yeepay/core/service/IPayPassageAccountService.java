package org.yeepay.core.service;

import org.yeepay.core.entity.PayPassageAccount;

import java.util.List;

/**
 * @author: yf
 * @date: 18/5/3
 * @description: 支付通道账户
 */
public interface IPayPassageAccountService {

    int add(PayPassageAccount payPassageAccount);

    int update(PayPassageAccount payPassageAccount);

    PayPassageAccount findById(Integer id);

    List<PayPassageAccount> select(int offset, int limit, PayPassageAccount payPassageAccount);

    Integer count(PayPassageAccount payPassageAccount);

    List<PayPassageAccount> selectAll(PayPassageAccount payPassageAccount);

    /**
     * 根据支付通道ID,查询所有支付通道账户列表
     * @param payPassageId
     * @return
     */
    List<PayPassageAccount> selectAllByPassageId(Integer payPassageId);

}
