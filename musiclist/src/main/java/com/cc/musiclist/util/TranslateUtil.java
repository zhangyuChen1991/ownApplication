package com.cc.musiclist.util;

/**
 * Created by zhangyu on 2016-06-20 11:50.
 */
public class TranslateUtil {

    /**
     * 字符串数组元素以都好分隔，拼成字符串 (一般与String[] stringToStringArray(String str)方法搭配使用，相互转换)
     * @param strArry
     * @return
     */
    public String stringArrayToString(String[] strArry){
        StringBuilder ret = new StringBuilder();
        for(int i = 0;i < strArry.length;i++){
            ret.append(strArry[i]);
            if(i != strArry.length - 1) {
                ret.append(",");
            }
        }
        return ret.toString();
    }

    /**
     * 字符串以都好分割，转换为字符串数组 (一般与stringArrayToString(String[] strArry)方法搭配使用，相互转换)
     * @param str
     * @return
     */
    public String[] stringToStringArray(String str){
        String[] ret = str.split(",");
        return ret;
    }


}
