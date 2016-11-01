package com.sz.china.testmoudule;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.util.DialogUtils;
import com.sz.china.testmoudule.util.OkHttpUtil;
import com.sz.china.testmoudule.util.SystemUtil;
import com.sz.china.testmoudule.util.ToastUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 包含学习okhttp时的一些demo代码(现在已经没用了，retrofit2对其作了非常好的封装)
 * retrofit的demo代码
 * Created by zhangyu on 2016/9/21.
 */
public class RetrofitAndOkHttpAct extends Activity {
    private static final String TAG = "RetrofitAndOkHttpAct";
    @ViewInject(R.id.tv)
    private TextView tv;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);
        ViewInjectUtil.injectView(this);
        loadingDialog = DialogUtils.createLoadingDialog(this,"加载中..");

        String url = "https://www.baidu.com/";
//        getAsyn(url);
//        getSync(url);
        doRetrofitCall();
//        doTestLogin();//调试通过后，敏感常量已改，代码可以参考，无法运行

    }

    /**
     * 异步get请求
     *
     * @param url
     */
    public void getAsyn(final String url) {
        OkHttpUtil.getInstances().getAsyn(url, new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e(TAG, url + "failure!  IOException:" + e.toString());
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                final String str = response.body().string();//除了string 还有byte、bytestream(支持下载)等
                RetrofitAndOkHttpAct.this.runOnUiThread(new Runnable() {
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

    //retrofit demo 特么的好好学吧！
    public static final String API_URL = "https://api.github.com";

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

    public static class Result {
        public final String result_data;
        public final String result_code;

        public Result(String result_data, String result_code) {
            this.result_code = result_code;
            this.result_data = result_data;
        }
    }

    public interface GitHub {
        //@Path中的参数代替路径里面{}中的内容，按照下面的程序来设置，最后url等同于 https://api.github.com/repos/square/retrofit/contributors
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);

        //@QUery设置参数 按照下面的程序来设置，最后url等同于 http://api.epuxun.com/product/login?devId=保密&appId=保密&appSecret=保密&username=rhyro&password=rhyro
        @POST("/product/login")
        Call<Result> login(@Query("devId") String devId,
                           @Query("appId") String appId,
                           @Query("appSecret") String appSecret,
                           @Query("username") String user,
                           @Query("password") String pasword);

        @GET("")
        Call<Result> test(@FieldMap HashMap map);

        @FormUrlEncoded
        @POST("")
        Call<Result> test1(@Field("") String map);


    }

    final static String BaseURL = "http://api.epuxun.com";
    //应用识别码
    public static String appId = "保密";//此处调试通过后，常量已改，代码可参考
    //应用秘钥
    public static String appSecret = "保密";//此处调试通过后，常量已改，代码可参考

    public void doTestLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHub github = retrofit.create(GitHub.class);
        Call<Result> call = github.login(SystemUtil.getDeviceId(), appId, appSecret, "rhyro", "rhyro");

        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                ToastUtil.showToast("success", 0);
                String rc = response.body().result_code;
                String rd = response.body().result_data;

                tv.setText(rc + "\n" + rd);
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                ToastUtil.showToast("onFailure", 0);
            }
        });
    }

    private void doRetrofitCall() {
        loadingDialog.show();
        // Create a very simple REST adapter which points the GitHub API.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create an instance of our GitHub API interface.
        GitHub github = retrofit.create(GitHub.class);

        // Create a call instance for looking up Retrofit contributors.
        Call<List<Contributor>> call = github.contributors("square", "retrofit");

        // Fetch and print a list of the contributors to the library.
        // List<Contributor> contributors = call.execute().body();//execute 同步操作，没有在子线程执行，还是需要自己起线程

        call.enqueue(new Callback<List<Contributor>>() {
            @Override
            public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
                ListIterator<Contributor> contributors = response.body().listIterator();
                StringBuffer sb = new StringBuffer();
                while (contributors.hasNext()) {
                    Contributor contributor = contributors.next();
                    Log.d(TAG, contributor.login + " (" + contributor.contributions + ")");
                    sb.append(contributor.login + " (" + contributor.contributions + ")\n");
                }
                tv.setText(sb.toString());
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Contributor>> call, Throwable t) {
                loadingDialog.dismiss();
                ToastUtil.showToast("网络错误",0);
            }
        });
    }
}
