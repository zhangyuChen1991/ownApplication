package com.sz.china.testmoudule.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 水平滑动可删除的item View
 * 用于listview
 * Created by Administrator on 2016/8/10.
 */
public class ScrollRemoveItemView extends LinearLayout {
    private static final String TAG = "ScrollRemoveItemView";
    private Scroller mScroller;
    private boolean isHorizontalScroll = false;//是否是水平滑动
    private ValueAnimator autoScrollAnimator;
    private int viewWidth;
    private RemoveListener removeListener;//监听接口


    public ScrollRemoveItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ScrollRemoveItemView(Context context) {
        super(context);
        init(context);
    }

    public ScrollRemoveItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        Log.i(TAG, "init..  getScrollX() = " + getScrollX());
    }

    private int startX, nowTouchX, preTouchX, startY, nowTouchY;


    public void setRemoveListener(RemoveListener removeListener) {
        this.removeListener = removeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击停止滚动，或者开始下一轮滚动的数据设置
                if (mScroller.computeScrollOffset()) { // 滚动还未结束
                    mScroller.abortAnimation();        //停止滚动
                } else {
                    Log.i(TAG, "ACTION_DOWN..");
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    preTouchX = startX;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.computeScrollOffset()) { // 滚动已结束
                    nowTouchX = (int) event.getX();
                    nowTouchY = (int) event.getY();
                    isHorizontalScroll = isHorizontalScroll(startX, startY, nowTouchX, nowTouchY);

                    if (isHorizontalScroll) {
                        int distanceX = nowTouchX - preTouchX;
                        setScrollDistance(distanceX);
                        setViewAlpha();
                        preTouchX = nowTouchX;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startAutoScroll();
                break;
        }
        return true;
    }

    /**
     * 开始自动滚动
     */
    private void startAutoScroll() {
        //TODO
        int scrollX = getScrollX();

        if (Math.abs(scrollX) > viewWidth) {
            return;
        }

        float ratio = Math.abs(scrollX) / (float) viewWidth;
        Log.w(TAG, "mark3 startAutoScroll.. ratio = " + ratio + "  ,scrollX = " + scrollX + "  ,viewWidth = " + viewWidth);
        if (Math.abs(scrollX) < (viewWidth / 3)) {//滚回，复原
            autoScrollAnimator = ValueAnimator.ofFloat((float) scrollX, 0f);
            autoScrollAnimator.setDuration((long)200); //根据已移动的距离设置自动滚动的时间 (ratio) * 6
        } else {//滑出，消失
            if (scrollX > 0) {//向左滑
                autoScrollAnimator = ValueAnimator.ofFloat((float) scrollX, (float) viewWidth);
            } else {//向右滑
                autoScrollAnimator = ValueAnimator.ofFloat((float) scrollX, (float) -viewWidth);
            }
            autoScrollAnimator.setDuration((long) ((1 - ratio) * 1200));
        }


        autoScrollAnimator.addUpdateListener(animatorUpdateListener);
        autoScrollAnimator.start();
    }

    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float nowSetScrollPosition = (float) animation.getAnimatedValue();
            Log.d(TAG, "addUpdateListener  nowSetScrollPosition = " + nowSetScrollPosition);
            mScroller.setFinalX((int) nowSetScrollPosition);
            setViewAlpha();
            invalidate();
            if(nowSetScrollPosition == viewWidth || nowSetScrollPosition == 0){
                if(null != removeListener)
                    removeListener.beRemoved();
            }
        }
    };

    /**
     * 设置透明度
     */
    private void setViewAlpha() {
        float scrollX = getScrollX();
        float ratio = Math.abs(scrollX) / (viewWidth * 0.8f);
        if (ratio > 1)
            ratio = 1;
        Log.v(TAG, "setViewAlpha.. scrollX = " + scrollX + "  ,setAlpha : " + (1 - ratio));
        setAlpha(1 - ratio);
    }

    /**
     * 设置当次滚动的距离
     *
     * @param distanceX
     */
    private void setScrollDistance(int distanceX) {
        float X = -getScrollX();
        Log.v(TAG, "ACTION_MOVE.. distanceX = " + distanceX + "  ,getScrollX = " + getScrollX());
        mScroller.setFinalX(-(int) (X + distanceX));
        invalidate();
    }

    /**
     * 判断是横向滑动还是纵向滑动
     *
     * @param startX
     * @param startY
     * @param secondX
     * @param secondY
     * @return
     */
    private boolean isHorizontalScroll(int startX, int startY, int secondX, int secondY) {
        boolean ret = false;
        int distanceX = Math.abs(startX - secondX);
        int distanceY = Math.abs(startY - secondY);

        if (distanceX > distanceY)
            ret = true;
        return ret;
    }

    @Override
    public void computeScroll() {
        Log.d(TAG, "computeScroll..");
        if (mScroller.computeScrollOffset() && isHorizontalScroll) {
            Log.d(TAG, "start to scroll  mScroller.getCurrX() = " + mScroller.getCurrX() + "  ,mScroller.getCurrY() = " + mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
    }

    public interface RemoveListener{
        public void beRemoved();
    }
}
