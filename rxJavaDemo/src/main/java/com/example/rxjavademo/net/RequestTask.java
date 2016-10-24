package com.example.rxjavademo.net;

import com.example.rxjavademo.constants.Urls;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getImage(Subscriber subscriber, String url) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://img3.doubanio.com/view/movie_poster_cover/ipst/public/").addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        CallService callService = retrofit.create(CallService.class);
        callService.getImage(url)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
