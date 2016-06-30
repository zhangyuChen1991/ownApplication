package com.cc.test.designpattern.singleton;

/**
 * Created by zhangyu on 2016-06-07 15:50.
 * 枚举实现单例模式，写法简单。且枚举与普通类一样，可以有自己的属性，方法。
 * 并且，枚举默认是线程安全的
 * 并且，在反序列化时不会被重新创建对象
 * 总结：简单好用
 */
public enum SingletonEnum {
    INSTANCE;


    public int valueInt;
    public void doSomething() {
    }
}



