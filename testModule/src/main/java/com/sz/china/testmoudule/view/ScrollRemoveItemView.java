package com.sz.china.testmoudule.view;

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
    }

    private int startX, nowTouchX, preTouchX, distanceX,startY,nowTouchY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击停止滚动，或者开始下一轮滚动的数据设置
                if (mScroller.computeScrollOffset()) { // 滚动动画还未结束
                    mScroller.abortAnimation();        //停止滚动
                } else {
                    Log.i(TAG, "ACTION_DOWN..");
                    startX = (int) event.getX();
                    startY = (int) event.getY();
                    preTouchX = startX;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.computeScrollOffset()) { // 滚动动画已结束
                    nowTouchX = (int) event.getX();
                    nowTouchY = (int) event.getY();
                    isHorizontalScroll = isHorizontalScroll(startX,startY,nowTouchX,nowTouchY);

                    if(isHorizontalScroll) {
                        distanceX = nowTouchX - preTouchX;
                        float X = -getScrollX();
                        Log.v(TAG, "ACTION_MOVE.. distanceX = " + distanceX + "  ,getScrollX = " + getScrollX());
                        mScroller.setFinalX(-(int) (X + distanceX));
                        invalidate();
                        preTouchX = nowTouchX;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    /**
     * 判断是横向滑动还是纵向滑动
     * @param startX
     * @param startY
     * @param secondX
     * @param secondY
     * @return
     */
    private boolean isHorizontalScroll(int startX,int startY,int secondX,int secondY){
        boolean ret = false;
        int distanceX = Math.abs(startX - secondX);
        int distanceY = Math.abs(startY - secondY);

        if(distanceX > distanceY)
            ret = true;
        return ret;
    }

    //TODO 配套listview在开始滑动时判断是横向还是竖向，确定滑动模式，处理事件
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
}
