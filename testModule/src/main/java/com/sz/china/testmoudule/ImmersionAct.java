package com.sz.china.testmoudule;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by zhangyu on 2016/7/10 08:25.
 */
public class ImmersionAct extends Activity {
    private static final String TAG = "ImmersionAct";
    private View rootView;

    @ViewInject(R.id.bg_img)
    private ImageView bgImage;
    private final int resume = 0, finish = 1;
    private int stationBarHeigth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无actionbar
        setContentView(R.layout.activity_immersion);
        ViewInjectUtil.injectView(this);
        setImmersionStatus();

    }

    private float touchStartX, touchStartY, touchMoveX, touchMoveY;
    private boolean rotateOpen = false;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchStartX = event.getX();
                touchStartY = event.getY();
                Log.i(TAG, "ACTION_DOWN   touchStartX = " + touchStartX + "  ,touchStartY = " + touchStartY);
                break;

            case MotionEvent.ACTION_MOVE:
                touchMoveX = event.getX();
                touchMoveY = event.getY();
                Log.i(TAG, "ACTION_MOVE  touchStartX = " + touchStartX + "  ,touchMoveX = " + touchMoveX + "  ,touchStartY = " + touchStartY + "  ,touchMoveY = " + touchMoveY);

                float moveLength = touchMoveX - touchStartX;

                if (!rotateOpen && moveLength > 10 && Math.abs(touchMoveY - touchStartY) < 50) {
                    rotateOpen = true;
                }

                if (rotateOpen) {
                    float rotateAngle = getRotateAngle(moveLength);
                    rootView.setRotation(rotateAngle);
                    rootView.invalidate();
                }

                break;
            case MotionEvent.ACTION_UP:
                rotateOpen = false;

                float finalRotate = rootView.getRotation();
                ValueAnimator valueAnimator;
                Log.v(TAG, "ACTION_UP finalRotate = " + finalRotate);

                if (finalRotate < 20) {
                    valueAnimator = ValueAnimator.ofFloat(finalRotate, 0);
                    rootView.setTag(resume);     //标志，恢复原状
                } else {
                    valueAnimator = ValueAnimator.ofFloat(finalRotate, 90);
                    rootView.setTag(finish);     //标志，结束页面
                }

                valueAnimator.setDuration(300);
                valueAnimator.addUpdateListener(animatorUpdateListener);
                valueAnimator.addListener(animatorListener);
                valueAnimator.start();

                break;
        }
        return true;
    }

    /**
     * 动画进行中，监听
     */
    ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float rotateAngle = (float) animation.getAnimatedValue();
            rootView.setRotation(rotateAngle);
            rootView.invalidate();
        }
    };

    /**
     * 动画开始、取消、结束、重复监听
     */
    Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            int tag = (int) rootView.getTag();
            if (tag == resume) {

            } else if (tag == finish) {
                finish();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


    Runnable rootViewInvisibleDelay = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    rootView.setVisibility(View.INVISIBLE);
                }
            });
        }
    };

    /**
     * 根据触摸滑动处理Image的旋转角度
     *
     * @param moveLength
     */
    private float getRotateAngle(float moveLength) {
        float totalLength = rootView.getWidth();
        float percent = moveLength / (totalLength * 2f);

        float angle = percent * 90;
        return angle < 0 ? 0 : angle;
    }

    /**
     * 获取activity图像内容的bitmap
     *
     * @return
     */
    public void initRootView() {

        rootView = getWindow().getDecorView().findViewById(android.R.id.content);

        rootView.setPivotX(rootView.getWidth() * 0.5f);
        rootView.setPivotY(rootView.getHeight() * 1.25f);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        initRootView();

        Rect outRect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        stationBarHeigth = outRect.top;
    }

}






















