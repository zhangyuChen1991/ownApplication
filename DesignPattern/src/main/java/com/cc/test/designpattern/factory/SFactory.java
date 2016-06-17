package com.cc.test.designpattern.factory;

/**
 * Created by zhangyu on 2016-06-13 11:09.
 */
public class SFactory extends Factory {
    @Override
    public <T extends Product> T create(Class<T> clz) {
        {
            SProduct product = null;
            try {
                product = (SProduct) Class.forName(clz.getName()).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return (T) product;
        }
    }
}
