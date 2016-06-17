package com.cc.musiclist.util;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by zhangyu on 2016-06-17 17:22.
 */
public class FileUtil {
    private static ArrayList<File> fileList = new ArrayList<>();

    public static ArrayList<File> getFileList(File directory, String fileType) {

        File[] files = directory.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                getFileList(file, fileType);
            } else {
                if (FileTypeUtil.getFileType(file).equals(fileType))
                    fileList.add(files[i]);
            }
        }

        return fileList;
    }
}
