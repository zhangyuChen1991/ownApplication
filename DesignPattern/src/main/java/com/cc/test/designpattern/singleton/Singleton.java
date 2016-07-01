package com.cc.test.designpattern.singleton;

import java.io.ObjectStreamException;

/**
 * Created by zhangyu on 2016-06-07 15:51.
 * 一种比较推荐的单例写法(反序列化未处理)
 */
public class Singleton {
    private Singleton(){}

    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }

    private static class SingletonHolder{
        private static final Singleton instance = new Singleton();
    }
}
