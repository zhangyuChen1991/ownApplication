package com.cc.musiclist;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.cc.musiclist.util.FileDirectoryUtil;
import com.cc.musiclist.util.FileUtil;
import com.cc.musiclist.util.ToastUtil;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private ArrayList<File> files;
    private ListView listView;
    private MAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initResources();
        initViewState();
    }


    private void initView() {
        listView = (ListView) findViewById(R.id.listview);

    }

    private void initResources() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast("没有权限",0);
            // 请求读取SD卡权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            ToastUtil.showToast("有权限",0);
        }

        files = FileUtil.getFileList(FileDirectoryUtil.getRootDirectory(), "mp3");
        //debug
        for (int i = 0; i < files.size(); i++) {
            Log.d(TAG, files.get(i).getAbsolutePath());
        }
        //debug

        adapter = new MAdapter();
    }

    private void initViewState() {
        listView.setAdapter(adapter);
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

            return v;
        }
    }
}
