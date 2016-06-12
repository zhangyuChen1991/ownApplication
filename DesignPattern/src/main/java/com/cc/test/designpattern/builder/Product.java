package com.cc.test.designpattern.builder;

/**
 * Created by zhangyu on 2016-06-12 15:30.
 */
public abstract class Product {
    protected String head, hand, body, foot;
    protected String thinking;

    protected abstract void setHead();

    protected abstract void setHand();

    protected abstract void setBody();

    protected abstract void setFoot();

    //设置思想
    protected abstract void setThinking();
}
