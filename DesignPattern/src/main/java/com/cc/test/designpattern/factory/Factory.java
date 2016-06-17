package com.cc.test.designpattern.factory;

/**
 * Created by zhangyu on 2016-06-13 10:42.
 */
public abstract class Factory {

    /**
     * 根据传入产品类的类型获取该类对象（也可以根据其他约定好的参数来创建指定对象）
     * @param clz
     * @param <T>
     * @return
     */
    public abstract <T extends Product> T create(Class<T> clz);


}
