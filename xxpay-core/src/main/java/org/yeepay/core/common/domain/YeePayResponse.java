package org.yeepay.core.common.domain;

import com.alibaba.fastjson.JSONObject;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.util.MyLog;
import org.yeepay.core.common.util.ObjectValidUtil;
import org.yeepay.core.entity.BaseModel;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/11/29
 * @description:
 */
public class YeePayResponse extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;
    private static final MyLog _log = MyLog.getLog(YeePayResponse.class);

    public int code;     // 返回码
    public String msg;     // 返回消息
    public Object data;    // 返回数据

    public YeePayResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        _log.info(this.toString());
    }

    public YeePayResponse(RetEnum retEnum, Object data) {
        this.code = retEnum.getCode();
        this.msg = retEnum.getMessage();
        this.data = data;
        _log.info(this.toString());
    }

    public YeePayResponse(RetEnum retEnum, Object data, Map<String, Object> ps) {
        this.code = retEnum.getCode();
        this.msg = retEnum.getMessage();
        this.data = data;
        this.setPs(ps);
        _log.info(this.toString());
    }

    public static YeePayResponse build(RetEnum retEnum) {
        YeePayResponse yeepayResponse = new YeePayResponse(retEnum.getCode(), retEnum.getMessage(), null);
        return yeepayResponse;
    }

    public static YeePayResponse build(RetEnum retEnum, Object data) {
        YeePayResponse yeepayResponse = new YeePayResponse(retEnum.getCode(), retEnum.getMessage(), data);
        return yeepayResponse;
    }

    public static YeePayResponse buildSuccess() {
        return buildSuccess(null);
    }

    public static YeePayResponse buildSuccess(Object data) {
        YeePayResponse yeepayResponse = new YeePayResponse(RetEnum.RET_COMM_SUCCESS, data);
        return yeepayResponse;
    }

    public static YeePayResponse buildSuccess(Object data, JSONObject param) {
        if(param != null && param.getBooleanValue("returnArray")) {
            List<Object> objectList = new LinkedList<Object>();
            objectList.add(data);
            return new YeePayResponse(RetEnum.RET_COMM_SUCCESS, objectList);
        }else {
            return new YeePayResponse(RetEnum.RET_COMM_SUCCESS, data);
        }
    }

    public String getMsg() {
        return msg;
    }

    public YeePayResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public YeePayResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "yeepayResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
