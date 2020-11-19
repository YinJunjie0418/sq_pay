package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.entity.PayOrderCashCollRecord;
import org.yeepay.core.entity.PayOrderCashCollRecordExample;
import org.yeepay.core.service.IPayOrderCashCollRecordService;
import org.yeepay.service.dao.mapper.PayOrderCashCollRecordMapper;

import java.util.List;

@Service(interfaceName = "org.yeepay.core.service.IPayOrderCashCollRecordService", version = "1.0.0", retries = -1)
public class PayOrderCashCollRecordServiceImpl implements IPayOrderCashCollRecordService {

    @Autowired
    private PayOrderCashCollRecordMapper recordMapper;

    @Override
    public int add(PayOrderCashCollRecord record) {
        return recordMapper.insertSelective(record);
    }

    @Override
    public List<PayOrderCashCollRecord> select(int offset, int limit, PayOrderCashCollRecord record) {
        PayOrderCashCollRecordExample example = new PayOrderCashCollRecordExample();
        example.setOrderByClause("CreateTime desc");
        example.setOffset(offset);
        example.setLimit(limit);
        PayOrderCashCollRecordExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, record);
        return recordMapper.selectByExample(example);
    }

    @Override
    public Integer count(PayOrderCashCollRecord record) {
        PayOrderCashCollRecordExample example = new PayOrderCashCollRecordExample();
        PayOrderCashCollRecordExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, record);
        return recordMapper.countByExample(example);
    }

    void setCriteria(PayOrderCashCollRecordExample.Criteria criteria, PayOrderCashCollRecord record) {
        if(record != null) {
            if(record.getStatus() != null) criteria.andStatusEqualTo(record.getStatus());
            if(StringUtils.isNotEmpty(record.getPayOrderId()) ) criteria.andPayOrderIdEqualTo(record.getPayOrderId());
            if(StringUtils.isNotEmpty(record.getChannelOrderNo()) ) criteria.andChannelOrderNoEqualTo(record.getChannelOrderNo());
            if(StringUtils.isNotEmpty(record.getTransInUserId()) ) criteria.andTransInUserIdEqualTo(record.getTransInUserId());
        }
    }
}
