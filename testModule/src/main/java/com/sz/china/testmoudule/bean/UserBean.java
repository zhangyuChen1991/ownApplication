package com.sz.china.testmoudule.bean;

/**
 * Created by zhangyu on 2017/1/12.
 */

public class UserBean {
    private String name;
    private int age;
    private String sex;
    private String date;
    private boolean isRich;

    public boolean isRich() {
        return isRich;
    }

    public void setRich(boolean rich) {
        isRich = rich;
    }



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public UserBean(String name,int age,String sex,String date){
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.date = date;
    }
}
