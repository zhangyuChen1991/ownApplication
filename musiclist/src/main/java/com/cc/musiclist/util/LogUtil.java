package com.cc.musiclist.util;

import android.util.Log;

/**
 * Created by zhangyu on 2016-07-01 19:14.
 */
public class LogUtil {
    public static boolean openLog = true;

    public static void v(String TAG, String message) {
        if (openLog)
            Log.v(TAG, message);
    }

    public static void d(String TAG, String message) {
        if (openLog)
            Log.d(TAG, message);
    }

    public static void i(String TAG, String message) {
        if (openLog)
            Log.i(TAG, message);
    }

    public static void w(String TAG, String message) {
        if (openLog)
            Log.w(TAG, message);
    }

    public static void e(String TAG, String message) {
        if (openLog)
            Log.e(TAG, message);
    }
}
