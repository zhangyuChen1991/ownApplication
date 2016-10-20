package com.example.rxjavademo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.rxjavademo.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zhangyu on 2016/10/20.
 */
public class TranslateAct extends BaseActivity {
    @Override
    protected void initView() {
        
    }

    @Override
    protected void initResources() {
        doMap();
    }

    @Override
    protected void initViewState() {

    }

    String imgFilePath = "dog_img.jpeg";
    //将文件路径转换为bitmap发出 观察者直接收到bitmap进行处理
    private void doMap() {
        Observable observable = Observable.just(imgFilePath);
        observable.map(new Func1<String,Bitmap>() {

            @Override
            public Bitmap call(String imgFilePath) {
                return getBitmapFromAssets(imgFilePath);
            }
        }).subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                //TODO 显示图片
            }
        });
    }

    private Bitmap getBitmapFromAssets(String imgFilePath) {
        try {
            InputStream is = getAssets().open(imgFilePath);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
