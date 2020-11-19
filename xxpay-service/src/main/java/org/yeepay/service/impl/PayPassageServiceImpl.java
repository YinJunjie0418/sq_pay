package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yeepay.core.entity.PayPassage;
import org.yeepay.core.entity.PayPassageAccount;
import org.yeepay.core.entity.PayPassageAccountExample;
import org.yeepay.core.entity.PayPassageExample;
import org.yeepay.core.service.IPayPassageService;
import org.yeepay.service.dao.mapper.PayPassageAccountMapper;
import org.yeepay.service.dao.mapper.PayPassageMapper;

import java.util.List;

/**
 * @author: yf
 * @date: 2018/5/3
 * @description: 支付通道
 */
@Service(interfaceName = "org.yeepay.core.service.IPayPassageService", version = "1.0.0", retries = -1)
public class PayPassageServiceImpl implements IPayPassageService {

    @Autowired
    private PayPassageMapper payPassageMapper;

    @Autowired
    private PayPassageAccountMapper payPassageAccountMapper;

    @Override
    public int add(PayPassage payPassage) {
        return payPassageMapper.insertSelective(payPassage);
    }

    @Override
    public int update(PayPassage payPassage) {
        return payPassageMapper.updateByPrimaryKeySelective(payPassage);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public int updateRate(PayPassage payPassage) {
        // 修改通道费率
        int count = payPassageMapper.updateByPrimaryKeySelective(payPassage);
        // 修改该通道下所有子账户费率
        if(count == 1) {
            PayPassageAccount payPassageAccount = new PayPassageAccount();
            payPassageAccount.setPassageRate(payPassage.getPassageRate());
            PayPassageAccountExample example = new PayPassageAccountExample();
            PayPassageAccountExample.Criteria criteria = example.createCriteria();
            criteria.andPayPassageIdEqualTo(payPassage.getId());
            return payPassageAccountMapper.updateByExampleSelective(payPassageAccount, example);
        }
        return count;
    }

    @Override
    public PayPassage findById(Integer id) {
        return payPassageMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PayPassage> select(int offset, int limit, PayPassage payPassage) {
        PayPassageExample example = new PayPassageExample();
        example.setOrderByClause("createTime DESC");
        example.setOffset(offset);
        example.setLimit(limit);
        PayPassageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassage);
        return payPassageMapper.selectByExample(example);
    }

    @Override
    public Integer count(PayPassage payPassage) {
        PayPassageExample example = new PayPassageExample();
        PayPassageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassage);
        return payPassageMapper.countByExample(example);
    }

    @Override
    public List<PayPassage> selectAll(PayPassage payPassage) {
        PayPassageExample example = new PayPassageExample();
        example.setOrderByClause("createTime DESC");
        PayPassageExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payPassage);
        return payPassageMapper.selectByExample(example);
    }

    @Override
    public List<PayPassage> selectAllByPayType(String payType) {
        PayPassage payPassage = new PayPassage();
        payPassage.setPayType(payType);
        return selectAll(payPassage);
    }

    void setCriteria(PayPassageExample.Criteria criteria, PayPassage obj) {
        if(obj != null) {
            if(obj.getPayType() != null) criteria.andPayTypeEqualTo(obj.getPayType());
            if(obj.getRiskStatus() != null && obj.getRiskStatus().byteValue() != -99) criteria.andRiskStatusEqualTo(obj.getRiskStatus());
            if(obj.getStatus() != null && obj.getStatus().byteValue() != -99) criteria.andStatusEqualTo(obj.getStatus());
        }
    }
}
