package com.sz.china.testmoudule.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.utilcode.utils.SizeUtils;
import com.sz.china.testmoudule.R;

/**
 * Created by zhangyu on 2018/9/5.
 */

public class DragView1 extends RelativeLayout {
    private static final String TAG = "DragView";
    float mDownX;
    float mDownY;

    boolean mShrinked = false;
    boolean isAniming = false;
    private Context mContext;

    public DragView1(Context context) {
        super(context);
    }

    public DragView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
    }

    boolean moved = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moved = false;
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moved = true;
                Log.d(TAG,"event.getX(): "+event.getX() + ",getWidth() = "+getWidth());
                if (event.getX() < getWidth() - SizeUtils.dp2px(mContext,50)){//在长条左侧触碰，不跟随移动
                    return false;
                }
                setTranslationX(getTranslationX() + (event.getX() - mDownX));
                setTranslationY(getTranslationY() + (event.getY() - mDownY));
                break;
            case MotionEvent.ACTION_UP:
                if (!isAniming && !moved) {
                    if (mShrinked) {
                        expand();
                    } else {
                        shrink(300);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

        return true;
    }

    public void expand() {
        setPivotX(getWidth());
        TextView tv = (TextView) findViewById(R.id.dv_tv);
//        tv.setVisibility(VISIBLE);
//        setTranslationX(getTranslationX() - SizeUtils.dp2px(mContext, 250));

        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "translationX", tv.getTranslationX(), tv.getTranslationX() - 580);
        animator.setDuration(300);
        animator.start();
        isAniming = true;
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShrinked = false;
                isAniming = false;

            }
        });
    }

    public void shrink(int duration) {
        setPivotX(getWidth());

        final TextView tv = (TextView) findViewById(R.id.dv_tv);
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "translationX", tv.getTranslationX(), tv.getTranslationX() + 580);
        animator.setDuration(duration);
        animator.start();
        isAniming = true;

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShrinked = true;
                isAniming = false;
//                tv.setVisibility(GONE);
//                setTranslationX(getTranslationX() + SizeUtils.dp2px(mContext, 250));
            }
        });

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        shrink(0);
    }

    public void setWidth(int width) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        params.width = width;
        setLayoutParams(params);
    }

}
