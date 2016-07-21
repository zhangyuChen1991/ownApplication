package com.cc.test.designpattern.adapter;

import android.view.View;

/**
 * 非经典适配模式示例
 * 一套适配接口，使用方和实现方通过接口对接，根据具体(数据)情况的不同而执行不同的逻辑
 * Created by zhangyu on 2016-07-20 17:15.
 */
public interface BaseAdapter {

    public int getCount();
    public View getView(int position);
    public String getOrder();
}
