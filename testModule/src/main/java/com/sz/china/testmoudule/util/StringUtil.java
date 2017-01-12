package com.sz.china.testmoudule.util;

import android.text.TextUtils;

/**
 * Created by zhangyu on 2016-06-21 10:53.
 */
public class StringUtil {
    /**
     * 秒值转化为00:00格式字符串
     *
     * @param second
     * @return
     */
    public static String msToTime(long second) {
        StringBuffer sb = new StringBuffer();
        long min = second / 60;
        long s = second % 60;

        if (min < 10)
            sb.append("0").append(min);
        else
            sb.append(min);

        sb.append(":");

        if (s < 10)
            sb.append("0").append(s);
        else
            sb.append(s);
        return sb.toString();
    }

    public static boolean isMobileNO(String mobiles) {
        if (mobiles == null) {
            return false;
        }
        return mobiles.matches("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
    }

    public static boolean isEmil(String str) {
        if (str == null) {
            return false;
        }
        return str.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
    }

    public static String testDataBinding(String name) {
        return "姓名：" + name;
    }
}
