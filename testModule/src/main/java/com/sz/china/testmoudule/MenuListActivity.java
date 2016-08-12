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

/**
 * Created by zhangyu on 2016/8/7 14:01.
 */
public class MenuListActivity extends Activity {
    private ListView listView;
    private MyAdapter adapter;
    private String[] menu = {"图片沉浸状态栏及activity翻页效果初始实现(仅作参考，未抽离成工具类)",
            "测试注解 /页面进、出旋转动画 /测试自定义圆形imageview",
            "下拉刷新其一：PullToRefreshScrollView",
            "旋转activity翻页工具类:ActivityRotateAnimationUtil",
            "测试二维码扫描框架 以及上下拉刷新侧滑菜单listview：PullToRefreshSwipeMenuListView",
            "测试九宫格解锁view",
            "RecycleViewDemo",
            "SwipeRefreshLayout",
            "滑动删除item listview"};

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
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.listview) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MenuListActivity.this, ImmersionActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MenuListActivity.this, TestActivity1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MenuListActivity.this, PullToRefreshScrollViewActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MenuListActivity.this, TestRotateAnimationActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MenuListActivity.this, TestScanAcy.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MenuListActivity.this, TestUnlockViewActivity.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MenuListActivity.this, RecycleViewDemoActivity.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MenuListActivity.this, SwipeRefreshLayoutActivity.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(MenuListActivity.this, ScrollRemoveItemActivity.class);
                        startActivity(intent8);
                        break;
                }
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
                v = View.inflate(MenuListActivity.this, R.layout.adapter_view_menu, null);
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
