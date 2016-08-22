package com.sz.china.testmoudule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by zhangyu on 2016/8/10.
 */
public class ScrollRemoveItemListView extends ListView {
    private static final String TAG = "SRIListView";
    boolean isVerticalScroll = true;

    public ScrollRemoveItemListView(Context context) {
        super(context);
        init();
    }

    public ScrollRemoveItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollRemoveItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getChildCount();
        ScrollRemoveItemView v = (ScrollRemoveItemView) getChildAt(0);
        v.setRemoveListener(new ScrollRemoveItemView.RemoveListener() {
            @Override
            public void beRemoved() {

            }
        });


    }

    boolean thisTouchHadDeal = false;

    private int startX, nowTouchX, startY, nowTouchY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent.. isVerticalScroll = " + isVerticalScroll);

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                Log.w(TAG, "ACTION_DOWN..  startX = " + startX + "  ,startY = " + startY);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.v(TAG, "ACTION_MOVE.. isVerticalScroll = " + isVerticalScroll);
                if (!thisTouchHadDeal) {
                    thisTouchHadDeal = true;
                    nowTouchX = (int) ev.getX();
                    nowTouchY = (int) ev.getY();
                    isVerticalScroll = !isHorizontalScroll(startX, startY, nowTouchX, nowTouchY);
                    Log.w(TAG, "ACTION_MOVE..判断后 isVerticalScroll = " + isVerticalScroll);
                }
                if (isVerticalScroll)   //如果是竖直方向滑动，沿袭父类做法
                    return super.onInterceptTouchEvent(ev);
                else {    //横向滑动，不做响应事件，让子View处理事件
                    return false;
                }
            case MotionEvent.ACTION_UP:
                Log.v(TAG, "ACTION_UP.. isVerticalScroll = " + isVerticalScroll);
                isVerticalScroll = true;
                thisTouchHadDeal = false;
                break;
        }

        return super.onInterceptTouchEvent(ev);
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

        Log.e(TAG, "startX = " + startX + "  ,startY = " + startY + "  ,secondX = " + secondX + "  ,secondY = " + secondY + "  ,distanceX = " + distanceX + "  ,distanceY = " + distanceY);
        if (distanceX > distanceY)
            ret = true;
        return ret;
    }


    private interface itemChangeListener{
        public void beRemovedAt(int position);
    }
}
