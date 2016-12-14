package com.sz.china.testmoudule.control;

import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;

import com.sz.china.testmoudule.R;

/**
 * 跟随滑动控制颜色和透明度动画
 * Created by zhangyu on 2016/12/14.
 */

public class ColorChangeBehavior extends CoordinatorLayout.Behavior {
    private static final String TAG = "Behavior";

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        Log.d(TAG, " 搜索容器 R.id.acb_search_container" + R.id.acb_search_container);
        Log.d(TAG, "onStartNestedScroll   child.getId() = " + child.getId() + "   ,directTargetChild.getId() = " + directTargetChild.getId());
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {

        Log.v(TAG, " 搜索容器 R.id.acb_search_container" + R.id.acb_search_container);
        Log.v(TAG, "onNestedPreScroll   child.getId() = " + child.getId() + "   ,target.getId() = " + target.getId());
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        Log.i(TAG, " 搜索容器 R.id.acb_search_container" + R.id.acb_search_container);
        Log.i(TAG, "onNestedScroll   child.getId() = " + child.getId() + "   ,target.getId() = " + target.getId());
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }
}
