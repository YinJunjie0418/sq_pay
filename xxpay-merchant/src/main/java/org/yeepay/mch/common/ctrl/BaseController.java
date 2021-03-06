package org.yeepay.mch.common.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.yeepay.core.common.constant.RetEnum;
import org.yeepay.core.common.domain.BizResponse;
import org.yeepay.core.common.util.DateUtil;
import org.yeepay.core.common.util.GoogleAuthenticator;
import org.yeepay.core.common.util.MD5Util;
import org.yeepay.core.entity.MchInfo;
import org.yeepay.mch.common.config.MainConfig;
import org.yeepay.mch.secruity.JwtUser;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: yf
 * @date: 17/12/6
 * @description:
 */
@Controller
public class BaseController {

    @Autowired
    protected MainConfig mainConfig;

    private static final int DEFAULT_PAGE_INDEX = 1;
    private static final int DEFAULT_PAGE_SIZE = 20;
    protected static final String PAGE_COMMON_ERROR = "common/error";
    protected static final String PAGE_COMMON_PC_ERROR = "common/pc_error";

    protected JwtUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        return jwtUser;
    }

    protected int getPageIndex(JSONObject object) {
        if(object == null) return DEFAULT_PAGE_INDEX;
        Integer pageIndex = object.getInteger("page");
        if(pageIndex == null) return DEFAULT_PAGE_INDEX;
        return pageIndex;
    }

    protected int getPageSize(JSONObject object) {
        if(object == null) return DEFAULT_PAGE_SIZE;
        Integer pageSize = object.getInteger("limit");
        if(pageSize == null) return DEFAULT_PAGE_SIZE;
        return pageSize;
    }

    protected int getPageIndex(Integer page) {
        if(page == null) return DEFAULT_PAGE_INDEX;
        return page;
    }

    protected int getPageSize(Integer limit) {
        if(limit == null) return DEFAULT_PAGE_SIZE;
        return limit;
    }

    protected JSONObject getJsonParam(HttpServletRequest request) {
        String params = request.getParameter("params");
        if(StringUtils.isNotBlank(params)) {
            return JSON.parseObject(params);
        }
        // ??????Map
        Map properties = request.getParameterMap();
        // ?????????Map
        JSONObject returnObject = new JSONObject();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnObject.put(name, value);
        }
        return returnObject;
    }

    protected Boolean getBoolean(JSONObject param, String key) {
        if(param == null) return null;
        return param.getBooleanValue(key);
    }

    protected String getString(JSONObject param, String key) {
        if(param == null) return null;
        return param.getString(key);
    }

    protected String getStringRequired(JSONObject param, String key) {
        if(param == null ) {
            throw new RuntimeException(getErrMsg(key));
        }
        String value = param.getString(key);
        if(StringUtils.isBlank(value)) {
            throw new RuntimeException(getErrMsg(key));
        }
        return value;
    }

    protected String getStringDefault(JSONObject param, String key, String defaultValue) {
        String value = getString(param, key);
        if(value == null) return defaultValue;
        return value;
    }

    protected Byte getByte(JSONObject param, String key) {
        if(param == null) return null;
        return param.getByte(key);
    }

    protected Byte getByteRequired(JSONObject param, String key) {
        if(param == null ) throw new RuntimeException(getErrMsg(key));
        Byte value = param.getByte(key);
        if(value == null) throw new RuntimeException(getErrMsg(key));
        return value;
    }

    protected int getByteDefault(JSONObject param, String key, byte defaultValue) {
        Byte value = getByte(param, key);
        if(value == null) return defaultValue;
        return value.byteValue();
    }

    protected Integer getInteger(JSONObject param, String key) {
        if(param == null) return null;
        return param.getInteger(key);
    }

    protected Integer getIntegerRequired(JSONObject param, String key) {
        if(param == null ) throw new RuntimeException(getErrMsg(key));
        Integer value = param.getInteger(key);
        if(value == null) throw new RuntimeException(getErrMsg(key));
        return value;
    }

    protected int getIntegerDefault(JSONObject param, String key, int defaultValue) {
        Integer value = getInteger(param, key);
        if(value == null) return defaultValue;
        return value.intValue();
    }

    protected Long getLong(JSONObject param, String key) {
        if(param == null) return null;
        return param.getLong(key);
    }

    protected Long getLongRequired(JSONObject param, String key) {
        if(param == null ) throw new RuntimeException(getErrMsg(key));
        Long value = param.getLong(key);
        if(value == null) throw new RuntimeException(getErrMsg(key));
        return value;
    }

    protected long getLongDefault(JSONObject param, String key, long defaultValue) {
        Long value = getLong(param, key);
        if(value == null) return defaultValue;
        return value.longValue();
    }

    protected JSONObject getJSONObject(JSONObject param, String key) {
        if(param == null) return null;
        return param.getJSONObject(key);
    }

    protected <T> T getObject(JSONObject param, String key, Class<T> clazz) {
        JSONObject object = getJSONObject(param, key);
        if(object == null) return null;
        return JSON.toJavaObject(object, clazz);
    }

    protected <T> T getObject(JSONObject param, Class<T> clazz) {
        if(param == null) return null;
        return JSON.toJavaObject(param, clazz);
    }

    private String getErrMsg(String key) {
        return "??????" + key + "??????";
    }

    /**
     * ?????????????????????
     * @param googleAuthSecretKey
     * @param code
     * @return
     */
    protected boolean checkGoogleCode(String googleAuthSecretKey, Long code) {
        if(StringUtils.isBlank(googleAuthSecretKey)) {
            return false;
        }
        // ????????????
        long t = System.currentTimeMillis();
        GoogleAuthenticator ga = new GoogleAuthenticator();
        ga.setWindowSize(5);    // ????????????????????????
        return ga.check_code(googleAuthSecretKey, code, t);
    }

    /**
     * ????????????(???????????????????????????BizResponse??????,????????????null)
     * @param mchInfo
     * @param param
     * @return
     */
    protected BizResponse verifyPay(MchInfo mchInfo, JSONObject param) {
        // ????????????????????????
        Byte paySecurityType = mchInfo.getPaySecurityType();
        switch (paySecurityType) {
            case 0: // ????????????
                break;
            case 1: // ?????????????????????
                String payPassword = getStringRequired(param, "payPassword");   // ????????????
                String passwordMd5 = MD5Util.string2MD5(payPassword);
                // ????????????????????????????????????
                if(!mchInfo.getPayPassword().equals(passwordMd5)) {
                    return BizResponse.build(RetEnum.RET_MCH_PAP_PASSWORD_NOT_MATCH);
                }
                break;
            case 2: // ?????????????????????
                Long googleCode = getLongRequired(param, "googleCode");
                if(!checkGoogleCode(mchInfo.getGoogleAuthSecretKey(), googleCode)) {
                    return BizResponse.build(RetEnum.RET_MCH_GOOGLECODE_NOT_MATCH);
                }
                break;
            case 3: // ????????????????????????????????????
                payPassword = getStringRequired(param, "payPassword");   // ????????????
                passwordMd5 = MD5Util.string2MD5(payPassword);
                // ????????????????????????????????????
                if(!mchInfo.getPayPassword().equals(passwordMd5)) {
                    return BizResponse.build(RetEnum.RET_MCH_PAP_PASSWORD_NOT_MATCH);
                }
                googleCode = getLongRequired(param, "googleCode");
                if(!checkGoogleCode(mchInfo.getGoogleAuthSecretKey(), googleCode)) {
                    return BizResponse.build(RetEnum.RET_MCH_GOOGLECODE_NOT_MATCH);
                }
                break;
            default:
                return null;
        }
        return null;
    }

    /**
     * ?????????????????????
     * @param param
     * @param keys
     */
    protected void checkRequired(JSONObject param, String... keys) {
        if(param == null ) throw new RuntimeException(getErrMsg(keys[0]));
        for(String key : keys) {
            String value = param.getString(key);
            if(value == null) throw new RuntimeException(getErrMsg(key));
        }
    }

    /**
     * ??????????????????????????????,?????????????????????
     * @param param
     * @param name
     * @return
     */
    public Long getRequiredAmountL(JSONObject param, String name) {
        String amountStr = getStringRequired(param, name);  // ?????????????????????,??????????????????2???
        Long amountL = new BigDecimal(amountStr.trim()).multiply(new BigDecimal(100)).longValue(); // // ?????????
        return amountL;
    }

    /**
     * ????????????????????????(?????????????????????????????????)
     * @param param
     * @param names
     */
    public void handleParamAmount(JSONObject param, String... names) {
        for(String name : names) {
            String amountStr = getString(param, name);  // ?????????????????????,??????????????????2???
            if(StringUtils.isNotBlank(amountStr)) {
                Long amountL = new BigDecimal(amountStr.trim()).multiply(new BigDecimal(100)).longValue(); // // ?????????
                param.put(name, amountL);
            }
        }
    }

    public Map<String, Object> request2payResponseMap(HttpServletRequest request, String[] paramArray) {
        Map<String, Object> responseMap = new HashMap<>();
        for (int i = 0;i < paramArray.length; i++) {
            String key = paramArray[i];
            String v = request.getParameter(key);
            if (v != null) {
                responseMap.put(key, v);
            }
        }
        return responseMap;
    }

    /**
     * ????????????????????????
     * @param param
     * @return
     */
    public JSONObject getQueryObj(JSONObject param) {
        // ??????????????????
        Date createTimeStart = null;
        Date createTimeEnd = null;
        String createTimeStartStr = getString(param, "createTimeStart");
        if(StringUtils.isNotBlank(createTimeStartStr)) createTimeStart = DateUtil.str2date(createTimeStartStr);
        String createTimeEndStr = getString(param, "createTimeEnd");
        if(StringUtils.isNotBlank(createTimeEndStr)) createTimeEnd = DateUtil.str2date(createTimeEndStr);
        JSONObject queryObj = new JSONObject();
        queryObj.put("createTimeStart", createTimeStart);
        queryObj.put("createTimeEnd", createTimeEnd);
        return queryObj;
    }

}
