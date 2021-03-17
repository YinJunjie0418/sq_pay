package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.yeepay.core.entity.EmpMch;
import org.yeepay.core.service.IEmpMchService;
import org.yeepay.service.dao.mapper.EmpMchMapper;
import org.yeepay.service.dao.mapper.MchInfoMapper;

@Service(interfaceName = "org.yeepay.core.service.IEmpMchService", version = "1.0.0", retries = -1)
public class EmpMchServiceImpl implements IEmpMchService {
    @Autowired
    private EmpMchMapper empMchMapper;

    @Override
    public EmpMch findByEmpId(Long empId) {
        return empMchMapper.selectByPrimaryKey(empId);
    }
}
