package com.cc.test.designpattern.builder.impl;

import com.cc.test.designpattern.builder.Product;

/**
 * Created by zhangyu on 2016-06-12 15:39.
 */
public class ToyPerson extends Product {
    @Override
    protected void setHead() {
        head = "玩具头";
    }

    @Override
    protected void setHand() {
        hand = "玩具手";
    }

    @Override
    protected void setBody() {
        body = "玩具身体";
    }

    @Override
    protected void setFoot() {
        foot = "玩具脚";
    }

    @Override
    protected void setThinking() {
        thinking = "我是个玩具，没有思想";
    }
}
