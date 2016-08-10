package com.cc.musiclist.util;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhangyu on 2016-06-17 17:22.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    private static ArrayList<File> fileList = new ArrayList<>();

    public static ArrayList<File> getFileList(File directory, String[] fileTypes) {
        if (!directory.exists() || !directory.canRead())
            return fileList;

        queryDirectory(directory, fileTypes);
        return fileList;
    }

    /**
     * 扫描指定文件目录 添加指定文件类型并返回列表
     * @param directory 扫描的文件目录
     * @param fileTypes 指定得文件类型
     */
    private static void queryDirectory(File directory, String[] fileTypes) {

        File[] files = directory.listFiles();
        if (files == null)
            return;
        else {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                MLog.i(TAG, "file：" + file.getAbsolutePath());
                if (file.isDirectory()) {
                    queryDirectory(file, fileTypes);
                } else {
                    for (int m = 0; m < fileTypes.length; m++)
                        if (FileTypeUtil.getFileTypeByPostfix(file).equals(fileTypes[m])) {
                            fileList.add(file);
                            MLog.d(TAG, "-->add:" + file.getAbsolutePath());
                        }

                }
            }
        }

    }
}
