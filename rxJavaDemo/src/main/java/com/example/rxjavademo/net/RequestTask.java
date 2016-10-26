package com.example.rxjavademo.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.rxjavademo.bean.MovieBean;
import com.example.rxjavademo.bean.MoviePoster;
import com.example.rxjavademo.bean.Movies;
import com.example.rxjavademo.constants.Urls;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 执行网络任务逻辑
 * 与CallService搭配使用，是结合Retrofit与RxJava的简单封装
 * Created by zhangyu on 2016/10/24.
 */
public class RequestTask {

    /**
     * 单例模式
     *
     * @return
     */
    public static RequestTask getInstance() {
        return SingleHolder.instance;
    }
    private static final String TAG = "RequestTask";
    private static class SingleHolder {
        private static final RequestTask instance = new RequestTask();
    }

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Urls.baseUrl)
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//添加适配 使请求返回值为Observable
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private CallService callService = retrofit.create(CallService.class);

    /**
     * 获取豆瓣前250的电影
     * 在新线程执行网络任务
     * subscriber在UI线程执行
     *
     * @param subscriber 观察者 逻辑由调用方自定义
     */
    public void getMovieTop250(Subscriber subscriber) {
        callService.getMovieTop250(0, 10)
                .flatMap(new Func1<Movies, Observable<MovieBean>>() {//转换操作，获取movie列表，依次发射单个movieBean
                    @Override
                    public Observable<MovieBean> call(Movies movies) {
                        Log.d(TAG,movies.toString());
                        Observable observable = Observable.from(movies.subjects);
                        return observable;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getImage(Subscriber subscriber, final String url) {
        callService.getImage(url)
                .map(new Func1<ResponseBody, Object>() {
                    @Override
                    public Object call(ResponseBody responseBody) {
                        InputStream is = responseBody.byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        return new MoviePoster(bitmap,url);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
