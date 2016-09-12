package com.cc.musiclist.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyu on 2016-06-20 11:50.
 */
public class TranslateUtil {

    /**
     * 字符串数组元素以逗号分隔，拼成字符串 (一般与String[] stringToStringArray(String str)方法搭配使用，相互转换)
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

    /**
     * 文件集合转换为其路径组成的字符数组（StringsToFileList(String[] strs)配合使用）
     * @param files
     * @return
     */
    public String[] fileListToStrings(List<File> files){
        String[] strs = new String[files.size()];
        for(int i = 0;i < files.size();i++){
            strs[i] = files.get(i).getAbsolutePath();
        }

        return strs;
    }

    /**
     * 字符数组转换为文件集合
     * @param strs（fileListToStrings(String[] strs)配合使用）
     * @return
     */
    public List<File> StringsToFileList(String[] strs){
        List<File> files = new ArrayList<File>();
        try {
            for(int i = 0;i < strs.length;i++){
                File file = new File(strs[i]);
                files.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return files;
    }

}
