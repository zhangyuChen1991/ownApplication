package com.sz.china.testmoudule.util;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 处理json字符串的工具类 对服务器返回的json数据做处理
 *
 * @author zhangyu
 * @date 2016-3-11
 */
public class JsonUtil {
    private static final String TAG = "JsonUtil";
    private static Object ret;

    /**
     * 从指定json对象中获取指定键的值
     *
     * @param jsonObject json对象
     * @param targetKey  指定键
     * @return
     */
    public static Object getValue(JSONObject jsonObject, String targetKey) {
        decodeJson(jsonObject, targetKey);
        return ret;
    }

    /**
     * 解析json
     *
     * @param jsonObject json对象
     * @param targetKey  指定键
     */
    public static void decodeJson(JSONObject jsonObject, String targetKey) {
        ret = null;
        if (TextUtils.isEmpty(targetKey))
            return;
        try {
            Iterator<String> keys = jsonObject.keys();
            JSONObject jo = null;
            JSONArray ja = null;
            Object o = null;
            String key;
            while (keys.hasNext()) {
                key = keys.next();
                Log.v(TAG, "key = " + key);
                if (targetKey.equals(key)) {
                    ret = jsonObject.get(key);
                    return;
                } else {
                    o = jsonObject.get(key);
                    if (o != null) {
                        if (o instanceof JSONObject) {
                            jo = (JSONObject) o;
                            decodeJson(jo, targetKey); // 递归遍历
                        } else if (o instanceof JSONArray) {
                            ja = (JSONArray) o;
                            for (int i = 0; i < ja.length(); i++) { // 遍历数组
                                JSONObject jo1 = ja.getJSONObject(i);
                                decodeJson(jo1, targetKey); // 递归遍历
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "JsonUtil.getValue  json 处理异常!");
            e.printStackTrace();
        }
    }

    /**
     * 从指定json对象中获取指定键的值 使用时请先对返回值判空再转换类型
     *
     * @param
     * @param targetKey 指定键
     * @return 获取的对象 或一个new Object();
     */
    public static Object getValue(String json, String targetKey) {
        try {
            ret = new Object();
            JSONObject jsonObject = new JSONObject(json);
            decodeJson(jsonObject, targetKey);
        } catch (JSONException e) {
            Log.e(TAG, "JsonUtil.getValue  json 处理异常!");
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 从指定json对象中获取指定键的值转换成String的值
     *
     * @param
     * @param targetKey 指定键
     * @return 指定获取的值或者""
     */
    public static String getValueToString(String json, String targetKey) {
        try {
            ret = null;
            JSONObject jsonObject = new JSONObject(json);
            decodeJson(jsonObject, targetKey);
        } catch (JSONException e) {
            Log.e(TAG, "JsonUtil.getValue  json 处理异常!");
            e.printStackTrace();
        }
        return ret == null ? "" : ret.toString();
    }

    /**
     * 将服务器返回的json数据中 指定类型的json数据转换为指定类型对象 存入list中返回
     *
     * @param json      json数据
     * @param targetKey 指定key值
     * @param cls       指定对象类型
     * @return 返回此类型信息的对象集合
     */
    public static <T> List<T> getObjectList(String json, String targetKey, Class<T> cls) {
        if (TextUtils.isEmpty(json) || TextUtils.isEmpty(targetKey))
            return null;

        List<T> list = new ArrayList<T>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            list = getObjectList(jsonObject, targetKey, cls);
        } catch (JSONException e) {
            Log.e(TAG, "json 处理异常! json = " + json);
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 将json对象中 指定类型的json数据转换为指定类型对象 存入list中返回
     *
     * @param
     * @param targetKey 指定key值
     * @param cls       指定对象类型
     * @return 返回此类型信息的对象集合
     */
    public static <T> List<T> getObjectList(JSONObject jsonObject, String targetKey, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Object targetJson = jsonObject.get(targetKey);
            if (targetJson instanceof JSONObject) {
                JSONObject jo = (JSONObject) targetJson;
                list.add(GsonUtil.fromJson(jo.toString(), cls));
                return list;
            } else if (targetJson instanceof JSONArray) {
                JSONArray targetJson1 = (JSONArray) targetJson;
                for (int i = 0; i < targetJson1.length(); i++) {
                    list.add(GsonUtil.fromJson(targetJson1.getJSONObject(i).toString(), cls));
                }
                return list;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 从json数组字符串中获取指定对象集合
     *
     * @param jsonArray
     * @param cls
     * @return
     */
    public static <T> List<T> getObjectListFromJsonArry(String jsonArray, Class<T> cls) {
        try {
            JSONArray jsArry = new JSONArray(jsonArray);
            return getObjectListFromJsonArry(jsArry, cls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<T>();
    }

    /**
     * 从json数组中获取指定对象集合
     *
     * @param jsonArray
     * @param cls
     * @return
     */
    public static <T> List<T> getObjectListFromJsonArry(JSONArray jsonArray, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            if (null != jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(GsonUtil.fromJson(jsonArray.getJSONObject(i).toString(), cls));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
