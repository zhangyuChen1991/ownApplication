package com.example.rxjavademo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;

import java.io.IOException;
import java.io.InputStream;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/10/20.
 */
public class TranslateAct extends BaseActivity {
    private static final String TAG = "TranslateAct";
    @ViewInject(R.id.at_iv)
    private ImageView iv;


    @Override
    protected void initView() {
        setContentView(R.layout.act_translate);

    }

    @Override
    protected void initResources() {
        doMap();
    }

    @Override
    protected void initViewState() {

    }

    String imgFilePath = "dog_img.jpeg";


    private void doMap() {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                //将文件路径转换为bitmap发出 观察者直接收到bitmap进行处理
                Observable observable = Observable.just(imgFilePath);
                observable.map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String imgFilePath) {
                        return getBitmapFromAssets(imgFilePath);
                    }
                }).subscribeOn(Schedulers.immediate())//当前线程()发布
                        .observeOn(AndroidSchedulers.mainThread())//主线程执行
                        .subscribe(new Subscriber<Bitmap>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "observable.map(..)  onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "observable.map(..)  onError" + e.getMessage());
                            }

                            @Override
                            public void onNext(Bitmap bitmap) {
                                //显示图片
                                iv.setImageBitmap(bitmap);
                            }
                        });
            }
        };
        new Thread(run).start();
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
