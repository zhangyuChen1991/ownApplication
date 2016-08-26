package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cc.library.annotation.ViewInjectUtil;

/**
 * 横向滑动的页面菜单目录
 * Created by zhangyu on 2016/8/23.
 */
public class HorizontalScrollPageMenuAct extends Activity implements View.OnClickListener {

    private int zoomOutState = 0x13, zoomInState = 0x15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_page_menu);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 0:
                break;
        }
    }

}
