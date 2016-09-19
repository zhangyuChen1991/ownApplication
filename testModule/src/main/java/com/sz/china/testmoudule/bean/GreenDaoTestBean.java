package com.sz.china.testmoudule.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by zhangyu on 2016/9/19.
 */
@Entity //建好文件后记得添加这个注解，然后编译一下，生成相应的dao、session文件
public class GreenDaoTestBean {
    private String value1;
    private String value2;
    private String value3;
    private long number;
    @Id //主键
    private Long Id;

    @Keep
    public GreenDaoTestBean(String value1, String value2, String value3, long number) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.number = number;
    }

    @Keep
    public GreenDaoTestBean() {
    }

    @Generated(hash = 781794772)
    public GreenDaoTestBean(String value1, String value2, String value3, long number, Long Id) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.number = number;
        this.Id = Id;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GreenDaoTestBean{" +
                "value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                ", number=" + number +
                '}';
    }

    public Long getId() {
        return this.Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }
}
