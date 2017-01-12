package com.sz.china.testmoudule.control;

import android.view.View;

import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by zhangyu on 2017/1/12.
 */

public class ViewHandler {
    /**
     * dataBinding 绑定事件
     * @param view
     */
    public void onButton1Click(View view){
        ToastUtil.showToast("button1 click",0);
    }
}
