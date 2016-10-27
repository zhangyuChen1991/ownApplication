package com.example.rxjavademo.bus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * RxJava实现事件总线RxBus
 *  流程:
 *  １.创建全局的可发送可接收消息的Subject实例
 *　２.通过subject.subscribe()给自己添加订阅者(subscriber/observaber)
 *　３.通过subject.onNext()调用所有订阅者的执行方法，即所谓的”发送消息”。
 *  详细讲解:http://blog.csdn.net/chen_zhang_yu/article/details/52944633
 * Created by zhangyu on 2016/10/26.
 */

public class RxBus {

    private Subject bus;

    RxBus() {
        bus = new SerializedSubject(PublishSubject.create());
    }

    private static class BusSingleHolder {
        private static final RxBus Instance = new RxBus();
    }

    //单例模式
    public static RxBus getInstance() {
        return BusSingleHolder.Instance;
    }

    /**
     * 获取指定数据类型的Observable
     * 获取之后可以通过subscribe方法增加订阅者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> getObservable(Class<T> eventType) {
        return bus.ofType(eventType);//filter(..).cast(..)  包含过滤和转换类型两个动作，只发送出指定类型的消息
    }

    /**
     * 发送消息 即调用所有观察者的onNext()方法
     * @param obj
     */
    public void send(Object obj) {
        bus.onNext(obj);//这里调用的是所有存在的observaber的onNext()方法
    }
}


