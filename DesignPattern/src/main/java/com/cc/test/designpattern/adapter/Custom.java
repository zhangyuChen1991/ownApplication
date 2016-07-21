package com.cc.test.designpattern.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 接口使用者
 * 通过调用同一套接口与适配器对接，在适配器中数据不同的情况下，可以呈现出不同的处理结果。
 * 这样将具体需求与具体数据与对应的处理逻辑解耦。
 * Created by zhangyu on 2016-07-20 17:17.
 */
public class Custom {

    View[] views;
    BaseAdapter baseAdapter;

    public void setBaseAdapter(BaseAdapter baseAdapter) {
        this.baseAdapter = baseAdapter;
    }

    private void doSomething() {
        int count = baseAdapter.getCount();

        String order = baseAdapter.getOrder();
        for (int i = 0; i < count; i++) {
            System.out.println(order);
        }
    }

    private void initViews() {
        View[] views = new View[baseAdapter.getCount()];
    }

    private void setViewInPosition(int position) {
        views[position] = baseAdapter.getView(position);

    }
}
