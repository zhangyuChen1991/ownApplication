package com.cc.musiclist.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.cc.musiclist.application.BaseApplication;

import java.util.Set;

/**
 * Created by zhangyu on 2016-06-20 14:26.
 */
public class SpUtil {
    private static Context context = BaseApplication.context;
    private static SharedPreferences sp = context.getSharedPreferences("cache",Context.MODE_PRIVATE);
    private static SharedPreferences.Editor editor = sp.edit();

    public static void put(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }

    public static void put(String key,int value){
        editor.putInt(key,value);
        editor.commit();
    }

    public static void put(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public static void put(String key,long value){
        editor.putLong(key,value);
        editor.commit();
    }

    public static void put(String key,float value){
        editor.putFloat(key,value);
        editor.commit();
    }

    public static void put(String key,Set<String> value){
        editor.putStringSet(key,value);
        editor.commit();
    }

    public static Boolean getBoolean(String key,boolean defaultValue){
        return sp.getBoolean(key,defaultValue);
    }

    public static int getInt(String key,int defaultValue){
        return sp.getInt(key,defaultValue);
    }

    public static Float getFloat(String key,float defaultValue){
        return sp.getFloat(key,defaultValue);
    }

    public static Long getLong(String key,long defaultValue){
        return sp.getLong(key,defaultValue);
    }

    public static String getString(String key,String defaultValue){
        return sp.getString(key,defaultValue);
    }

    public static Set<String> getStringSet(String key,Set<String> defaultValue){
        return sp.getStringSet(key,defaultValue);
    }

}
