package com.sz.china.testmoudule.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangyu on 2016/9/21.
 */
public class OkHttpAct extends Activity {
    private static final String TAG = "OkHttpAct";
    @ViewInject(R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ViewInjectUtil.injectView(this);

        getAsyn("https://www.baidu.com/");
    }

    /**
     * 异步get请求
     *
     * @param url
     */
    public void getAsyn(final String url) {

        Request request = new Request.Builder().url(url).build();
        OkHttpUtil.getInstances().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {//请求返回成功
                    final String str = response.body().string();//除了string 还有byte、bytestream(支持下载)等
                    OkHttpAct.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(response.headers().toString() + "\n\n" + str);
                        }
                    });
                } else {//请求返回失败
                    Log.e(TAG, url + "  请求失败!");
                }
            }
        });
    }

}
