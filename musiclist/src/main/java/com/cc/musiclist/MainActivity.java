package com.cc.musiclist;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.util.FileDirectoryUtil;
import com.cc.musiclist.util.FileUtil;
import com.cc.musiclist.util.SpUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private ArrayList<File> files;
    private ListView listView;
    private Button modelSet, pauseOrPlay, stop;
    private MAdapter adapter;
    private MediaPlayManager mediaPlayManager;
    private MHandler handler;
    private ProgressDialog progressDialog;
    private static final int initFileStart = 0x25, initFileOver = 0x27;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initResources();
    }


    private void initView() {
        listView = (ListView) findViewById(R.id.listview);
        listView.setDividerHeight(0);
        modelSet = (Button) findViewById(R.id.model_change);
        pauseOrPlay = (Button) findViewById(R.id.pause_play);
        stop = (Button) findViewById(R.id.stop);

        stop.setOnClickListener(this);
        modelSet.setOnClickListener(this);
        pauseOrPlay.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在扫描歌曲..");
    }

    private void initResources() {
        handler = new MHandler(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ToastUtil.showToast("没有权限", 0);
            // 请求读取SD卡权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        adapter = new MAdapter();
        scanFile();
    }

    private void initMediaPlayerManager() {
        mediaPlayManager = new MediaPlayManager();
        if (null != files) {
            mediaPlayManager.setPlayLists(files);
        }
    }

    private void scanFile() {
        new Thread(scanFileRunnable).start();
    }

    private Runnable scanFileRunnable = new Runnable() {
        @Override
        public void run() {
            TranslateUtil tu = new TranslateUtil();
            handler.sendEmptyMessage(initFileStart);
            files = FileUtil.getFileList(FileDirectoryUtil.getRootDirectory(), "mp3");
//            SpUtil.put(Constants.filesPathCache, );
            handler.sendEmptyMessage(initFileOver);
            //debug
            for (int i = 0; i < files.size(); i++) {
                Log.d(TAG, files.get(i).getAbsolutePath());
            }
            //debug
            tu = null;
        }
    };

    private void initViewState() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);

        setModelText();
        pauseOrPlay.setTag(Constants.STATE_PAUSE);
    }

    private void setModelText() {
        int playModel = mediaPlayManager.getPlayModel();
        if (playModel == MediaPlayManager.SEQUENTIAL_MODEL) {
            modelSet.setText("顺序播放");
        } else if (playModel == MediaPlayManager.RANDOM_MODEL)
            modelSet.setText("随机播放");
        else if (playModel == MediaPlayManager.SINGLE_MODEL)
            modelSet.setText("单曲循环");
    }


    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.listview:
                    if (position < files.size()) {
                        mediaPlayManager.setNowPlayFile(files.get(position));
                        mediaPlayManager.prepare();
                        mediaPlayManager.start();
                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.model_change:     //模式设置
                mediaPlayManager.nextModel();
                initViewState();
                break;
            case R.id.pause_play:
                int state = (int) pauseOrPlay.getTag();
                if (state == Constants.STATE_PAUSE) {     //暂停状态
                    mediaPlayManager.start();
                    pauseOrPlay.setTag(Constants.STATE_PLAYING);
                    pauseOrPlay.setText("暂停");
                } else {      //播放状态
                    mediaPlayManager.pause();
                    pauseOrPlay.setTag(Constants.STATE_PAUSE);
                    pauseOrPlay.setText("播放");
                }
            case R.id.stop:     //停止
                mediaPlayManager.stop();
                break;
        }
    }

    private class MAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return files == null ? 0 : files.size();
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
            TextView songName = (TextView) v.findViewById(R.id.song_name);

            if (position < files.size()) {
                String sName = files.get(position).getName();
                songName.setText(sName);
            }
            return v;
        }
    }

    private void showLoading() {
        progressDialog.show();
    }

    private void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        files.clear();
        mediaPlayManager.destory();
        super.onDestroy();
    }

    private static class MHandler extends Handler {
        WeakReference<MainActivity> mainActivityReference;
        MainActivity mainActivity;

        public MHandler(MainActivity mainActivityRefer) {
            mainActivityReference = new WeakReference<MainActivity>(mainActivityRefer);
            mainActivity = mainActivityReference.get();
        }


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case initFileStart:
                    mainActivity.showLoading();
                    break;
                case initFileOver:
                    mainActivity.initMediaPlayerManager();
                    mainActivity.initViewState();
                    mainActivity.dismissLoading();
                    break;
            }
        }
    }
}
