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

    private static void queryDirectory(File directory, String[] fileTypes) {

        File[] files = directory.listFiles();
        if (files == null)
            return;
        else {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                LogUtil.i(TAG, "file：" + file.getAbsolutePath());
                if (file.isDirectory()) {
                    queryDirectory(file, fileTypes);
                } else {
                    for (int m = 0; m < fileTypes.length; m++)
                        if (FileTypeUtil.getFileTypeByPostfix(file).equals(fileTypes[m])) {
                            fileList.add(file);
                            LogUtil.d(TAG, "-->add:" + file.getAbsolutePath());
                        }

                }
            }
        }

    }
}
