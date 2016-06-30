package com.cc.musiclist.manager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by zhangyu on 2016/6/19 14:51.
 * 播放管理器
 */
public class MediaPlayManager {
    private static final String TAG = "MediaPlayManager";
    private MediaPlayer mediaPlayer;
    private List<File> playLists;
    private File nowPlayFile;
    private int nowFilePosition;
    private int playModel;
    private int playingState;
    private int nowFileDuration;
    //顺序模式，随机模式，单曲模式
    public static final int SEQUENTIA_LOOP_MODEL = 0x11, RANDOM_MODEL = 0X13, SINGLE_MODEL = 0X15;
    private PlayCallBack playCallBack;

    public interface PlayCallBack {
        public void playNext(String newSongName);

        public void startNew(String newSongName, int duration);

        public void stop();
    }

    public MediaPlayManager() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(mCompletionListener);
        playModel = SEQUENTIA_LOOP_MODEL;
        playingState = Constants.STATE_PLAY_STOP;
        nowFileDuration = 0;
        nowFilePosition = 0;
        playLists = new LinkedList<>();
    }


    public void setPlayCallBack(PlayCallBack playCallBack) {
        this.playCallBack = playCallBack;
    }

    public void setPlayModel(int playModel) {
        this.playModel = playModel;
    }

    public int getPlayModel() {
        return playModel;
    }

    public int getPlayingState() {
        return playingState;
    }

    public File getNowPlayFile() {
        return nowPlayFile;
    }

    public void setNowPlayFile(File nowPlayFile) {
        if (null != nowPlayFile) {
            nowFilePosition = playLists.indexOf(nowPlayFile);
            this.nowPlayFile = nowPlayFile;
        } else
            nowPlayFile = playLists.get(0);
        insertPosition = -1;      //初始化插入位置
    }

    public int getNowFileDuration() {
        return nowFileDuration;
    }

    public int getNowPlayingProgress() {
        if (playingState == Constants.STATE_PLAY_STOP)
            return 0;
        else
            return mediaPlayer.getCurrentPosition();
    }

    /**
     * 设置播放列表
     *
     * @param playLists
     */
    public void setPlayLists(List<File> playLists) {
        for (File file : playLists) {
            this.playLists.add(file);
            Log.i(TAG,file.getName());
        }
    }

    public boolean prepare() {
        try {
            if (null != nowPlayFile && nowPlayFile.exists()) {
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(nowPlayFile.getPath());
                mediaPlayer.prepare();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void start() {
        Log.i(TAG, "start: " + nowPlayFile.getName());
        ToastUtil.showToast("" + nowPlayFile.getName(), 0);
        mediaPlayer.start();
        nowFileDuration = mediaPlayer.getDuration();
        playingState = Constants.STATE_PLAYING;

        if (null != playCallBack)
            playCallBack.startNew(nowPlayFile.getName(), nowFileDuration);
    }

    public void pause() {
        Log.i(TAG, "pause:");
        mediaPlayer.pause();
        playingState = Constants.STATE_PLAY_PAUSE;
    }

    public void stop() {
        Log.i(TAG, "stop:");
        mediaPlayer.stop();
        playingState = Constants.STATE_PLAY_STOP;

        if (null != playCallBack)
            playCallBack.stop();
    }

    public void nextModel() {
        if (playModel == SEQUENTIA_LOOP_MODEL) {
            playModel = RANDOM_MODEL;
        } else if (playModel == RANDOM_MODEL)
            playModel = SINGLE_MODEL;
        else
            playModel = SEQUENTIA_LOOP_MODEL;
    }

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //播放完
            playNext();
        }

    };

    private int insertPosition = -1;

    public void insertSong(File insertFile) {

        if(insertPosition == -1)
            insertPosition = nowFilePosition + 1;

        playLists.add(insertPosition++, insertFile);

        //debug
        for (File file:playLists)
            Log.v(TAG,file.getName());
        //debug
    }

    public void playNext() {
        switch (playModel) {
            case SEQUENTIA_LOOP_MODEL:
                nowFilePosition++;
                if (nowFilePosition >= playLists.size())
                    nowFilePosition = 0;
                setNowPlayFile(playLists.get(nowFilePosition));
                break;
            case RANDOM_MODEL:
                Random r = new Random();
                nowFilePosition = r.nextInt(playLists.size());
                setNowPlayFile(playLists.get(nowFilePosition));
                break;
            case SINGLE_MODEL:
                break;
        }
        prepare();
        start();

        if (null != playCallBack)
            playCallBack.playNext(nowPlayFile.getName());
    }

    public void destory() {
        playLists.clear();
        mediaPlayer.release();
        mediaPlayer = null;
        playLists = null;
    }

}
