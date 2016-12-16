package com.sz.china.testmoudule;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.recycleview.adapter.CustomAdapter1;
import com.sz.china.testmoudule.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2016/12/13.
 */

public class NewsLayoutAct extends Activity {
    private static final String TAG = "CBAct";

    @ViewInject(R.id.acb_recyclerview)
    RecyclerView recyclerView;
    @ViewInject(R.id.acb_search_container)
    RelativeLayout searchContainer;
    @ViewInject(R.id.acb_swiperefresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CustomAdapter1 adapter;
    private List<Integer> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newslayout);
        ViewInjectUtil.injectView(this);

        initResources();
        initView();
    }

    private void initResources() {
        data = new ArrayList<>();
        for (int i = 0; i < 27; i++) {
            data.add(i);
        }
    }

    private int totalDy = 0;

    private void initView() {
        //设置是否以逐渐放大的方式出现，出现的起始位置、终点位置
        swipeRefreshLayout.setProgressViewOffset(true, DisplayUtils.dip2px(this,30),DisplayUtils.dip2px(this,60));
        swipeRefreshLayout.setColorSchemeResources(R.color.green,R.color.red);      //loading圈的颜色，最多四种
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        searchContainer.setBackgroundColor(getResources().getColor(R.color.transparent));

        adapter = new CustomAdapter1();
        adapter.setData(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                updateView(searchContainer, recyclerView, totalDy);
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    float maxHeight = 0;

    private void updateView(View searchContainer, RecyclerView target, int nowY) {
        RecyclerView recyclerView = target;
        Log.d(TAG, ". nowY = " + nowY);
        if (maxHeight == 0)//只赋一次值，相当于初始化，因为recyclerView.getChildAt(0)滚动后会变化，不能每次都重新计算
            maxHeight = recyclerView.getChildAt(0).getHeight() - searchContainer.getHeight();

        float ratio = nowY / maxHeight;
        ratio = ratio > 1 ? 1f : ratio;
        ratio = ratio < 0 ? 0f : ratio;
        float alpha = (float) Math.sin((ratio) * (Math.PI / 2f));

        EditText editText = (EditText) searchContainer.findViewById(R.id.acb_search_edittext);

        if (alpha == 0) {//容器透明 搜索框底色为白色

            searchContainer.setBackgroundColor(searchContainer.getContext().getResources().getColor(R.color.transparent));
            searchContainer.setAlpha(1);//父view设置透明度会影响子view
            editText.setBackground(searchContainer.getContext().getResources().getDrawable(R.drawable.half_circle_recentage_white));
            editText.setHintTextColor(Color.GRAY);
            editText.setAlpha(1);
        } else {//背景蓝色 透明度渐变变化
            editText.setBackground(searchContainer.getContext().getResources().getDrawable(R.drawable.half_circle_recentage_green));
            editText.setHintTextColor(Color.WHITE);
            editText.setAlpha(alpha);

            searchContainer.setBackgroundColor(searchContainer.getContext().getResources().getColor(R.color.darkturquoise1));
            searchContainer.setAlpha(alpha);
        }
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);//设置正在刷新的状态，即是否依旧转圈，false让圈消失
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };
}
