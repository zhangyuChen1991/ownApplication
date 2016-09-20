package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;

import com.sz.china.testmoudule.bean.GreenDaoTestBean;
import com.sz.china.testmoudule.db.TestBeanDaoManager;

/**
 * 测试greendao的页面
 * Created by zhangyu on 2016/9/19.
 */
public class GreenDaoAct extends Activity {
    private static final String TAG = "GreenDaoAct";
    private GreenDaoTestBean greenDaoTestBean = new GreenDaoTestBean("anything", "anything", "anything", System.currentTimeMillis());
    private TestBeanDaoManager dbManager = TestBeanDaoManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
        init();
    }

    private void init() {

        dbManager.createDataBase();
//        dbManager.insert(greenDaoTestBean);//插入数据

//        dbManager.queryDataByNumber(1474270281139l);//通过number找数据
        greenDaoTestBean.setId(1l);
        greenDaoTestBean.setValue1("updated");
//        dbManager.update(greenDaoTestBean);//更新数据

        dbManager.delete(greenDaoTestBean);//删除数据
        dbManager.queryAll();
    }

}
