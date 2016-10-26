package com.example.rxjavademo.Bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by zhangyu on 2016/10/26.
 */

public class RxBus {

    private Subject bus;

    RxBus() {
        bus = new SerializedSubject(PublishSubject.create());
    }

    private static class SingleHolder {
        public static final RxBus Instance = new RxBus();
    }

    //单例模式
    public static RxBus getInstance() {
        return SingleHolder.Instance;
    }

    public void send(Object obj) {
        bus.onNext(obj);
    }

    public <T> Observable<T> getObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }


    /**
     private void doSend() {
     RxBus.getInstance().send("发了条消息");
     }
     */

    /**
     Subscription s = RxBus.getInstance().getObservable(String.class)
     .subscribe(new Subscriber<String>() {
    @Override public void onCompleted() {
    Log.i(TAG, "xnima  onCompleted");
    }

    @Override public void onError(Throwable e) {
    Log.e(TAG, "xnima  onError" + e.getMessage());
    }

    @Override public void onNext(String s) {
    Log.d(TAG, "xnima" + s);
    }
    });
     */

}
