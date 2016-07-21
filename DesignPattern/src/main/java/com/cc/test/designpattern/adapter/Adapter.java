package com.cc.test.designpattern.adapter;

import android.view.View;

/**
 * 具体实现接口的一个类Adapter
 * Created by zhangyu on 2016-07-20 17:15.
 */
public class Adapter implements BaseAdapter{
    @Override
    public int getCount() {
        //TODO 根据实际需求处理逻辑
        return 0;
    }

    @Override
    public View getView(int position) {
        //TODO 根据实际需求处理逻辑
        return null;
    }

    @Override
    public String getOrder() {
        //TODO 根据实际需求处理逻辑
        return "i an adapter";
    }
}
