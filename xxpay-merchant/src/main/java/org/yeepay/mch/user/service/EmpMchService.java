package org.yeepay.mch.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeepay.core.entity.EmpMch;
import org.yeepay.mch.common.service.RpcCommonService;

@Component
public class EmpMchService {
    @Autowired
    private RpcCommonService rpcCommonService;
    public EmpMch findByEmpId(Long empId) {
        return rpcCommonService.rpcEmpMchService.findByEmpId(empId);
    }
}
