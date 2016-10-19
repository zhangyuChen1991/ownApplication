package com.example.rxjavademo.activity;

import android.util.Log;

import com.example.rxjavademo.base.BaseActivity;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 发布者(Observable)的不同创建方式
 * Created by zhangyu on 2016/10/19.
 */
public class ObservableCreateAct extends BaseActivity {
    private static final String TAG = "ObservableCreateAct";

    @Override
    protected void initView() {

    }

    @Override
    protected void initResources() {
        do1();
    }

    private void do1() {

        Integer[] items = {1, 2, 3, 4, 5};
        //通过from方式创建，将其它种类的对象和数据类型转换为Observable
        Observable fromObservale = Observable.from(items);
        //fromObservale 注册观察者 触发观察者的执行方法
        fromObservale.subscribe(
                new Action1<Integer>() {

                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "Action1 call " + integer);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, throwable.getMessage());
                    }
                },
                new Action0() {

                    @Override
                    public void call() {
                        Log.i(TAG, "Action0  onComplete..");
                    }
                });
    }

    @Override
    protected void initViewState() {

    }


}
