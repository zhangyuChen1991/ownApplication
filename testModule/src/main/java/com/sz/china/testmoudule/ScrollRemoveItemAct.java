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
import com.sz.china.testmoudule.util.ToastUtil;
import com.sz.china.testmoudule.view.ScrollRemoveItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/10.
 */
public class ScrollRemoveItemAct extends Activity {
    @ViewInject(R.id.listview3)
    private ListView listView;

    private MyAdapter adapter;
    private List<Integer> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_remove_item);
        ViewInjectUtil.injectView(this);

        setData();
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    private void setData() {
        for (int i = 1; i <= 20; i++)
            data.add(i);
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.size();
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
            if (convertView == null) {
                v = View.inflate(ScrollRemoveItemAct.this, R.layout.adapter_scroll_remove_item, null);
            } else {
                v = convertView;
            }
            TextView tv = (TextView) v.findViewById(R.id.asri_tv);
            tv.setText(data.get(position) + "");
            ScrollRemoveItemView removeItem = (ScrollRemoveItemView) v.findViewById(R.id.scroll_remove_item);
            addRemoveListener(removeItem, position);
            removeItem.resume();//回滚回初始位置

            return v;
        }
    }

    private void addRemoveListener(ScrollRemoveItemView removeItem, final int position) {
        removeItem.setRemoveListener(new ScrollRemoveItemView.RemoveListener() {
            @Override
            public void beRemoved() {
                ToastUtil.showToast("第" + data.get(position) + "条删除", 0);
                data.remove(position);
                adapter.notifyDataSetChanged();

            }
        });
    }
}
