package com.sz.china.testmoudule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sz.china.testmoudule.recycleview.adapter.CustomAdapter;

/**
 * Created by zhangyu on 2016/10/31.
 */

public class RecyclerViewDemoMenuAct extends Activity {
    private static final String TAG = "RVDMenu";
    private RecyclerView recyclerView;
    private CustomAdapter adapter = new CustomAdapter();
    private String[] data = {"基本使用", "瀑布流"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo_menu);
        initView();
        initResources();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.arvdm_recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 点击事件监听
     */
    private CustomAdapter.RecyclerViewOnClickListener recyclerViewOnClick = new CustomAdapter.RecyclerViewOnClickListener() {
        @Override
        public void onItemClick(int position) {
            switch (position){
                case 0:
                    startActivity(new Intent(RecyclerViewDemoMenuAct.this,RecyclerViewDemoAct1.class));
                    break;
                case 1:
                    startActivity(new Intent(RecyclerViewDemoMenuAct.this,RecycleViewDemoAct.class));
                    break;
            }
        }

        @Override
        public void onTextOnclick(int position) {

        }
    };

    private void initResources() {
        adapter.setData(data);
        adapter.setRecyclerViewOnClick(recyclerViewOnClick);
        adapter.notifyDataSetChanged();
    }
}
