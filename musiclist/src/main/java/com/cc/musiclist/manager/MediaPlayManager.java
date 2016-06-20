package com.cc.musiclist.manager;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by zhangyu on 2016/6/19 14:51.
 * 播放管理器
 */
public class MediaPlayManager {
    private MediaPlayer mediaPlayer;
    private ArrayList<File> playLists;
    private File nowPlayFile;
    private int nowFilePosition;
    private int playModel;
    //顺序模式，随机模式，单曲模式
    public static final int SEQUENTIAL_MODEL = 0x11, RANDOM_MODEL = 0X13, SINGLE_MODEL = 0X15;


    public MediaPlayManager() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(mCompletionListener);
        playModel = SEQUENTIAL_MODEL;
    }


    public void setNowFilePosition(int nowFilePosition) {
        this.nowFilePosition = nowFilePosition;
        nowPlayFile = playLists.get(nowFilePosition);
    }
    public void setPlayModel(int playModel) {
        this.playModel = playModel;
    }

    public int getPlayModel() {
        return playModel;
    }


    public void setNowPlayFile(File nowPlayFile) {
        this.nowPlayFile = nowPlayFile;
    }

    /**
     * 设置播放列表
     *
     * @param playLists
     */
    public void setPlayLists(ArrayList<File> playLists) {
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

    public boolean start() {
        mediaPlayer.start();
        return false;
    }

    public boolean pause() {
        mediaPlayer.pause();
        return false;
    }

    public boolean stop() {
        mediaPlayer.stop();
        return false;
    }

    public void nextModel() {
        if (playModel == SEQUENTIAL_MODEL) {
            playModel = RANDOM_MODEL;
        } else if (playModel == RANDOM_MODEL)
            playModel = SINGLE_MODEL;
        else
            playModel = SEQUENTIAL_MODEL;
    }

    MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //播放完
            playNext();
        }

    };


    public void playNext() {
        switch (playModel){
            case SEQUENTIAL_MODEL:
                setNowFilePosition(nowFilePosition++);
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
    }

    public void destory() {
        playLists.clear();
        mediaPlayer.release();
        mediaPlayer = null;
        playLists = null;
    }

}
