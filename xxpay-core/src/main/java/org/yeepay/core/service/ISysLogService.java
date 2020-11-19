package org.yeepay.core.service;

import org.yeepay.core.entity.SysLog;

import java.util.List;

public interface ISysLogService {

    int add(SysLog record);

    List<SysLog> select(int offset, int limit, SysLog record);

    Integer count(SysLog record);

}
