package com.cc.musiclist.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

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
        File directory = null;
        if ( Environment.isExternalStorageEmulated() && Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED)
            directory = Environment.getExternalStorageDirectory();
        else
            directory = Environment.getRootDirectory();

        Log.d("FileDirectoryUtil","RootDirectory:"+directory.getAbsolutePath());
        return directory;
    }


}
