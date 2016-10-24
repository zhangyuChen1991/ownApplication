package com.example.rxjavademo.activity;

import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.MovieBean;
import com.example.rxjavademo.bean.Movies;
import com.example.rxjavademo.net.RequestTask;

import java.io.InputStream;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by zhangyu on 2016/10/24.
 */
public class RetrofitAndRxJavaAct extends BaseActivity {
    private static final String TAG = "Ret&RxAct";

    @ViewInject(R.id.asd_tv)
    private TextView tv;
    private StringBuffer sb = new StringBuffer("Retrofit和RxJava结合的Demo测试\n\n");

    @Override
    protected void initView() {
        setContentView(R.layout.act_simple_demo);
    }

    @Override
    protected void initResources() {
        RequestTask.getInstance().getMovieTop250(getMovieTop250Subscriber);
    }

    @Override
    protected void initViewState() {

    }

    private Subscriber<Movies> getMovieTop250Subscriber = new Subscriber<Movies>() {
        @Override
        public void onCompleted() {
            Log.i(TAG, "getMovieTop250Subscriber   onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "getMovieTop250Subscriber   onError" + e.toString());
        }

        @Override
        public void onNext(Movies movies) {
            List<MovieBean> movieList = movies.subjects;
            for(MovieBean movie:movieList){
                RequestTask.getInstance().getImage(getMovieImg,movie.images.medium);
            }
            Log.d(TAG, "getMovieTop250Subscriber   onNext:\n");
        }
    };

    private Subscriber<ResponseBody> getMovieImg = new Subscriber<ResponseBody>() {
        @Override
        public void onCompleted() {
            Log.i(TAG, "getMovieImg   onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "getMovieImg   onError" + e.toString());
        }

        @Override
        public void onNext(ResponseBody o) {
            InputStream is = o.byteStream();
            //TODO 输入流转换为bitmap,把图片显示到页面上
//            Log.d(TAG, o instanceof InputStream ? "is inputstream" : "not inputstream");

        }
    };
}
