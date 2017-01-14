package com.sz.china.testmoudule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sz.china.testmoudule.BR;

/**
 * Created by zhangyu on 2017/1/12.
 */

public class UserBean extends BaseObservable{
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


    @Bindable
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
        notifyPropertyChanged(BR.user);
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
