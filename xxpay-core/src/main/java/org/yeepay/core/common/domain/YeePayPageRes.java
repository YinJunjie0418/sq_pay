package org.yeepay.core.common.domain;

import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.util.MyLog;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/11/29
 * @description:
 */
public class YeePayPageRes extends YeePayResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private static final MyLog _log = MyLog.getLog(YeePayPageRes.class);

    public int count;     // 记录总数

    public YeePayPageRes(RetEnum retEnum, Object data, int count) {
        super(retEnum, data);
        this.count = count;
        _log.info(this.toString());
    }

    /**
     * 实例化函数 方法重载 添加ps参数
     * @param retEnum
     * @param data
     * @param ps
     * @param count
     */
    public YeePayPageRes(RetEnum retEnum, Object data,Map<String, Object> ps, int count) {
        super(retEnum, data, ps);
        this.count = count;
        _log.info(this.toString());
    }

    public static YeePayPageRes buildSuccess(Object data, int count) {
        YeePayPageRes yeepayResponse = new YeePayPageRes(RetEnum.RET_COMM_SUCCESS, data, count);
        return yeepayResponse;
    }

    /**
     * <p><b>Description: </b>buildSuccess方法重载   新增ps 扩展参数
     * <p>2018年9月17日 上午10:13:01
     * @author matf
     * @param data
     * @param ps
     * @param count
     * @return
     */
    public static YeePayPageRes buildSuccess(Object data, Map ps, int count) {
        YeePayPageRes yeepayResponse = new YeePayPageRes(RetEnum.RET_COMM_SUCCESS, data, ps, count);
        return yeepayResponse;
    }

    public static YeePayPageRes buildSuccess() {
        YeePayPageRes yeepayResponse = new YeePayPageRes(RetEnum.RET_COMM_SUCCESS, null, 0);
        return yeepayResponse;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "yeepayPageRes{" +
                "count=" + count +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
