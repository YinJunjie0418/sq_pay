package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.service.IMchAccountHistoryService;
import org.yeepay.core.service.IMchAccountService;
import org.yeepay.core.service.IMchSettDailyCollectService;
import org.yeepay.service.dao.mapper.MchSettDailyCollectMapper;
import org.yeepay.core.entity.MchSettDailyCollect;

/**
 * @author: yf
 * @date: 17/12/4
 * @description: 商户结算汇总
 */
@Service(interfaceName = "org.yeepay.core.service.IMchSettDailyCollectService", version = "1.0.0", retries = -1)
public class MchSettDailyCollectServiceImpl implements IMchSettDailyCollectService {

    @Autowired
    private MchSettDailyCollectMapper mchSettDailyCollectMapper;

    @Autowired
    private IMchAccountHistoryService mchAccountHistoryService;

    @Autowired
    private IMchAccountService mchAccountService;

    @Override
    public int add(MchSettDailyCollect record) {
        return mchSettDailyCollectMapper.insertSelective(record);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void handleSettDailyCollect(String collDate, MchSettDailyCollect record) {

        // 增加结算汇总记录
        int count = add(record);
        if(count != 1) throw new ServiceException(RetEnum.RET_COMM_OPERATION_FAIL);

        // 更新结算状态
        mchAccountHistoryService.updateCompleteSett4Mch(record.getMchId(), collDate, record.getRiskDay());

        // 更新账户可结算金额
        mchAccountService.updateSettAmount(record.getMchId(), record.getTotalMchIncome());

    }
}
