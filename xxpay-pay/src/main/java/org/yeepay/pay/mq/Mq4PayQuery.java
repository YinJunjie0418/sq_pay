package org.yeepay.pay.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.yeepay.core.common.constant.PayConstant;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.common.util.YeePayUtil;
import org.yeepay.core.entity.PayOrder;
import org.yeepay.core.entity.TransOrder;
import org.yeepay.pay.channel.PaymentInterface;
import org.yeepay.pay.channel.TransInterface;
import org.yeepay.pay.service.RpcCommonService;
import org.yeepay.pay.util.SpringUtil;
import org.yeepay.pay.util.Util;
import org.yeepay.pay.channel.PaymentInterface;
import org.yeepay.pay.service.RpcCommonService;
import org.yeepay.pay.util.SpringUtil;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 支付渠道订单查询MQ
 * @author yf
 * @date 2018-3-6
 * @version V1.0
 * @Copyright:
 */
@Component
public class Mq4PayQuery {

    @Autowired
    private Queue payQueryQueue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    public BaseNotify4MchPay baseNotify4MchPay;

    @Autowired
    private RpcCommonService rpcCommonService;

    private static final MyLog _log = MyLog.getLog(Mq4PayQuery.class);

    public void send(String msg) {
        _log.info("发送MQ消息:msg={}", msg);
        this.jmsTemplate.convertAndSend(this.payQueryQueue, msg);
    }

    /**
     * 发送延迟消息
     * @param msg
     * @param delay
     */
    public void send(String msg, long delay) {
        _log.info("发送MQ延时消息:msg={},delay={}", msg, delay);
        jmsTemplate.send(this.payQueryQueue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                TextMessage tm = session.createTextMessage(msg);
                tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
                tm.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, 1);
                return tm;
            }
        });
    }

    @JmsListener(destination = MqConfig.PAY_QUERY_QUEUE_NAME)
    public void receive(String msg) {
        _log.info("处理支付订单查询任务.msg={}", msg);
        JSONObject msgObj = JSON.parseObject(msg);
        int count = msgObj.getIntValue("count");
        String payOrderId = msgObj.getString("payOrderId");
        String channelName = msgObj.getString("channelName");
        PayOrder payOrder = rpcCommonService.rpcPayOrderService.findByPayOrderId(payOrderId);
        if(payOrder == null) {
            _log.warn("查询支付订单为空,payOrderId={}", payOrderId);
            return;
        }
        if(payOrder.getStatus() != PayConstant.PAY_STATUS_PAYING) {
            _log.warn("订单状态不是支付中({}),不需查询渠道.payOrderId={}", PayConstant.PAY_STATUS_PAYING, payOrderId);
            return;
        }

        PaymentInterface paymentInterface = (PaymentInterface) SpringUtil.getBean(channelName.toLowerCase() +  "PaymentService");

        JSONObject retObj = paymentInterface.query(payOrder);

        // 订单为成功
        if(YeePayUtil.isSuccess(retObj) && "0".equals(retObj.get("status"))) {
            String transaction_id = retObj.getString("transaction_id");
            int updatePayOrderRows = rpcCommonService.rpcPayOrderService.updateStatus4Success(payOrder.getPayOrderId(), transaction_id, retObj.getString("channelAttach"));
            _log.error("将payOrderId={}订单状态更新为支付成功,result={}", payOrderId, updatePayOrderRows);
            if (updatePayOrderRows == 1) {
                // 通知业务系统
                baseNotify4MchPay.doNotify(payOrder, true);
                return;
            }
        }

        // 发送延迟消息,继续查询
        if(count++ < 30) {
            msgObj.put("count", count);
            send(msgObj.toJSONString(), 5 * 1000);   // 延迟5秒查询
        }

    }
}
