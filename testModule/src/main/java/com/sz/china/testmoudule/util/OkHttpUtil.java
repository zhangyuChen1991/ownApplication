package com.sz.china.testmoudule.util;


import android.app.Activity;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * okhttp的使用的部分demo简单代码，异步请求进行了简单封装，可用。
 * 同步操作封装的很烂，亟待改进，暂不建议用，仅当demo代码看。
 * Created by zhangyu on 2016/9/21.
 */
public class OkHttpUtil {
    private OkHttpClient okHttpClient = new OkHttpClient();

    private static class SingleHolder {
        private static OkHttpUtil okHttpUtil = new OkHttpUtil();
    }

    //单例
    public static OkHttpUtil getInstances() {
        return SingleHolder.okHttpUtil;
    }

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    /**
     * 异步get请求
     * callback回调仍然在子线程，处理组件需要另外到主线程执行
     *
     * @param url      请求url地址
     * @param callback 请求结果的回调处理
     */
    public void getAsyn(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步get请求 同步操作不能在主线程执行，需要另起子线程
     * 备注：这个封装不好 虽然实现了子线程请求、主线程回调。  通常需求还是应该多用异步请求
     *
     * @param url
     * @throws IOException
     */
    public void getSync(final String url, final OkHttpUtilCallBack callBack, final Activity context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder().url(url).build();
                    Response response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()) {//请求返回成功
                        final String resp = response.body().string();
                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callBack.onSuccess(resp);
                            }
                        });

                    } else {//请求返回失败
                        callBack.onFailure(null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 异步 post提交json
     * callback回调仍然在子线程，处理组件需要另外到主线程执行
     *
     * @param url      请求url地址
     * @param json     要提交的json
     * @param callback 请求结果的回调处理
     */
    public void postJsonAsyn(String url, String json, Callback callback) {
        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, json);

        Request request = new Request.Builder().url(url).post(requestBody).build();

        okHttpClient.newCall(request).enqueue(callback);

    }

    /**
     * 同步 post提交json
     *
     * @param url  请求url地址
     * @param json 要提交的json
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
     * 异步 post提交string
     *
     * @param url      请求url地址
     * @param str      要提交的字符串
     * @param callback 请求结果的回调处理
     */
    public void postStringAsyn(String url, String str, Callback callback) {
        MediaType stringType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(stringType, str);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步 post提交文件
     *
     * @param url      请求url地址
     * @param filePath 要提交的文件路径
     * @param callback 请求结果的回调处理
     */
    public void postFileAsyn(String url, String filePath, Callback callback) {
        File file = new File(filePath);
        if (null == file || !file.exists()) return;

        MediaType fileType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(fileType, file);
        Request request = new Request.Builder().url(url).post(requestBody).build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 异步 post提交流
     * 这个例子是流直接写入Okio的BufferedSink。你的程序可能会使用OutputStream，你可以使用BufferedSink.outputStream()来获取。
     * @param url
     * @param callback
     * @throws Exception
     */
    public void postStreamSync(String url ,Callback callback) throws Exception {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = new RequestBody() {
            @Override
            public MediaType contentType() {
                return MEDIA_TYPE_MARKDOWN;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.writeUtf8("Numbers\n");
                sink.writeUtf8("-------\n");
                for (int i = 2; i <= 997; i++) {
                    sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                }
            }

            private String factor(int n) {
                for (int i = 2; i < n; i++) {
                    int x = n / i;
                    if (x * i == n) return factor(x) + " × " + i;
                }
                return Integer.toString(n);
            }
        };

        Request request = new Request.Builder()
                .url(url)//"https://api.github.com/markdown/raw"
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * 同步 post提交键值对 参数比较麻烦，不好封装，具体拷出去用吧
     *
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
            throw new IOException();
        }
    }

    /**
     * 异步 post提交键值对 参数比较麻烦，不好封装，具体拷出去用吧
     *
     * @param url
     */
    public void postKVAsyn(String url) {
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
     * 根据不同类型的RequestBody可以用post提交不同的数据 提交文件、字符串、流、表单、分块请求等
     */

    public interface OkHttpUtilCallBack {
        void onSuccess(String response);

        void onFailure(IOException e);
    }

}
