package com.cc.musiclist.manager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import com.cc.musiclist.constant.Constants;
import com.cc.musiclist.util.ToastUtil;

import java.io.File;
import java.io.IOException;
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
    }


    public void setPlayCallBack(PlayCallBack playCallBack) {
        this.playCallBack = playCallBack;
    }

    public void setNowFilePosition(int nowFilePosition) {
        this.nowFilePosition = nowFilePosition;
        nowPlayFile = playLists.get(nowFilePosition);
    }

    public int getNowFilePosition() {
        return nowFilePosition;
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
        this.nowPlayFile = nowPlayFile;
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
        this.playLists = playLists;
    }

    public boolean prepare() {
        try {
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(nowPlayFile.getPath());
            mediaPlayer.prepare();
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


    public void playNext() {
        switch (playModel) {
            case SEQUENTIA_LOOP_MODEL:
                nowFilePosition++;
                if (nowFilePosition == playLists.size())
                    nowFilePosition = 0;
                setNowFilePosition(nowFilePosition);
                break;
            case RANDOM_MODEL:
                Random r = new Random();
                int next = r.nextInt(playLists.size());
                setNowFilePosition(next);
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
