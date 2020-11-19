package org.yeepay.service.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yf
 * Date: 18/3/1 下午7:42
 * Description:
 */
public class DateUtil {

    /**
     * 获取当前年月日
     * @return
     */
    public static int getCurrentDay() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String todayStr = dateFormat.format(new Date());
        return Integer.parseInt(todayStr);
    }

}
