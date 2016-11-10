package com.sz.china.testmoudule;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sz.china.testmoudule.recycleview.adapter.CustomAdapter;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by zhangyu on 2016/10/31.
 */

public class RecyclerViewDemoAct1 extends Activity {
    private RecyclerView recyclerView;
    private CustomAdapter adapter = new CustomAdapter();
    private String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo1);


        initView();
        initResources();
    }

    private void initView() {
        adapter.setRecyclerViewOnClick(recyclerViewOnClickListener);
        recyclerView = (RecyclerView) findViewById(R.id.arvd_recycler_view);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //参数：context,列数或行数(对应第三个参数是纵向或横向)，纵向或横向滑动，是否颠倒显示数据
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(adapter);
//        recyclerView.addItemDecoration(new VerticalLinearDivider());//设置装饰

    }

    RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

//            c.drawCircle(50,50,20,new Paint());
        }
    };

    /**
     * 绘制垂直分割线
     */
    private class VerticalLinearDivider extends RecyclerView.ItemDecoration{
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);
            int parentWidth = parent.getWidth();
            int yHeight = 0;
            for(int i =0;i < parent.getChildCount();i++) {
                int nowItemHeight = parent.getChildAt(i).getHeight();
                yHeight += nowItemHeight;
                c.drawLine(0,yHeight,parentWidth,yHeight,new Paint());//绘制分割线 这么搞不行：分割线未能跟随滑动
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //设置itemView的padding
            outRect.set(0,0,0,0);
        }
    }

    CustomAdapter.RecyclerViewOnClickListener recyclerViewOnClickListener = new CustomAdapter.RecyclerViewOnClickListener(){

        @Override
        public void onItemClick(int position) {
            ToastUtil.showToast("item click "+ position,0);
        }

        @Override
        public void onTextOnclick(int position) {
            ToastUtil.showToast("text click "+ position,0);
        }
    };

    private void initResources() {
        adapter.setData(data);
        adapter.notifyDataSetChanged();
    }
}
