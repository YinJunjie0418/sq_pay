package org.yeepay.pay.ctrl.payment;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yeepay.core.common.constant.PayConstant;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.common.util.YeePayUtil;
import org.yeepay.pay.ctrl.common.BaseController;
import org.yeepay.pay.service.TransOrderService;
import org.yeepay.pay.service.TransOrderService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 转账
 * @author yf
 * @date 2017-10-30
 * @version V1.0
 * @Copyright:
 */
@RestController
public class TransOrderController extends BaseController {

    private final MyLog _log = MyLog.getLog(TransOrderController.class);

    @Autowired
    private TransOrderService transOrderService;

    /**
     * 统一转账接口:
     * 1)先验证接口参数以及签名信息
     * 2)验证通过创建支付订单
     * 3)根据商户选择渠道,调用支付服务进行下单
     * 4)返回下单数据
     * @param request
     * @return
     */
    @RequestMapping(value = "/api/trans/create_order")
    public String transOrder(HttpServletRequest request) {
        _log.info("###### 开始接收商户统一转账请求 ######");
        try {
            JSONObject po = getJsonParam(request);
            return transOrderService.createTransOrder(po);
        }catch (Exception e) {
            _log.error(e, "");
            return YeePayUtil.makeRetFail(YeePayUtil.makeRetMap(PayConstant.RETURN_VALUE_FAIL, "支付中心系统异常", null, null));
        }
    }



}
