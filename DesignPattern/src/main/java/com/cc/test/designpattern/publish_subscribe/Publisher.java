package com.cc.test.designpattern.publish_subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * 发布-订阅者模式(观察者模式) 发布者
 * Created by zhangyu on 2016-07-20 16:54.
 */
public class Publisher {
    //订阅者集合
    List<Subscreber> subscrebers = new ArrayList<>();

    /**
     * 发布
     */
    protected void publish(String msg) {
        for (int i = 0; i < subscrebers.size(); i++) {
            subscrebers.get(i).onReceive(msg);
        }
    }

    /**
     * 添加订阅者
     */
    public boolean subscribe(Subscreber subscreber) {
        if (null != subscreber) {
            subscrebers.add(subscreber);
            return true;
        }

        return false;
    }

    /**
     * remove clean 订阅者等方法 省略..
     */
}
