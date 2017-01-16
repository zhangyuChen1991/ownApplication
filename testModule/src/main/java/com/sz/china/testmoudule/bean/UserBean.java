package com.sz.china.testmoudule.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.sz.china.testmoudule.BR;

/**
 * DataBinding绑定的数据例子
 * 如果继承BaseObservable可以通过@Bindable注解绑定数据进而使用notifyPropertyChanged方法，在数据变化时通知页面更新
 * BR相当于一个R文件 将binding类里面的成员变量的id进行列表
 * (有时候会报红 需要重新编译运行一遍才行)
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
        notifyPropertyChanged(BR.age);
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);//通知 数据改变
    }

    @Bindable
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
        notifyPropertyChanged(BR.sex);
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
