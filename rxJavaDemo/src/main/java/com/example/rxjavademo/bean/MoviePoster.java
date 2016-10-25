package com.example.rxjavademo.bean;

import android.graphics.Bitmap;

/**
 * Created by zhangyu on 2016/10/25.
 */

public class MoviePoster {
    public Bitmap bitmap;
    public String url;

    public MoviePoster(Bitmap bitmap,String url){
        this.bitmap = bitmap;
        this.url = url;
    }
}
