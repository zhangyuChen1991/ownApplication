package com.cc.musiclist;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.manager.MapManager;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.manager.SystemBarTintManager;
import com.cc.musiclist.manager.TimeCountManager;
import com.cc.musiclist.util.DisplayUtils;
import com.cc.musiclist.util.FileDirectoryUtil;
import com.cc.musiclist.util.FileUtil;
import com.cc.musiclist.util.SpUtil;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private List<File> files;
    private ListView listView;
    private TextView songNameTv, playTime;
    private Button modelSet, pauseOrPlay, stop, next;
    private LinearLayout menu;
    private ViewPager viewPager;
    private SeekBar seekBar;
    private MAdapter adapter;
    private MediaPlayManager mediaPlayManager;
    private MHandler handler;
    private Dialog progressDialog;
    private static final int initFileStart = 0x25, initFileOver = 0x27, updateProgress = 0x31, updateAnimatior = 0x41;
    private TranslateUtil tu;
    private String filePathCache;
    private PopupWindow popWindow;
    private HashMap<Integer, View> items;

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


    private void initView() {
//        viewPager = (ViewPager) findViewById(R.id.viewpager);
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

        addAnimationImg = new ImageView(this);

        initProgressDialog();
        initPopWindow();
    }

    /**
     * 设置屏幕顶部的时间、电量等图标显示在界面上
     */
    private void setImmersionStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

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

    private void initProgressDialog() {
        progressDialog = new Dialog(this, R.style.base_dialog);
        progressDialog.setContentView(R.layout.progress_dailog);
        WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
        params.height = (int) (DisplayUtils.getHeight() * 0.2f);
        params.width = (int) (DisplayUtils.getWidth() * 0.9f);
        progressDialog.getWindow().setAttributes(params);
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void initResources() {
        handler = new MHandler(this);
        adapter = new MAdapter();
        tu = new TranslateUtil();
        items = new HashMap<>();
        initFiles();
    }

    private void initMediaPlayerManager() {
        String prePath = SpUtil.getString(Constants.lastPlayFilePath, "");
        Log.i(TAG, "prePath = " + prePath);
        File preFile = new File(prePath);
        mediaPlayManager = new MediaPlayManager();
        mediaPlayManager.setPlayCallBack(playCallBack);
        if (null != files) {
            mediaPlayManager.setPlayLists(files);
            //设置上次退出时的状态
            mediaPlayManager.setNowPlayFile(preFile);
            mediaPlayManager.setPlayModel(SpUtil.getInt(Constants.lastPlayModel, MediaPlayManager.SEQUENTIA_LOOP_MODEL));
            mediaPlayManager.prepare();
        }
    }

    private void initMapManager() {
        MapManager.INSTANCE.init(files);
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
        checkListState();
        if (null != mediaPlayManager.getNowPlayFile()) {
            songNameTv.setText(StringUtil.subPostfix(mediaPlayManager.getNowPlayFile().getName()));
            songNameTv.requestFocus();
        }
        setModelText();

        Fragment.instantiate(this,Fragment.class.getName());
    }

    private void checkListState() {
        songNameTv.setText("没有歌曲在播放");

        if (null == files || files.size() == 0) {
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

    private void scrollToNowPlay() {
        listView.setSelection(MapManager.INSTANCE.getFilePosition(mediaPlayManager.getNowPlayFile()));
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

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            ViewHolder holder = null;
            if (convertView == null) {
                v = View.inflate(MainActivity.this, R.layout.dapter_view, null);
                holder = new ViewHolder();
                holder.songName = (TextView) v.findViewById(R.id.song_name);
                holder.itemContainer = (RelativeLayout) v.findViewById(R.id.container);
                holder.itemMenuIvContainer = (RelativeLayout) v.findViewById(R.id.item_menu_iv_container);
                holder.itemMenu = (LinearLayout) v.findViewById(R.id.menu_container);
                holder.addToPlayListIvContainer = (RelativeLayout) v.findViewById(R.id.item_add_iv_container);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();
            }


            if (position < files.size()) {
                String sName = files.get(position).getName();
                holder.songName.setText(StringUtil.subPostfix(sName));
            }

            if (position == MapManager.INSTANCE.getFilePosition(mediaPlayManager.getNowPlayFile())) {
                holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.mediumturquoise));
            } else
                holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.whitesmoke));

            if (position == menuPosition) {
                ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f, 0f, 1f, 0.5f, 0f);
                scaleAnimation.setDuration(200);
                holder.itemMenu.startAnimation(scaleAnimation);
                holder.itemMenu.setVisibility(View.VISIBLE);
            } else {
                holder.itemMenu.setVisibility(View.GONE);
            }

            onItemViewClick(position, holder.itemMenuIvContainer);
            onItemViewClick(position, holder.addToPlayListIvContainer);
            items.put(position, v);
            return v;
        }
    }

    private ImageView addAnimationImg;

    private void startAnimation(int position, ViewHolder holder) {

        addAnimationImg.setImageResource(R.drawable.add_blue);
        addAnimationImg.setBackgroundColor(getResources().getColor(R.color.transparent));

        final WindowManager.LayoutParams parms = new WindowManager.LayoutParams();
        // 设置宽高
        parms.height = WindowManager.LayoutParams.WRAP_CONTENT;
        parms.width = WindowManager.LayoutParams.WRAP_CONTENT;
        // 设置无焦点，不可触，保持屏幕开启
        parms.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        //| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	//设置不可触
        parms.format = PixelFormat.TRANSLUCENT;
        parms.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;    //加权限，.SYSTEM_ALERT_WINDOW

        int[] loc = new int[2];
        holder.addToPlayListIvContainer.getLocationOnScreen(loc);
        final int startX = parms.x = loc[0] - DisplayUtils.getWidth() / 2 + holder.addToPlayListIvContainer.getWidth() / 2;  //parms.x parms.y 0,0点在屏幕中心
        final int startY = parms.y = loc[1] - DisplayUtils.getHeight() / 2 - holder.itemMenuIvContainer.getHeight() / 2;
        try {

            getWindowManager().addView(addAnimationImg, parms); // 窗体添加内容

            final float distanceX = DisplayUtils.getWidth() / 2 - loc[0];
            final float distanceY = (float) (DisplayUtils.getHeight() - loc[1]);

            ValueAnimator.AnimatorUpdateListener animUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float updataValues = (float) animation.getAnimatedValue();
                    parms.x = (int) (startX + distanceX * updataValues);
                }
            };

            ValueAnimator.AnimatorUpdateListener animUpdateListener2 = new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float updataValues = (float) animation.getAnimatedValue();
                    parms.y = (int) (startY + distanceY * updataValues);
                    getWindowManager().updateViewLayout(addAnimationImg, parms);
                }
            };

            ValueAnimator valueAnimX = ObjectAnimator.ofFloat(0, 1);
            valueAnimX.addUpdateListener(animUpdateListener);
            valueAnimX.addListener(animatorListener);

            ValueAnimator valueAnimY = ObjectAnimator.ofFloat(0, -0.2f, 1);
            valueAnimY.addUpdateListener(animUpdateListener2);
            valueAnimY.addListener(animatorListener);

            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(1000);
            animatorSet.play(valueAnimX).with(valueAnimY);
            animatorSet.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            getWindowManager().removeView(addAnimationImg);
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private class ViewHolder {
        private RelativeLayout addToPlayListIvContainer, itemMenuIvContainer;
        private TextView songName, delete;
        private RelativeLayout itemContainer;
        private LinearLayout itemMenu;
    }

    private int menuPosition = -1;

    private void onItemViewClick(final int position, View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.item_menu_iv_container://条目菜单
                        if (position == menuPosition)
                            menuPosition = -1;
                        else
                            menuPosition = position;
                        adapter.notifyDataSetChanged();

                        break;
                    case R.id.item_add_iv_container:
                        startAnimation(position, (ViewHolder) items.get(position).getTag());
                        File insertFile = files.get(position);
                        mediaPlayManager.insertSong(insertFile);
                        ToastUtil.showToast(StringUtil.subPostfix(insertFile.getName()) + "  已插入播放队列", 0);
                        break;
                }
            }
        });
    }

    private void showLoading() {
        progressDialog.show();
    }

    private void dismissLoading() {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "ondestory.");
        SpUtil.put(Constants.lastPlayFilePath, mediaPlayManager.getNowPlayFile().getAbsolutePath());
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
                    mainActivity.initMapManager();
                    mainActivity.initMediaPlayerManager();
                    mainActivity.updateViewState();
                    mainActivity.scrollToNowPlay();
                    mainActivity.dismissLoading();
                    break;
                case updateProgress:
                    mainActivity.setNowProgress();
                    break;
                case updateAnimatior:
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
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
