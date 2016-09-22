package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.util.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
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

        String url = "https://www.baidu.com/";
        getAsyn(url);
//        getSync(url);
    }

    /**
     * 异步get请求
     *
     * @param url
     */
    public void getAsyn(final String url) {
        OkHttpUtil.getInstances().getAsyn(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, url + "failure!  IOException:" + e.toString());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String str = response.body().string();//除了string 还有byte、bytestream(支持下载)等
                OkHttpAct.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText(response.headers().toString() + "\n\n" + str);
                    }
                });
            }
        });
    }

    /**
     * @param url
     */
    // 这里用起来虽然看着简洁，但是内部封装的很烂
    public void getSync(final String url) {
        OkHttpUtil.getInstances().getSync(url, new OkHttpUtil.OkHttpUtilCallBack() {
            @Override
            public void onSuccess(String response) {
                tv.setText(response);
            }

            @Override
            public void onFailure(IOException e) {

            }
        }, this);
    }

}
