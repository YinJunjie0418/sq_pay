package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.entity.SysLog;
import org.yeepay.core.entity.SysLogExample;
import org.yeepay.core.service.ISysLogService;
import org.yeepay.service.dao.mapper.SysLogMapper;

import java.util.List;

@Service(interfaceName = "org.yeepay.core.service.ISysLogService", version = "1.0.0", retries = -1)
public class SysLogServiceImpl implements ISysLogService {
    
    @Autowired
    private SysLogMapper recordMapper;

    void setCriteria(SysLogExample.Criteria criteria, SysLog obj) {
        if(obj != null) {
            if(obj.getUserId() != null) criteria.andUserIdEqualTo(obj.getUserId());
            if(obj.getSystem() != null) criteria.andSystemEqualTo(obj.getSystem());
        }
    }

    @Override
    public int add(SysLog record) {
        return recordMapper.insertSelective(record);
    }

    @Override
    public List<SysLog> select(int offset, int limit, SysLog record) {
        SysLogExample example = new SysLogExample();
        example.setOrderByClause("CreateTime desc");
        example.setOffset(offset);
        example.setLimit(limit);
        SysLogExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, record);
        return recordMapper.selectByExample(example);
    }

    @Override
    public Integer count(SysLog record) {
        SysLogExample example = new SysLogExample();
        SysLogExample.Criteria criteria = example.createCriteria();
        setCriteria(criteria, record);
        return recordMapper.countByExample(example);
    }
}
