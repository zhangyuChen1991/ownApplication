package com.cc.musiclist.fragment;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.MainActivity;
import com.cc.musiclist.R;
import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.manager.MapManager;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.manager.TimeCountManager;
import com.cc.musiclist.util.DisplayUtils;
import com.cc.musiclist.util.FileDirectoryUtil;
import com.cc.musiclist.util.FileUtil;
import com.cc.musiclist.util.SpUtil;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhangyu on 2016-07-01 10:51.
 */
public class AudioFileFragment extends Fragment {
    private static final String TAG = "AudioFileFragment";
    private ListView listView;
    private HashMap<Integer, View> items;
    public MAdapter adapter;
    private TranslateUtil tu;
    private String filePathCache;
    public List<File> files;
    private MediaPlayManager mediaPlayManager;
    private MainActivity.MHandler handler;
    private ImageView addAnimationImg;
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.audio_file_fragment, container,false);
        return rootView;
    }

    @Override
    public void onStart() {
        initView();
        initResources();
        initViewState();
        super.onStart();
    }

    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.listview_audio_file);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(onItemClickListener);
        addAnimationImg = new ImageView(getActivity());

    }

    private void initResources() {
        items = new HashMap<>();
        adapter = new MAdapter();
        tu = new TranslateUtil();
        mediaPlayManager = MediaPlayManager.getInstance();
        handler = ((MainActivity) getActivity()).getHandler();
        initFiles();
    }

    private void initViewState() {
        listView.setAdapter(adapter);
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
                v = View.inflate(getContext(), R.layout.dapter_view, null);
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

            getActivity().getWindowManager().addView(addAnimationImg, parms); // 窗体添加内容

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
                    getActivity().getWindowManager().updateViewLayout(addAnimationImg, parms);
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
            getActivity().getWindowManager().removeView(addAnimationImg);
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

    /**
     * 初始化歌曲文件目录
     */
    private void initFiles() {
        filePathCache = SpUtil.getString(Constants.filesPathCache, "");
        if (TextUtils.isEmpty(filePathCache))
            scanFile();
        else {
            files = tu.StringsToFileList(tu.stringToStringArray(filePathCache));
            handler.sendEmptyMessage(Constants.initFileOver);
        }
    }

    public void scanFile() {
        new Thread(scanFileRunnable).start();
    }

    /**
     * 扫描歌曲文件执行体
     */
    private Runnable scanFileRunnable = new Runnable() {
        @Override
        public void run() {

            try {
                handler.sendEmptyMessage(Constants.initFileStart);
                Thread.sleep(500);
                String[] fileTypes = {"mp3", "wav", "wma"};
                files = FileUtil.getFileList(FileDirectoryUtil.getSdCardDirectory(), fileTypes);

                SpUtil.put(Constants.filesPathCache, tu.stringArrayToString(tu.fileListToStrings(files)));
                handler.sendEmptyMessage(Constants.initFileOver);
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

    public void scrollToNowPlay() {
        listView.setSelection(MapManager.INSTANCE.getFilePosition(mediaPlayManager.getNowPlayFile()));
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.listview_audio_file:
                    if (position < files.size()) {
                        mediaPlayManager.setNowPlayFile(files.get(position));
                        mediaPlayManager.prepare();
                        mediaPlayManager.start();
                    }
                    break;
            }
        }
    };

    private TimeCountManager timeCountManager = new TimeCountManager() {
        @Override
        public void onCount() {
            Log.d(TAG, "sendEmptyMessage(updateProgress)");
            handler.sendEmptyMessage(Constants.updateProgress);
        }
    };

    public void adapterNotifyDataChange() {
        adapter.notifyDataSetChanged();
    }
}
