package com.cc.test.designpattern.share_object;

/**
 * 列车票 数据类
 * Created by zhangyu on 2016-07-21 10:28.
 */
public class Ticket {
    //始发地 目的地
    public String form,to;
    //铺位 价格
    public int bunk,price;

    /**
     * 构造方法
     * @param from 始发地
     * @param to 目的地
     * @param bunk 铺位
     */
    public Ticket(String from,String to,int bunk){
        this.form = from;
        this.to = to;
        this.bunk = bunk;
    }
}
