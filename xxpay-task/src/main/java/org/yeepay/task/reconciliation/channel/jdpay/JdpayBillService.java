package org.yeepay.task.reconciliation.channel.jdpay;

import org.springframework.stereotype.Service;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.task.reconciliation.channel.BaseBill;

/**
 * @author: yf
 * @date: 18/1/19
 * @description:
 */
@Service
public class JdpayBillService extends BaseBill {

    private static final MyLog _log = MyLog.getLog(JdpayBillService.class);

    @Override
    public String getChannelName() {
        return null;
    }
}
