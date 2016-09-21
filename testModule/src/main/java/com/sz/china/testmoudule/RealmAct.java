package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.sz.china.testmoudule.bean.TestRealmBean;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * 简单测试realm
 * 只做了简单的增删改查测试，至于同异步操作、各类监听、复杂条件查询等，细查文档吧。
 * java版github地址：https://github.com/realm/realm-java
 * 文档地址：https://realm.io/docs/java/latest/
 * API文档：https://realm.io/docs/java/latest/api/
 * Created by zhangyu on 2016/9/20.
 */
public class RealmAct extends Activity{
    private static final String TAG = "RealmAct";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void doTest() {
//        TestRealmBean trBean = new TestRealmBean(4,"anthing1", System.currentTimeMillis());

        List<TestRealmBean> list = new ArrayList<>();
        list.add(new TestRealmBean(3, "anthing", System.currentTimeMillis()));

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).name("0920.realm").build();
        Realm realm = Realm.getInstance(realmConfiguration);//如果用getDefaultInstance() 则数据库名是'default.realm'


        realm.beginTransaction();

        TestRealmBean trBean = realm.createObject(TestRealmBean.class);//注意：采用这种方式创建对象，如果设置主键，会挂掉，主键应该自动生成了.创建后就直接添加到数据库  realm.copyToRealm()同样
        trBean.setValue1("anything2");
        trBean.setNumber(System.currentTimeMillis());

        //插入数据
//        realm.insert(trBean);//注意：插入主键已存在的数据对象，会挂掉.
        realm.insert(list);

        //删除所有该类数据
//        realm.delete(TestRealmBean.class);
        //删除数据
        trBean.deleteFromRealm();//注意：删除不存在的数据对象，会挂掉
        realm.commitTransaction();


//        realm.insertOrUpdate(trBean);

        //查找全部该类数据   条件查询还包括 beginwith endswith lessThan greaterThan between
        RealmResults results = realm.where(TestRealmBean.class).findAll();
        for (int i = 0; i < results.size(); i++)
            Log.d(TAG, "where(TestRealmBean.class).findAll()\n" + results.get(i).toString());


        RealmQuery realmQuery = realm.where(TestRealmBean.class).equalTo("value1", trBean.getValue1());//注意：查找没有符合条件的数据对象 会挂掉
        RealmResults results1 = realmQuery.findAll();
        for (int i = 0; i < results1.size(); i++)
            Log.i(TAG, "where(TestRealmBean.class).equalTo(\"value1\", trBean.getValue1())\n" + results1.get(i).toString());
    }
}
