package org.yeepay.core.service;

import org.yeepay.core.entity.MchBankAccount;

import java.util.List;

/**
 * @author: yf
 * @date: 17/12/7
 * @description:
 */
public interface IMchBankAccountService {

    List<MchBankAccount> select(int offset, int limit, MchBankAccount mchBankAccount);

    int count(MchBankAccount mchBankAccount);

    MchBankAccount find(MchBankAccount mchBankAccount);

    MchBankAccount findById(Long id);

    MchBankAccount findByAccountNo(String accountNo);

    int add(MchBankAccount mchBankAccount);

    int update(MchBankAccount mchBankAccount);

    int updateByMchId(MchBankAccount mchBankAccount, Long mchId);

    int delete(Long id);

}
