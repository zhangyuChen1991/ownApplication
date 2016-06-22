package com.cc.musiclist.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.cc.musiclist.application.BaseApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhangyu on 2016-06-17 17:15.
 */
public class FileDirectoryUtil {

    private Context context = BaseApplication.context;
    private static final String TAG = "FileDirectoryUtil";

    /**
     * 获取设备存储的根目录
     *
     * @return
     */
    public static File getSdCardDirectory() {
        File directory = Environment.getExternalStorageDirectory();
        Log.d(TAG, "RootDirectory:" + directory.getAbsolutePath());
        return directory;
    }

    /**
     * 获取外部sd卡目录，这个方法没试过。。。
     * @return
     */
    public static String getPath2() {
        String sdcard_path = null;
        String sd_default = Environment.getExternalStorageDirectory()
                .getAbsolutePath();
        Log.d(TAG, "sd_default = " + sd_default);
        if (sd_default.endsWith("/")) {
            sd_default = sd_default.substring(0, sd_default.length() - 1);
        }
        // 得到路径
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            String line;
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if (line.contains("secure"))
                    continue;
                if (line.contains("asec"))
                    continue;
                if (line.contains("fat") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                } else if (line.contains("fuse") && line.contains("/mnt/")) {
                    String columns[] = line.split(" ");
                    if (columns != null && columns.length > 1) {
                        if (sd_default.trim().equals(columns[1].trim())) {
                            continue;
                        }
                        sdcard_path = columns[1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "sdcard_path = " + (sdcard_path == null ? "null" : sdcard_path));
        return sdcard_path;
    }

}
