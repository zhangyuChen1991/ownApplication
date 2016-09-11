package com.cc.musiclist.manager;

import android.util.Log;

/**
 * Created by zhangyu on 2016-06-22 15:06.
 * 计时器
 */
public abstract class TimeCountManager {
    private  static final String TAG = "TimeCountManager";
    private boolean countOrNot = false;
    private boolean pause = false;

    public void startCount() {
        pause = false;
        countOrNot = true;
        new Thread(countRunning).start();
    }

    public void pauseCount() {
        pause = true;
    }

    public void finishCount() {
        countOrNot = false;
    }

    public void restartCount() {
        finishCount();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startCount();
    }

    public abstract void onCount();

    private Runnable countRunning = new Runnable() {
        @Override
        public void run() {
            while (countOrNot) {
                if (!pause) {
                    try {
                        Log.v(TAG,"count..");
                        onCount();
                        int c = 0;
                        while (countOrNot && !pause && c < 5) {
                            Thread.sleep(200);
                            c++;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };
}
