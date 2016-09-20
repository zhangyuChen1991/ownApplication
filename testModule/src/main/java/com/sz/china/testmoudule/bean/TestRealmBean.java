package com.sz.china.testmoudule.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zhangyu on 2016/9/20.
 */
public class TestRealmBean extends RealmObject {
    @PrimaryKey //主键注解
    private long id;
    private String value1;
    private long number;

    public TestRealmBean(long id, String v1, long number){
        this.id = id;
        this.value1 = v1;
        this.number = number;
    }

    public TestRealmBean(){};

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    @Override
    public String toString() {
        return "TestRealmBean{" +
                "id=" + id +
                ", value1='" + value1 + '\'' +
                ", number=" + number +
                '}';
    }
}
