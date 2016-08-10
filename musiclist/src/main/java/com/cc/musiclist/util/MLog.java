package com.cc.musiclist.util;

import android.util.Log;

/**
 * Created by zhangyu on 2016-07-01 19:14.
 */
public class MLog {
    public static boolean openLog = true;

    public static void v(String TAG, String message) {
        if (openLog)
            android.util.Log.v(TAG, message);
    }

    public static void d(String TAG, String message) {
        if (openLog)
            android.util.Log.d(TAG, message);
    }

    public static void i(String TAG, String message) {
        if (openLog)
            android.util.Log.i(TAG, message);
    }

    public static void w(String TAG, String message) {
        if (openLog)
            android.util.Log.w(TAG, message);
    }

    public static void e(String TAG, String message) {
        if (openLog)
            android.util.Log.e(TAG, message);
    }
}
