package com.example.rxjavademo.activity;

import android.util.Log;

import com.cc.utilcode.utils.ToastUtils;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bus.RxBus;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by zhangyu on 2016/10/27.
 */

public class RxBusAct extends BaseActivity {

    private static final String TAG = "xBusAct";


    @Override
    protected void initView() {
        ToastUtils.showLongToast(this,"页面没有内容，去看代码吧");
    }

    @Override
    protected void initResources() {

    }

    @Override
    protected void initViewState() {
        doSimple();
        doDemo();
    }

    private void doSimple() {
        //RxBus的主干部分抽取出来，帮助理解整个流程
        Subject subject = PublishSubject.create();

        //订阅操作，添加订阅者
        subject.subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "receive:  " + o.toString());
            }
        });

        //执行订阅者的onNext方法，完成"消息发送"
        subject.onNext("send something");
    }


    /**
     * RxBus的使用demo
     * 可以在任意地方发送，在任意地方接收
     */
    private void doDemo(){
        //发送方
        RxBus.getInstance().send("发了条消息");

        //接收方
        Subscription subscription = RxBus.getInstance().getObservable(String.class)
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "receive: " + s);
                    }
                });

        //解绑
        subscription.unsubscribe();
    }
}
