package com.cc.test.designpattern.builder.impl;

import com.cc.test.designpattern.builder.Builder;
import com.cc.test.designpattern.builder.Product;

/**
 * Created by zhangyu on 2016-06-12 15:40.
 */
public class ToyPersonBuilder extends Builder {
    private ToyPerson toyPerson;

    @Override
    public void buildHead() {
        toyPerson.setHead();
    }

    @Override
    public void buildHand() {
        toyPerson.setHand();
    }

    @Override
    public void buildFoot() {
        toyPerson.setFoot();
    }

    @Override
    public void buildBody() {
        toyPerson.setBody();
    }

    @Override
    public void buildThinking() {
        toyPerson.setThinking();
    }

    @Override
    public Product create() {
        return toyPerson;
    }
    /**
     * 对象的属性和构造过程对外部不可见
     */
}
