package com.sz.china.testmoudule;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sz.china.testmoudule.bean.UserBean;
import com.sz.china.testmoudule.control.ViewHandler;
import com.sz.china.testmoudule.databinding.ActivityDataBindingBinding;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * 谷歌文档：
 * https://developer.android.com/topic/libraries/data-binding/index.html
 * Created by zhangyu on 2017/1/12.
 */

public class DataBindingSampleAct extends Activity {
    private ActivityDataBindingBinding binding;
    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根据layou文件名生成Binding类  layout：activity_data_binding  Bing类：ActivityDataBindingBinding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        userBean = new UserBean("大锤", 45, "男", "2017.01.12");
        binding.setUser(userBean);
        binding.setViewHandler(new ViewHandler());
        binding.adbTv5.setText("自动获取view id");
        binding.setExampleText("Single Text");
    }

    public void onButton1Click(View v) {
        if (userBean.getAge() > 35)
            userBean.setAge(35);
        else
            userBean.setAge(45);
        ToastUtil.showToast("button1 click  改变年龄", 0);
    }
}
