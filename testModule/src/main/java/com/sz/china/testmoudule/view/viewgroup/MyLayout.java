package com.sz.china.testmoudule.view.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/**
 * Created by ZhangYu on 2018/2/5.
 */

public class MyLayout extends ViewGroup {
    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        super.measureChild(child, parentWidthMeasureSpec, parentHeightMeasureSpec);
    }

    @Override
    protected void measureChildren(int widthMeasureSpec, int heightMeasureSpec) {
        super.measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    public class MyLayoutParam extends ViewGroup.MarginLayoutParams{

        public MyLayoutParam(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public MyLayoutParam(int width, int height) {
            super(width, height);
        }

        public MyLayoutParam(MarginLayoutParams source) {
            super(source);
        }
    }
}
