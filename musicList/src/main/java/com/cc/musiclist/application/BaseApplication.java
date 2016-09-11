package com.cc.musiclist.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangyu on 2016-06-16 16:44.
 */
public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
