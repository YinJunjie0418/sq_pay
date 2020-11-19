package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.entity.*;
import org.yeepay.core.service.IPayPassageAccountService;
import org.yeepay.service.dao.mapper.PayPassageAccountMapper;

import java.util.List;

/**
 * @author: yf
 * @date: 2018/5/3
 * @description: 支付通道账户
 */
@Service(interfaceName = "org.yeepay.core.service.IPayPassageAccountService", version = "1.0.0", retries = -1)
public class PayPassageAccountServiceImpl implements IPayPassageAccountService {

    @Autowired
    private PayPassageAccountMapper payPassageAccountMapper;

    @Override
    public int add(PayPassageAccount payPassageAccount) {
        return payPassageAccountMapper.insertSelective(payPassageAccount);
    }

    @Override
    public int update(PayPassageAccount payPassageAccount) {
        return payPassageAccountMapper.updateByPrimaryKeySelective(payPassageAccount);
    }

    @Override
    public PayPassageAccount findById(Integer id) {
        return payPassageAccountMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PayPassageAccount> select(int offset, int limit, PayPassageAccount payPassageAccount) {
        PayPassageAccountExample example = new PayPassageAccountExample();
        example.setOrderByClause("createTime DESC");
        example.setOffset(offset);
        example.setLimit(limit);
        PayPassageAccountExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassageAccount);
        return payPassageAccountMapper.selectByExample(example);
    }

    @Override
    public Integer count(PayPassageAccount payPassageAccount) {
        PayPassageAccountExample example = new PayPassageAccountExample();
        PayPassageAccountExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassageAccount);
        return payPassageAccountMapper.countByExample(example);
    }

    @Override
    public List<PayPassageAccount> selectAll(PayPassageAccount payPassageAccount) {
        PayPassageAccountExample example = new PayPassageAccountExample();
        example.setOrderByClause("createTime DESC");
        PayPassageAccountExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassageAccount);
        return payPassageAccountMapper.selectByExample(example);
    }

    @Override
    public List<PayPassageAccount> selectAllByPassageId(Integer payPassageId) {
        PayPassageAccount payPassageAccount = new PayPassageAccount();
        payPassageAccount.setPayPassageId(payPassageId);
        payPassageAccount.setStatus(MchConstant.PUB_YES);
        return selectAll(payPassageAccount);
    }

    void setCriteria(PayPassageAccountExample.Criteria criteria, PayPassageAccount obj) {
        if(obj != null) {
            if(obj.getPayPassageId() != null) criteria.andPayPassageIdEqualTo(obj.getPayPassageId());
            if(obj.getPassageMchId() != null) criteria.andPassageMchIdEqualTo(obj.getPassageMchId());
            if(obj.getRiskStatus() != null && obj.getRiskStatus().byteValue() != -99) criteria.andRiskStatusEqualTo(obj.getRiskStatus());
            if(obj.getStatus() != null && obj.getStatus().byteValue() != -99) criteria.andStatusEqualTo(obj.getStatus());
        }
    }

}
