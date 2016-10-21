package com.example.rxjavademo.base;

import android.app.Activity;
import android.os.Bundle;

import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by zhangyu on 2016/10/19.
 */
public abstract class BaseActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        ViewInjectUtil.injectView(this);
        initResources();
        initViewState();
    }

    protected abstract void initView();

    protected abstract void initResources();

    protected abstract void initViewState();
}
