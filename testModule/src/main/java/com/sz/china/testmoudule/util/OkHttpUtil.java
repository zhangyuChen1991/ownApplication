package com.sz.china.testmoudule.util;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * okhttp的使用的简单代码，不是封装
 * Created by zhangyu on 2016/9/21.
 */
public class OkHttpUtil {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private static class SingleHolder {
        private static OkHttpUtil okHttpUtil = new OkHttpUtil();
    }

    public static OkHttpUtil getInstances() {
        return SingleHolder.okHttpUtil;
    }

    /**
     * 获取okhttp实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 异步get请求
     *
     * @param url
     */
    public void getAsyn(String url) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 同步get请求
     *
     * @param url
     * @throws IOException
     */
    public void getSync(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {//请求返回成功

        } else {//请求返回失败

        }
    }

    /**
     * 同步 post提交json
     *
     * @param url
     * @param json
     * @throws IOException
     */
    public void postJsonSync(String url, String json) throws IOException {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {//请求返回成功

        } else {//请求返回失败

        }
    }

    /**
     * 同步 post提交键值对 参数比较麻烦，不好封装，具体拷出去用吧
     * @param url
     */
    public void postKVSync(String url) throws IOException {
        RequestBody fromBody = new FormBody.Builder()
                .add("key1", "value1")
                .add("key2", "value2").build();

        Request request = new Request.Builder().url(url).post(fromBody).build();
        Response response = okHttpClient.newCall(request).execute();

        if (response.isSuccessful()) {//请求返回成功

        } else {//请求返回失败

        }
    }

    /**
     * 异步 post提交键值对 参数比较麻烦，不好封装，具体拷出去用吧
     * @param url
     */
    public void postKVAsyn(String url){
        RequestBody fromBody = new FormBody.Builder()
                .add("key1", "value1")
                .add("key2", "value2").build();

        Request request = new Request.Builder().url(url).post(fromBody).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {//请求返回成功

                } else {//请求返回失败

                }
            }
        });
    }

    /**
     * 根据不同类型的RequestBody可以用post提交不同的数据 提交文件、字符串、流等
     */
}
