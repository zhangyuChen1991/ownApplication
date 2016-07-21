package com.cc.test.designpattern.share_object;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 车票工厂类
 * 包含一个对象池，如果有可利用的对象，直接取出对象使用，不再创造新的。
 * Created by zhangyu on 2016-07-21 10:31.
 */
public class TicketFactory {
    private static Map<String, Ticket> tickets = new HashMap<>();

    public static Ticket createTicket(String from, String to, int bunk) {
        String key = from + "-" + to + "-" + bunk;      //构造key
        Ticket ticket = tickets.get(key);       //从对象池中取符合条件的对象
        if (null == ticket) {       //如果对象池没有所需对象则创建一个，并存入对象池。
            Ticket ticket1 = new Ticket(from, to, bunk);
            tickets.put(key, ticket1);
            return ticket1;
        } else      //有符合条件的对象，取出并返回结果
            return ticket;
    }
}
