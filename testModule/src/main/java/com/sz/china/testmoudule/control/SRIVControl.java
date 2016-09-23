package com.sz.china.testmoudule.control;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * ScrollRemoveItemListView Control
 * Created by zhangyu on 2016/8/12.
 */
public class SRIVControl implements View.OnTouchListener,GestureDetector.OnGestureListener{
    private static final String TAG = "SRIVControl";
    @Override
    public boolean onDown(MotionEvent e) {
        Log.i(TAG,"SRIVControl  onDown..");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.i(TAG,"SRIVControl  onShowPress..");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG,"SRIVControl  onSingleTapUp..");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i(TAG,"SRIVControl  onScroll..");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(TAG,"SRIVControl  onLongPress..");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i(TAG,"SRIVControl  onFling..");
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d(TAG,"SRIVControl  onTouch..");
        return true;
    }
}
