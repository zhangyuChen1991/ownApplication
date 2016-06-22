package com.cc.musiclist.util;

import java.io.File;

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
}
