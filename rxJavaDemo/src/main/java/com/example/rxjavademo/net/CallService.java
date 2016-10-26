package com.example.rxjavademo.net;


import com.example.rxjavademo.bean.Movies;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by zhangyu on 2016/10/24.
 */

public interface CallService {
    //获取豆瓣top250的电影
    @GET("top250")
    Observable<Movies> getMovieTop250(@Query("start") int startNum, @Query("count") int countNum);

    @GET
    Observable<ResponseBody> getImage(@Url String url);//获取图片 url不定

}
