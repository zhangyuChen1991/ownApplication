package com.cc.musiclist.util;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhangyu on 2016-06-17 17:22.
 */
public class FileUtil {
    private static final String TAG = "FileUtil";
    private static ArrayList<File> fileList = new ArrayList<>();

    public static ArrayList<File> getFileList(File directory, String fileType) {
        queryDirectory(directory, fileType);
        return fileList;
    }

    private static void queryDirectory(File directory, String fileType) {

        File[] files = directory.listFiles();
        if (files == null)
            return;
        else {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                Log.i(TAG,"file："+file.getAbsolutePath());
                if (file.isDirectory()) {
                    getFileList(file, fileType);
                } else {
                    if (FileTypeUtil.getFileTypeByPostfix(file).equals(fileType)) {
                        fileList.add(file);
                        Log.d(TAG,"-->add:"+file.getAbsolutePath());
                    }

                }
            }
        }

    }
}
