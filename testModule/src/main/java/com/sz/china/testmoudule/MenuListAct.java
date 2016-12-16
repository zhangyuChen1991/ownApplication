package com.sz.china.testmoudule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sz.china.testmoudule.view.PageMenuHorizontalScrollView;

/**
 * Created by zhangyu on 2016/8/7 14:01.
 */
public class MenuListAct extends Activity {
    private ListView listView;
    private MyAdapter adapter;
    private String[] menu = {"图片沉浸状态栏及activity翻页效果初始实现(仅作参考，未抽离成工具类)",
            "测试注解 /页面进、出旋转动画 /测试自定义圆形imageview",
            "下拉刷新其一：PullToRefreshScrollView",
            "旋转activity翻页工具类:ActivityRotateAnimationUtil",
            "测试二维码扫描框架",
            "测试九宫格解锁view",
            "RecyclerViewDemo",
            "SwipeRefreshLayout",
            "滑动删除item listview",
            "viewpager滑动指示器",
            "magic line view",
            "GreenDao测试",
            "retrofit、okhttp",
            "wifi info (root可用)",
            "ScanView Demo (使用百分比控件布局)",
            "高斯模糊",
            "Glide",
            "用一组图片构成动画(逐帧动画)",
            "NewsLayout sample",
            "CoordinatorLayoutSample"};
    private Class[] activities = {ImmersionAct.class, TestAct1.class, PullToRefreshScrollViewAct.class, TestRotateAnimationAct.class
            , TestScanAct.class, TestUnlockViewAct.class, RecyclerViewDemoMenuAct.class,
            SwipeRefreshLayoutAct.class, ScrollRemoveItemAct.class, TestFt.class,
            MagicLineAct.class, GreenDaoAct.class, RetrofitAndOkHttpAct.class, WifiInfoAct.class,
            ScanViewDemoAct.class, BlurDemo.class, GlideAct.class, FrameAnimationAct.class,
            NewsLayoutAct.class, CoordinatorLayoutSample.class};

    private PageMenuHorizontalScrollView pageMenuHorizontalScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        initResources();
        initView();
    }

    private void initResources() {
        adapter = new MyAdapter();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        pageMenuHorizontalScrollView = (PageMenuHorizontalScrollView) findViewById(R.id.page_menu_scrollview);

    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.listview) {
                Intent intent = new Intent(MenuListAct.this, activities[position]);
                startActivity(intent);
            }
        }
    };

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return menu.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            TextView tv;
            if (convertView == null) {
                v = View.inflate(MenuListAct.this, R.layout.adapter_view_menu, null);
            } else {
                v = convertView;
            }

            tv = (TextView) v.findViewById(R.id.menu_item_tv);

            if (position < menu.length)
                tv.setText(menu[position]);
            return v;
        }
    }
}
