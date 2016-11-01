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
            "测试二维码扫描框架 以及上下拉刷新侧滑菜单listview：PullToRefreshSwipeMenuListView",
            "测试九宫格解锁view",
            "RecyclerViewDemo",
            "SwipeRefreshLayout",
            "滑动删除item listview",
            "测试，横向滑动页面菜单",
            "viewpager滑动指示器",
            "magic line view",
            "GreenDao测试",
            "retrofit、okhttp",
            "wifi info",
            "ScanView Demo"};

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
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MenuListAct.this, ImmersionAct.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MenuListAct.this, TestAct1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MenuListAct.this, PullToRefreshScrollViewAct.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MenuListAct.this, TestRotateAnimationAct.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MenuListAct.this, TestScanAct.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MenuListAct.this, TestUnlockViewAct.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6 = new Intent(MenuListAct.this, RecyclerViewDemoMenuAct.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7 = new Intent(MenuListAct.this, SwipeRefreshLayoutAct.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8 = new Intent(MenuListAct.this, ScrollRemoveItemAct.class);
                        startActivity(intent8);
                        break;
                    case 9:

                        pageMenuHorizontalScrollView.initView();
                        pageMenuHorizontalScrollView.scrollToPst();
                        pageMenuHorizontalScrollView.setVisibility(View.VISIBLE);

                        listView.setVisibility(View.GONE);
                        pageMenuHorizontalScrollView.startZoomInAnim();

                        break;
                    case 10:
                        Intent intent10 = new Intent(MenuListAct.this, TestFt.class);
                        startActivity(intent10);

                        break;
                    case 11:
                        Intent intent11 = new Intent(MenuListAct.this, MagicLineAct.class);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12 = new Intent(MenuListAct.this, GreenDaoAct.class);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13 = new Intent(MenuListAct.this, RetrofitAndOkHttpAct.class);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(MenuListAct.this, WifiInfoAct.class);
                        startActivity(intent14);
                        break;
                    case 15:
                        Intent intent15 = new Intent(MenuListAct.this, ScanViewDemoAct.class);
                        startActivity(intent15);
                        break;
//                    case 16:
//                        Intent intent16 = new Intent(MenuListAct.this, RecyclerViewDemoAct1.class);
//                        startActivity(intent16);
//                        break;

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
