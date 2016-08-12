package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ScrollRemoveItemActivity extends Activity {
    @ViewInject(R.id.listview3)
    private ListView listView;

    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_remove_item);
        ViewInjectUtil.injectView(this);

        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 20;
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
                v = View.inflate(ScrollRemoveItemActivity.this, R.layout.adapter_scroll_remove_item, null);
            } else {
                v = convertView;
            }
            return v;
        }
    }
}
