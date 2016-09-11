package com.sz.china.testmoudule;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.sz.china.testmoudule.recycleview.divider.GridDivider;

import java.util.ArrayList;

public class RecycleViewDemoAct extends AppCompatActivity {

    private ArrayList<String> data;
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private ArrayList<Integer> mHeights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        initResources();
        initView();

    }

    private void initResources() {
        mAdapter = new MyAdapter();

        data = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            data.add("第" + i + "个位置");
        }

        mHeights = new ArrayList<Integer>();
        for (int i = 0; i < data.size(); i++) {
            mHeights.add((int) (100 + Math.random() * 300));
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);

        recyclerView.addItemDecoration(new GridDivider(this));//(new LinearDivider(this,LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

    }

    private class MyLayoutManager extends RecyclerView.LayoutManager {
        @Override
        public RecyclerView.LayoutParams generateDefaultLayoutParams() {
            return null;
        }
    }

    /**
     * 继承抽象类ViewHolder完成自定义布局数据的容器类，构造方法中要实现itemView模板，作为参数传入
     */
    private class MyViewHolder extends ViewHolder {
        private TextView tv;
        private RelativeLayout container;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.adapter_tv);
            container = (RelativeLayout) itemView.findViewById(R.id.adapter_container);
        }
    }

    /**
     * 继承抽象类RecyclerView.Adapter实现方法完成布局模板的设置和内容的填充
     */
    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder viewHolder = new MyViewHolder(View.inflate(RecycleViewDemoAct.this, R.layout.adpter, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(data.get(position));
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) holder.container.getLayoutParams();
            param.height = mHeights.get(position);

            holder.container.setLayoutParams(param);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
