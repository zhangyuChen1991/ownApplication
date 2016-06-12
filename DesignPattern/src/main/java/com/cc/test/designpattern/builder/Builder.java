package com.cc.test.designpattern.builder;

/**
 * Created by zhangyu on 2016-06-12 15:30.
 */
public abstract class Builder {
    //造头
    public abstract void buildHead();
    //造手
    public abstract void buildHand();
    //造脚
    public abstract void buildFoot();
    //造身体
    public abstract void buildBody();
    //创造思想
    public abstract void buildThinking();


    //造产品（人）
    public abstract Product create();

}
