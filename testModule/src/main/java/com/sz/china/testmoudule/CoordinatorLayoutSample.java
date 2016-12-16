package com.sz.china.testmoudule;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by zhangyu on 2016/12/16.
 */

public class CoordinatorLayoutSample extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener{

    private static final String TAG = "CoordinatorLayoutSample";

    @ViewInject(R.id.appbar)
    AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout_sample);
        ViewInjectUtil.injectView(this);

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
//        collapsingToolbar.setContentScrimColor(Color.GRAY);//toolbar背景色
        collapsingToolbar.setTitle("title");

        mAppBarLayout.addOnOffsetChangedListener(this);//监听AppBarLayout的移动 实现AppBarLayout.OnOffsetChangedListener接口

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        Log.d(TAG,"AppBarLayout onOffsetChanged   i = "+i);
    }
}
