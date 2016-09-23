package com.cc.library.anim;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * 拖动页面产生旋转动画的工具类
 * 可在页面跳转时使用，有以旋转动画来切换页面的效果
 * 注意 使用本工具的页面主题需设置 android:theme="@android:style/Theme.Translucent",否则背景色会覆盖下一层页面的内容，效果不好。
 * Created by zhangyu on 2016-07-13 12:14.
 */
public class ActivityRotateAnimationUtil {
    private static final String TAG = "RotateAnimationUtil";
    private final int stationBarHeigth;
    private final Bitmap rootBitmap;
    private Activity activity;
    private ImageView rotateImageView;
    private WindowManager.LayoutParams rotateImageParams;
    private final int resume = 0, finish = 1;
    private View rootView;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    /**
     * 构造方法  严重注意:请在onWindowFocusChanged方法中使用,在onCreate()方法中使用将没有效果
     *
     * @param activity
     */
    public ActivityRotateAnimationUtil(Activity activity) {
        this.activity = activity;

        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        stationBarHeigth = outRect.top;

        rotateImageParams = new WindowManager.LayoutParams();
        rootBitmap = getRootViewBitmap();
    }

    /**
     * 获取activity图像内容的bitmap
     *
     * @return
     */
    public Bitmap getRootViewBitmap() {

        rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        Log.d(TAG, "root = " + rootView + "  ,width = " + rootView.getWidth() + "  ,height = " + rootView.getHeight());
        rootView.setDrawingCacheEnabled(true);
        Bitmap rootViewBitmap = rootView.getDrawingCache();

        return rootViewBitmap;
    }

    /**
     * 增加覆盖在上层、与页面内容一致的ImageView
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void addImage() {
        rotateImageView = new ImageView(activity);
        rotateImageView.setImageBitmap(rootBitmap);

        rotateImageParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        rotateImageParams.format = PixelFormat.TRANSLUCENT;        //背景透明
        rotateImageParams.width = rootView.getWidth();
        rotateImageParams.height = rootView.getHeight();
        rotateImageParams.verticalMargin = stationBarHeigth;    //距离顶部高度为一个状态栏的高度

        rotateImageView.setRotation(0);
        rotateImageView.setPivotX(rootView.getWidth() * 1f / 2f);
        rotateImageView.setPivotY(rootView.getHeight() * 5f / 4f);

        activity.getWindow().getWindowManager().addView(rotateImageView, rotateImageParams);
    }

    private float touchStartX, touchStartY, touchMoveX, touchMoveY;
    private boolean rotateOpen = false;

    /**
     * 需要在相应activity中设置此触摸滑动监听，touch事件才会生效
     */
    public View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        touchStartX = event.getX();
                        touchStartY = event.getY();
                        addImage();
                        new Thread(rootViewInvisibleDelay).start();
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
                            rotateImageView.setRotation(rotateAngle);
                            activity.getWindow().getWindowManager().updateViewLayout(rotateImageView, rotateImageParams);
                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        rotateOpen = false;

                        float finalRotate = rotateImageView.getRotation();
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
    };

    /**
     * 动画进行中，监听
     */
    ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float rotateAngle = (float) animation.getAnimatedValue();
            rotateImageView.setRotation(rotateAngle);
            activity.getWindow().getWindowManager().updateViewLayout(rotateImageView, rotateImageParams);
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
                rootView.setVisibility(View.VISIBLE);
                new Thread(removeImgDelay).start();

            } else if (tag == finish) {
                activity.finish();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    Runnable removeImgDelay = new Runnable() {
        @Override
        public void run() {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.getWindow().getWindowManager().removeViewImmediate(rotateImageView);
                }
            });
        }
    };

    /**
     * 延时处理原本View的不可见状态
     */
    Runnable rootViewInvisibleDelay = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(70);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            activity.runOnUiThread(new Runnable() {
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
        float percent = moveLength / (totalLength * 2);

        float angle = percent * 90;
        return angle < 0 ? 0 : angle;
    }

}
