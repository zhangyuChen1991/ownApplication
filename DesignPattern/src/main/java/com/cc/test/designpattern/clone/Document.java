package com.cc.test.designpattern.clone;

import java.util.ArrayList;

/**
 * Created by zhangyu on 2016-06-12 16:15.
 * <p>
 * 原型模式，实现Cloneable接口，实现clone()方法
 * 具体为深拷贝和浅拷贝，本例为简单的浅拷贝
 */
public class Document implements Cloneable {
    private int value1;
    private char value2 = 'a';
    private byte value3 = 1;
    private ArrayList<String> list = new ArrayList<>();
    private Document docClone;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        docClone = new Document();
        docClone.value1 = this.value1;
        docClone.value2 = this.value2;
        docClone.list = this.list;      //这里浅拷贝，只是用了list的引用，并没有进一步复制
        //docClone.list = this.list.clone();        //如果深拷贝，list应该这样写，且其余非基本类型的对象都应该进一步拷贝，对象内部的元素也同样，一直拷贝到最后一层(即所有属性都是基本类型的时候)
        return docClone;
    }

    @Override
    public String toString() {
        return "Document{" +
                "value1=" + value1 +
                ", value2='" + value2 + '\'' +
                ", list=" + list +
                '}';
    }
}
