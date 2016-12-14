package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.adapter.ViewPagerAdapter;
import com.sz.china.testmoudule.control.ColorChangeBehavior;
import com.sz.china.testmoudule.recycleview.adapter.CustomAdapter1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2016/12/13.
 */

public class CoordinatorLayoutBehaviorAct extends Activity {

    @ViewInject(R.id.acb_recyclerview)
    RecyclerView recyclerView;
    @ViewInject(R.id.acb_search_container)
    RelativeLayout searchContainer;

    private CustomAdapter1 adapter;
    private List<Integer> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinatorlayout_behavior);
        ViewInjectUtil.injectView(this);

        initResources();
        initView();
    }

    private void initResources() {
        data = new ArrayList<>();
        for(int i = 0;i < 27;i++){
            data.add(i);
        }

        //子view设置behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) recyclerView.getLayoutParams();
        params.setBehavior(new ColorChangeBehavior());
    }

    private void initView() {
        adapter = new CustomAdapter1();
        adapter.setData(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
