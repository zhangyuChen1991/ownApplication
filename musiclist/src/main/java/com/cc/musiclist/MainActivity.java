package com.cc.musiclist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.manager.TimeCountManager;
import com.cc.musiclist.util.FileDirectoryUtil;
import com.cc.musiclist.util.FileUtil;
import com.cc.musiclist.util.SpUtil;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private List<File> files;
    private ListView listView;
    private TextView songNameTv, playTime;
    private Button modelSet, pauseOrPlay, stop, next;
    private LinearLayout menu;
    private SeekBar seekBar;
    private MAdapter adapter;
    private MediaPlayManager mediaPlayManager;
    private MHandler handler;
    private AlertDialog progressDialog;
    private static final int initFileStart = 0x25, initFileOver = 0x27, updateProgress = 0x31;
    private TranslateUtil tu;
    private String filePathCache;
    private PopupWindow popWindow;

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
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(onItemClickListener);
        modelSet = (Button) findViewById(R.id.model_change);
        pauseOrPlay = (Button) findViewById(R.id.pause_play);
        stop = (Button) findViewById(R.id.stop);
        next = (Button) findViewById(R.id.next);
        songNameTv = (TextView) findViewById(R.id.song_name);
        menu = (LinearLayout) findViewById(R.id.menu);
        playTime = (TextView) findViewById(R.id.play_time);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);

        stop.setOnClickListener(this);
        modelSet.setOnClickListener(this);
        pauseOrPlay.setOnClickListener(this);
        next.setOnClickListener(this);
        menu.setOnClickListener(this);

        initProgressDialog();
        initPopWindow();
    }

    private void initPopWindow() {
        popWindow = new PopupWindow();
        View v = View.inflate(this, R.layout.popmenu, null);
        popWindow.setContentView(v);
        popWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        v.findViewById(R.id.scan_song_file).setOnClickListener(this);
        v.findViewById(R.id.cancel_scan_song_file).setOnClickListener(this);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("提示");
        progressDialog.setMessage("正在扫描歌曲..");

    }

    private void initResources() {
        handler = new MHandler(this);
        adapter = new MAdapter();
        tu = new TranslateUtil();
        initFiles();
    }

    private void initMediaPlayerManager() {
        mediaPlayManager = new MediaPlayManager();
        mediaPlayManager.setPlayCallBack(playCallBack);
        if (null != files) {
            mediaPlayManager.setPlayLists(files);
            //设置上次退出时的状态
            mediaPlayManager.setNowFilePosition(SpUtil.getInt(Constants.lastPlayPosition, 0));
            mediaPlayManager.setPlayModel(SpUtil.getInt(Constants.lastPlayModel, MediaPlayManager.SEQUENTIA_LOOP_MODEL));
            mediaPlayManager.prepare();
        }
    }

    /**
     * 初始化歌曲文件目录
     */
    private void initFiles() {
        filePathCache = SpUtil.getString(Constants.filesPathCache, "");
        if (TextUtils.isEmpty(filePathCache))
            scanFile();
        else {
            files = tu.StringsToFileList(tu.stringToStringArray(filePathCache));
            handler.sendEmptyMessage(initFileOver);
        }
    }

    private void scanFile() {
        new Thread(scanFileRunnable).start();
    }

    /**
     * 扫描歌曲文件执行体
     */
    private Runnable scanFileRunnable = new Runnable() {
        @Override
        public void run() {

            try {
                handler.sendEmptyMessage(initFileStart);
                Thread.sleep(500);
                String[] fileTypes = {"mp3", "wav", "wma"};
                files = FileUtil.getFileList(FileDirectoryUtil.getSdCardDirectory(), fileTypes);

                SpUtil.put(Constants.filesPathCache, tu.stringArrayToString(tu.fileListToStrings(files)));
                handler.sendEmptyMessage(initFileOver);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }

            //debug
//            for (int i = 0; i < files.size(); i++) {
//                Log.d(TAG, files.get(i).getAbsolutePath());
//            }
            //debug
        }
    };

    private void initViewState() {
        listView.setAdapter(adapter);

    }

    private void updateViewState() {
        songNameTv.setText(mediaPlayManager.getNowPlayFile().getName());
        songNameTv.requestFocus();
        setModelText();
    }

    private void scrollToNowPlay() {
        listView.setSelection(mediaPlayManager.getNowFilePosition());
    }

    private void setModelText() {
        int playModel = mediaPlayManager.getPlayModel();
        if (playModel == MediaPlayManager.SEQUENTIA_LOOP_MODEL) {
            modelSet.setText("列表\n循环");
        } else if (playModel == MediaPlayManager.RANDOM_MODEL)
            modelSet.setText("随机\n播放");
        else if (playModel == MediaPlayManager.SINGLE_MODEL)
            modelSet.setText("单曲\n循环");

        int playState = mediaPlayManager.getPlayingState();
        if (playState == Constants.STATE_PLAYING) {
            pauseOrPlay.setText("暂停");
        } else {
            pauseOrPlay.setText("播放");
        }
        Log.d(TAG, "initViewState  playState = " + playState + "，playModel = " + playModel);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.listview:
                    if (position < files.size()) {
                        mediaPlayManager.setNowFilePosition(position);
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
                break;
            case R.id.pause_play:
                int state = mediaPlayManager.getPlayingState();
                if (state == Constants.STATE_PLAY_STOP) {     //暂停或停止状态
                    mediaPlayManager.start();
                } else {      //播放状态
                    mediaPlayManager.pause();
                }
                break;
            case R.id.stop:     //停止
                mediaPlayManager.stop();
                break;
            case R.id.next:     //下一曲
                mediaPlayManager.playNext();
                break;
            case R.id.menu:     //菜单
                popWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                popWindow.setOutsideTouchable(false);
                break;
            case R.id.scan_song_file:       //扫描文件
                popWindow.dismiss();
                scanFile();
                break;
            case R.id.cancel_scan_song_file:        //取消扫描文件
                popWindow.dismiss();
                break;
        }
        updateViewState();
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
            RelativeLayout container = (RelativeLayout) v.findViewById(R.id.container);

            if (position < files.size()) {
                String sName = files.get(position).getName();
                songName.setText(sName);
            }

            if (position == mediaPlayManager.getNowFilePosition()) {
                container.setBackgroundColor(getResources().getColor(R.color.mediumturquoise));
            } else
                container.setBackgroundColor(getResources().getColor(R.color.whitesmoke));
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
        SpUtil.put(Constants.lastPlayPosition, mediaPlayManager.getNowFilePosition());
        SpUtil.put(Constants.lastPlayModel, mediaPlayManager.getPlayModel());
        files.clear();
        mediaPlayManager.stop();
        mediaPlayManager.destory();
        super.onDestroy();
    }

    private MediaPlayManager.PlayCallBack playCallBack = new MediaPlayManager.PlayCallBack() {
        @Override
        public void playNext(String newSongName) {
            scrollToNowPlay();
        }

        @Override
        public void startNew(String newSongName, final int duration) {
            updateViewState();
            adapter.notifyDataSetChanged();
            seekBar.setMax(duration / 1000);
            timeCountManager.restartCount();
        }

        @Override
        public void stop() {
            timeCountManager.finishCount();
            handler.sendEmptyMessage(updateProgress);
        }
    };

    private TimeCountManager timeCountManager = new TimeCountManager() {
        @Override
        public void onCount() {
            Log.d(TAG, "sendEmptyMessage(updateProgress)");
            handler.sendEmptyMessage(updateProgress);
        }
    };

    private void setNowProgress() {

        long nowProgress = mediaPlayManager.getNowPlayingProgress() / 1000;

        playTime.setText(StringUtil.msToTime(nowProgress));
        seekBar.setProgress((int) nowProgress);

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
                    mainActivity.updateViewState();
                    mainActivity.scrollToNowPlay();
                    mainActivity.dismissLoading();
                    break;
                case updateProgress:
                    mainActivity.setNowProgress();
                    break;
            }
        }
    }
}
