package com.cc.test.designpattern.share_object;

/**
 * Created by zhangyu on 2016-07-21 10:39.
 */
public class Custom {

    private void buyTicket(){
        TicketFactory.createTicket("广州","香港",0);
    }
}

