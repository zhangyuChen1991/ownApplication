package com.sz.china.testmoudule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sz.china.testmoudule.util.ToastUtil;

/**
 * 测试GestureDetector
 * 主要是为了搞清楚介于click和LongPress之间的手势监听
 * 当手指短时间触摸屏幕再抬起时(时间未达到LongPress，但大于click)，监听onSingleTapUp和onSingleTapConfirmed，从而触发事件。
 * Created by zhangyu on 2016/12/1.
 */

public class GestureDetectorView extends RelativeLayout {
    private static final String TAG = "GestureDetectorView";
    private GestureDetector gestureDetector;
    public GestureDetectorView(Context context) {
        super(context);
        init(context);
    }

    public GestureDetectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public GestureDetectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        gestureDetector = new GestureDetector(context,simpleOnGestureListener);
        this.setClickable(true);//必须要设置这个，simpleOnGestureListener中onSingleTapConfirmed和onSingleTapUp都监听不到
    }

    private GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.v(TAG,"onSingleTapConfirmed..");
            ToastUtil.showToast("点击",0);
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.v(TAG,"onSingleTapUp..");
            ToastUtil.showToast("手指抬起",0);
            return true;//改变返回值，当手指触摸抬起时，gestureDetector消费此次事件
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.v(TAG,"onLongPress..");
            ToastUtil.showToast("长按",0);
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.v(TAG,"onFling..");
            ToastUtil.showToast("Fling",0);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(gestureDetector.onTouchEvent(event)){//gestureDetector消费了事件，打印里面的Log
            Log.d(TAG,"gestureDetector.onTouchEvent(event) == true");
        }
        Log.i(TAG,"super.onTouchEvent(event) = " + super.onTouchEvent(event));
        return super.onTouchEvent(event);
    }

}
