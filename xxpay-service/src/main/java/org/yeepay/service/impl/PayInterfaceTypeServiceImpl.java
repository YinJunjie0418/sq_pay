package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.entity.PayInterfaceType;
import org.yeepay.core.entity.PayInterfaceTypeExample;
import org.yeepay.core.service.IPayInterfaceTypeService;
import org.yeepay.service.dao.mapper.PayInterfaceTypeMapper;

import java.util.List;

/**
 * @author: yf
 * @date: 2018/5/3
 * @description: 支付接口类型
 */
@Service(interfaceName = "org.yeepay.core.service.IPayInterfaceTypeService", version = "1.0.0", retries = -1)
public class PayInterfaceTypeServiceImpl implements IPayInterfaceTypeService {

    @Autowired
    private PayInterfaceTypeMapper payInterfaceTypeMapper;

    @Override
    public int add(PayInterfaceType payInterfaceType) {
        return payInterfaceTypeMapper.insertSelective(payInterfaceType);
    }

    @Override
    public int update(PayInterfaceType payInterfaceType) {
        return payInterfaceTypeMapper.updateByPrimaryKeySelective(payInterfaceType);
    }

    @Override
    public PayInterfaceType findByCode(String ifTypeCode) {
        return payInterfaceTypeMapper.selectByPrimaryKey(ifTypeCode);
    }

    @Override
    public List<PayInterfaceType> select(int offset, int limit, PayInterfaceType payInterfaceType) {
        PayInterfaceTypeExample example = new PayInterfaceTypeExample();
        example.setOrderByClause("createTime DESC");
        example.setOffset(offset);
        example.setLimit(limit);
        PayInterfaceTypeExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payInterfaceType);
        return payInterfaceTypeMapper.selectByExample(example);
    }

    @Override
    public Integer count(PayInterfaceType payInterfaceType) {
        PayInterfaceTypeExample example = new PayInterfaceTypeExample();
        PayInterfaceTypeExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payInterfaceType);
        return payInterfaceTypeMapper.countByExample(example);
    }

    @Override
    public List<PayInterfaceType> selectAll(PayInterfaceType payInterfaceType) {
        PayInterfaceTypeExample example = new PayInterfaceTypeExample();
        example.setOrderByClause("createTime DESC");
        PayInterfaceTypeExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, payInterfaceType);
        return payInterfaceTypeMapper.selectByExample(example);
    }

    void setCriteria(PayInterfaceTypeExample.Criteria criteria, PayInterfaceType obj) {
        if(obj != null) {
            if(obj.getIfTypeCode() != null) criteria.andIfTypeCodeEqualTo(obj.getIfTypeCode());
            if(obj.getStatus() != null && obj.getStatus().byteValue() != -99) criteria.andStatusEqualTo(obj.getStatus());
        }
    }
}
