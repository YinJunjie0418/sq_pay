package org.yeepay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.yeepay.core.common.Exception.ServiceException;
import org.yeepay.core.common.constant.MchConstant;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.entity.AgentSettDailyCollect;
import org.yeepay.core.service.IAgentAccountService;
import org.yeepay.core.service.IAgentSettDailyCollectService;
import org.yeepay.core.service.IMchAccountHistoryService;
import org.yeepay.service.dao.mapper.AgentSettDailyCollectMapper;

/**
 * @author: yf
 * @date: 18/05/08
 * @description: 代理商结算汇总
 */
@Service(interfaceName = "org.yeepay.core.service.IAgentSettDailyCollectService", version = "1.0.0", retries = -1)
public class AgentSettDailyCollectServiceImpl implements IAgentSettDailyCollectService {

    @Autowired
    private AgentSettDailyCollectMapper agentSettDailyCollectMapper;

    @Autowired
    private IMchAccountHistoryService mchAccountHistoryService;

    @Autowired
    private IAgentAccountService agentAccountService;

    @Override
    public int add(AgentSettDailyCollect record) {
        return agentSettDailyCollectMapper.insertSelective(record);
    }

    @Override
    @Transactional(transactionManager = "transactionManager", rollbackFor = Exception.class)
    public void handleSettDailyCollect(String collDate, AgentSettDailyCollect record, Byte bizType, String bizItem) {
        // 增加账户余额及资金账户流水记录
        agentAccountService.creditToAccount(record.getAgentId(), record.getTotalAgentProfit(), MchConstant.AGENT_BIZ_TYPE_PROFIT, bizItem, null);
        // 增加结算汇总记录
        int count = add(record);
        if(count != 1) throw new ServiceException(RetEnum.RET_COMM_OPERATION_FAIL);
        // 更新结算状态
        mchAccountHistoryService.updateCompleteSett4Agent(record.getAgentId(), collDate, record.getRiskDay(), bizType, bizItem);
        // 更新账户可结算金额
        // agentAccountService.updateSettAmount(record.getAgentId(), record.getTotalAgentProfit());
    }

}
