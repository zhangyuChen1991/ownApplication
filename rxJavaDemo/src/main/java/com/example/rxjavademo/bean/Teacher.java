package com.example.rxjavademo.bean;

/**
 * Created by zhangyu on 2016/10/20.
 */
public class Teacher {
    public String name;
    public int age;
    public String workSpace;

    public Teacher(String name, int age, String workSpace) {
        this.name = name;
        this.age = age;
        this.workSpace = workSpace;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", workSpace='" + workSpace + '\'' +
                '}';
    }
}
