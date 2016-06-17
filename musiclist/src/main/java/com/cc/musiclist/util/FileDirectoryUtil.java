package com.cc.musiclist.util;

import android.content.Context;
import android.os.Environment;

import com.cc.musiclist.application.BaseApplication;

import java.io.File;

/**
 * Created by zhangyu on 2016-06-17 17:15.
 */
public class FileDirectoryUtil {

    private Context context = BaseApplication.context;

    /**
     * 获取设备存储的根目录
     * @return
     */
    public static File getRootDirectory() {
        if (Environment.isExternalStorageEmulated())
            return Environment.getExternalStorageDirectory();
        else
            return Environment.getRootDirectory();
    }


}
