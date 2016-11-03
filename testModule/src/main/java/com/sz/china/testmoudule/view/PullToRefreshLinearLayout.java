package com.sz.china.testmoudule.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.sz.china.testmoudule.util.DisplayUtils;

/**
 * 下拉刷新布局
 * Created by zhangyu on 2016/11/2.
 */
public class PullToRefreshLinearLayout extends LinearLayout {
    private static final String TAG = "ptrLinearLayout";
    private Context context;
    private Scroller mScroller;
    private HeaderView headerView;
    private int startY;
    private int nowTouchY;
    private int preTouchY;
    private boolean refreshing = false;

    private PullToRefreashListener listener;

    public PullToRefreshLinearLayout(Context context) {
        super(context);
        init(context, null);
    }

    public PullToRefreshLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullToRefreshLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        mScroller = new Scroller(context);
        addHeader();
    }

    private void addHeader() {
        headerView = new HeaderView(context);
        addView(headerView, 0);
        Log.v(TAG, "headerView.getHeight() = " + headerView.getHeight());
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
                    startY = (int) event.getY();
                    preTouchY = startY;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(refreshing)//正在刷新
                    return false;
                if (!mScroller.computeScrollOffset()) { // 滚动已结束
                    nowTouchY = (int) event.getY();

                    //根据下拉距离判断处理方式
                    Log.d(TAG, "getCurrY = " + mScroller.getCurrY() + "  ,getFinalY = " + mScroller.getFinalY());
                    int distanceY = nowTouchY - preTouchY;
                    if (distanceY > 0 || mScroller.getCurrY() < 0) {
                        setScrollDistance(distanceY);
                        dealStatePull(mScroller.getCurrY());
                        preTouchY = nowTouchY;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //回滚 刷新
                dealStateUp(mScroller.getCurrY());
                break;
        }
        return true;
    }

    /**
     * 处理下拉放开后的view状态
     *
     * @param currY
     */
    private void dealStateUp(int currY) {
        if(currY == 0)
            return;
        if (currY < 0 && currY > -DisplayUtils.dip2px(context, 50)) {
            scrollBack();//滚回初始位置
        } else {
            //滚到刷新状态的位置
            smoothScrollTo(0, -(headerView.getHeight()));
            headerView.setNowState(HeaderView.STATE.REFRESHING);
            if (null != listener) {
                refreshing = true;
                listener.onRefresh();
            }
        }
    }

    /**
     * 处理下拉时的view状态
     *
     * @param currY
     */
    private void dealStatePull(int currY) {
        Log.w(TAG, "currY = " + currY + "  ,DisplayUtils.dip2px(context,50) = " + DisplayUtils.dip2px(context, 50));
        if (currY <= 0 && currY > -DisplayUtils.dip2px(context, 50)) {
            headerView.setNowState(HeaderView.STATE.PULLING);
        } else
            headerView.setNowState(HeaderView.STATE.READY);
    }


    /**
     * 回滚到起始状态(一般在刷新完成后调用)
     */
    public void scrollBack() {
        smoothScrollTo(0, 0);
    }

    /**
     * 设置当次滚动的距离
     *
     * @param distanceY
     */
    private void setScrollDistance(int distanceY) {
        float Y = -getScrollY();
        int currY = mScroller.getCurrY();
        //在不同阶段设置下拉的幅度比例，最开始拉的快，越往后越拉的慢
        if (currY <= 0 && currY > -headerView.getHeight()) {
            mScroller.setFinalY((-(int) ((Y + distanceY * 0.4f))));
        } else if (currY > -headerView.getHeight() * 1.8f)
            mScroller.setFinalY((-(int) ((Y + distanceY * 0.2f))));
        else
            mScroller.setFinalY((-(int) ((Y + distanceY * 0.1f))));
        Log.v(TAG, "ACTION_MOVE.. distanceY = " + distanceY + "  ,getScrollY = " + getScrollY() + "  ,currY = " + currY);
        invalidate();
    }


    //调用此方法滚动到目标位置
    public void smoothScrollTo(int fx, int fy) {
        int dx = fx - mScroller.getFinalX();
        int dy = fy - mScroller.getFinalY();
        smoothScrollBy(dx, dy);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {

        //设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setListener(PullToRefreashListener listener) {
        this.listener = listener;
    }

    public void refreshOver() {
        scrollBack();
        if (null != listener) {
            refreshing = false;
            listener.refreshOver();
        }
    }

    public interface PullToRefreashListener {
        void onRefresh();
        void refreshOver();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPadding(0, -headerView.getHeight(), 0, -headerView.getHeight() * 2);//设置padding，把headerView“挤到”页面外边去
    }
}
