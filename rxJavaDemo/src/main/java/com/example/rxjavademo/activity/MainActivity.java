package com.example.rxjavademo.activity;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;

/**
 * 主页面
 * 附RxJava文档地址: https://mcxiaoke.gitbooks.io/rxdocs/content/
 */
public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @ViewInject(R.id.main_listview)
    private ListView listView;

    private MyAdapter adapter;
    private String[] menu = {
            "简单demo",
            "发布者(Observable)不同的创建方式",
            "转换",
            "过滤",
            "结合",
            "结合retrofit"};

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ViewInjectUtil.injectView(this);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected void initResources() {

    }

    @Override
    protected void initViewState() {

    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.main_listview) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, SimpleDemoAct.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(MainActivity.this, ObservableCreateAct.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2 = new Intent(MainActivity.this, TranslateAct.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3 = new Intent(MainActivity.this, FilterAct.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4 = new Intent(MainActivity.this, MerageAct.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5 = new Intent(MainActivity.this, RetrofitAndRxJavaAct.class);
                        startActivity(intent5);
                        break;
//                    case 6:
//                        Intent intent6 = new Intent(MainActivity.this, RecycleViewDemoAct.class);
//                        startActivity(intent6);
//                        break;
//                    case 7:
//                        Intent intent7 = new Intent(MainActivity.this, SwipeRefreshLayoutAct.class);
//                        startActivity(intent7);
//                        break;
//                    case 8:
//                        Intent intent8 = new Intent(MainActivity.this, ScrollRemoveItemAct.class);
//                        startActivity(intent8);
//                        break;
//                    case 9:
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
                v = View.inflate(MainActivity.this, R.layout.adapter_view_menu, null);
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
