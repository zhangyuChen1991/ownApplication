package com.cc.musiclist;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cc.musiclist.adapter.TabsAdapter;
import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.fragment.AudioFileFragment;
import com.cc.musiclist.fragment.PlayListFragment;
import com.cc.musiclist.manager.MapManager;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.manager.SystemBarTintManager;
import com.cc.musiclist.manager.TimeCountManager;
import com.cc.musiclist.util.DisplayUtils;
import com.cc.musiclist.util.LogUtil;
import com.cc.musiclist.util.SpUtil;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;
import com.cc.musiclist.view.TitleView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private TextView songNameTv, playTime;
    private Button modelSet, pauseOrPlay, stop, next;
    private LinearLayout menu;
    private TitleView titleView;
    private ViewPager viewPager;
    private SeekBar seekBar;
    private MediaPlayManager mediaPlayManager;
    private MHandler handler;
    private Dialog progressDiaLogUtil;
    private PopupWindow popWindow;
    private TabsAdapter tabsAdapter;
    private List<Fragment> fragmentList;
    private AudioFileFragment audioFileFragment;
    private PlayListFragment playListFragment;
    private int currentPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setImmersionStatus();
        setTintColor();
        setContentView(R.layout.activity_main);

        initView();
        initResources();
        initViewState();
    }

    public MHandler getHandler() {
        return handler;
    }

    private void initView() {
        titleView = (TitleView) findViewById(R.id.title_view);
        titleView.setCallBack(titleViewCallBack);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(onPageChangeListener);

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


        initProgressDiaLogUtil();
        initPopWindow();
    }

    /**
     * 设置屏幕顶部的时间、电量等图标显示在界面上
     */
    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏 即下方的虚拟按键
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        }
    }

    private void setTintColor() {
        // 创建状态栏的管理实例
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // 激活状态栏设置
        tintManager.setStatusBarTintEnabled(true);
        // 设置一个颜色给系统栏
        tintManager.setTintColor(Color.parseColor("#1E90FF"));
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

    private void initProgressDiaLogUtil() {
        progressDiaLogUtil = new Dialog(this, R.style.base_dialog);
        progressDiaLogUtil.setContentView(R.layout.progress_dailog);
        WindowManager.LayoutParams params = progressDiaLogUtil.getWindow().getAttributes();
        params.height = (int) (DisplayUtils.getHeight() * 0.2f);
        params.width = (int) (DisplayUtils.getWidth() * 0.9f);
        progressDiaLogUtil.getWindow().setAttributes(params);
        progressDiaLogUtil.setCanceledOnTouchOutside(false);
    }

    private void initResources() {
        handler = new MHandler(this);

        audioFileFragment = new AudioFileFragment();
        playListFragment = new PlayListFragment();

        fragmentList = new ArrayList<>();

        fragmentList.add(playListFragment);
        fragmentList.add(audioFileFragment);

        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragmentList);
    }

    private void initMediaPlayerManager() {
        String prePath = SpUtil.getString(Constants.lastPlayFilePath, "");
        LogUtil.i(TAG, "prePath = " + prePath);
        File preFile = new File(prePath);
        mediaPlayManager = MediaPlayManager.getInstance();
        mediaPlayManager.setPlayCallBack(playCallBack);
        if (null != audioFileFragment.files) {
            setPlayList();
            //设置上次退出时的状态
            mediaPlayManager.setNowPlayFile(preFile);
            mediaPlayManager.setPlayModel(SpUtil.getInt(Constants.lastPlayModel, MediaPlayManager.SEQUENTIA_LOOP_MODEL));
            mediaPlayManager.prepare();
        }
    }

    /**
     * 设置播放列表
     * 有缓存则恢复缓存数据，没有缓存设置当前文件列表作为播放列表
     */
    private void setPlayList() {
        String cache = SpUtil.getString(Constants.playListCache, "");
        if (mediaPlayManager.getPlayLists().size() == 0) {
            if (TextUtils.isEmpty(cache))
                mediaPlayManager.setPlayLists(audioFileFragment.files);
            else {
                TranslateUtil tu = new TranslateUtil();
                List<File> lsit = tu.StringsToFileList(tu.stringToStringArray(cache));
                mediaPlayManager.setPlayLists(lsit);
            }
        }
    }

    private void initMapManager() {
        MapManager.INSTANCE.init(audioFileFragment.files);
    }

    private void initViewState() {
        viewPager.setAdapter(tabsAdapter);
        viewPager.setCurrentItem(1);

    }

    private void updateViewState() {
        checkListState();
        if (null != mediaPlayManager.getNowPlayFile()) {
            songNameTv.setText(StringUtil.subPostfix(mediaPlayManager.getNowPlayFile().getName()));
            songNameTv.requestFocus();
        }
        setModelText();
    }

    private void checkListState() {
        songNameTv.setText("没有歌曲在播放");

        if (null == audioFileFragment.files || audioFileFragment.files.size() == 0) {
            next.setTextColor(getResources().getColor(R.color.silver));
            pauseOrPlay.setTextColor(getResources().getColor(R.color.silver));
            stop.setTextColor(getResources().getColor(R.color.silver));
            next.setClickable(false);
            pauseOrPlay.setClickable(false);
            stop.setClickable(false);
            ToastUtil.showToast("没有找到歌曲.", 0);
        } else {
            next.setTextColor(getResources().getColor(R.color.whitesmoke));
            pauseOrPlay.setTextColor(getResources().getColor(R.color.whitesmoke));
            stop.setTextColor(getResources().getColor(R.color.whitesmoke));
            next.setClickable(true);
            pauseOrPlay.setClickable(true);
            stop.setClickable(true);
        }
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
        LogUtil.d(TAG, "initViewState  playState = " + playState + "，playModel = " + playModel);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        float currX = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            LogUtil.d(TAG, "onPageScrolled  position = " + position + "  ,positionOffset = " + positionOffset + "  ,positionOffsetPixels = " + positionOffsetPixels + "  ,currX = " + currX);

        }

        @Override
        public void onPageSelected(int position) {
            LogUtil.i(TAG, "onPageSelected  currX = " + currX);
            if (currentPosition != position)
                titleView.translateTo(currentPosition, position);

            currentPosition = position;
            if (position == 0)
                playListFragment.adapterNotifyDataChange();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private TitleView.TitleViewCallBack titleViewCallBack = new TitleView.TitleViewCallBack() {
        @Override
        public void onSelected(int position) {
            viewPager.setCurrentItem(position);
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
                if (state == Constants.STATE_PLAY_STOP || state == Constants.STATE_PLAY_PAUSE) {     //暂停或停止状态
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
                audioFileFragment.scanFile();
                break;
            case R.id.cancel_scan_song_file:        //取消扫描文件
                popWindow.dismiss();
                break;
        }
        updateViewState();
    }

    private void showLoading() {
        progressDiaLogUtil.show();
    }

    private void dismissLoading() {
        progressDiaLogUtil.dismiss();
    }

    private void destoryResources() {
        audioFileFragment.files.clear();
        mediaPlayManager.stop();
        mediaPlayManager.destory();
    }

    private void saveCache() {
        SpUtil.put(Constants.lastPlayFilePath, mediaPlayManager.getNowPlayFile().getAbsolutePath());
        SpUtil.put(Constants.lastPlayModel, mediaPlayManager.getPlayModel());
        mediaPlayManager.savePlayList();
    }

    private MediaPlayManager.PlayCallBack playCallBack = new MediaPlayManager.PlayCallBack() {
        @Override
        public void playNext(String newSongName) {
            audioFileFragment.scrollToNowPlay();
            playListFragment.scrollToNowPlay();
        }

        @Override
        public void startNew(String newSongName, final int duration) {
            updateViewState();
            audioFileFragment.adapterNotifyDataChange();
            playListFragment.adapterNotifyDataChange();
            seekBar.setMax(duration / 1000);
            timeCountManager.restartCount();
        }

        @Override
        public void stop() {
            timeCountManager.finishCount();
            handler.sendEmptyMessage(Constants.updateProgress);
        }
    };

    private TimeCountManager timeCountManager = new TimeCountManager() {
        @Override
        public void onCount() {
            LogUtil.d(TAG, "sendEmptyMessage(updateProgress)");
            handler.sendEmptyMessage(Constants.updateProgress);
        }
    };

    private void setNowProgress() {

        long nowProgress = mediaPlayManager.getNowPlayingProgress() / 1000;

        playTime.setText(StringUtil.msToTime(nowProgress));
        seekBar.setProgress((int) nowProgress);

    }

    public static class MHandler extends Handler {
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
                case Constants.initFileStart:
                    mainActivity.showLoading();
                    break;
                case Constants.initFileOver:
                    mainActivity.initMapManager();
                    mainActivity.initMediaPlayerManager();
                    mainActivity.updateViewState();
                    mainActivity.audioFileFragment.scrollToNowPlay();
                    mainActivity.playListFragment.adapterNotifyDataChange();
                    mainActivity.playListFragment.scrollToNowPlay();
                    mainActivity.dismissLoading();
                    break;
                case Constants.updateProgress:
                    mainActivity.setNowProgress();
                    break;
                case Constants.updateAnimatior:
                    break;
            }
        }
    }

    long firstClick = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        // 实现两秒内双击退出功能
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            long secondClick = System.currentTimeMillis();
            if (secondClick - firstClick > 2000) {
                ToastUtil.showToast("再按一次退出程序", 0);
                firstClick = secondClick;
            } else {
                saveCache();
                destoryResources();
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
