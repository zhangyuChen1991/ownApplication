package com.example.rxjavademo.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangyu on 2016/10/19.
 */
public class BaseApplication extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
