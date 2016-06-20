package com.cc.playlist;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cc.playlist.bean.BlockBean;
import com.cc.playlist.view.MGridView;

public class MainActivity extends Activity {

    private static final String TAG = "WhiteBlackActivity";
    private MGridView gridView;
    private MAdapter adapter;
    private int[][] blockData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initResources();
        initViewState();
    }


    private void initView() {
        gridView = (MGridView) findViewById(R.id.grid_view);

    }

    private void initResources() {
        blockData = new BlockBean().blockData;
        adapter = new MAdapter();
    }

    private void initViewState() {
        gridView.setAdapter(adapter);
    }


    private class MAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 300;
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
            View v = View.inflate(MainActivity.this, R.layout.dapter_view, null);
            View view = (View) v.findViewById(R.id.view);

            int lineNum = position / 3;//行数
            int columnNum = position % 3;//列数


            if (blockData[lineNum][columnNum]  == 1) {
                view.setBackgroundColor(Color.WHITE);
            } else {
                view.setBackgroundColor(Color.BLACK);
            }

            return v;
        }
    }
}
