package org.yeepay.core.service;

import org.yeepay.core.entity.EmpMch;

public interface IEmpMchService {
    EmpMch findByEmpId(Long empId);
}
