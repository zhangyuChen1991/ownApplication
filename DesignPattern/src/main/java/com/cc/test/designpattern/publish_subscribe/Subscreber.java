package com.cc.test.designpattern.publish_subscribe;

/**
 * 发布-订阅者模式(观察者模式) 订阅者
 * Created by zhangyu on 2016-07-20 16:55.
 */
public class Subscreber {

    public void onReceive(String msg) {
        System.out.println("订阅者收到了消息.. " + msg);
    }
}
