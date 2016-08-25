package com.sz.china.testmoudule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.adapter.TabsAdapter;
import com.sz.china.testmoudule.fragment.Fragment1;
import com.sz.china.testmoudule.fragment.Fragment2;
import com.sz.china.testmoudule.fragment.Fragment3;
import com.sz.china.testmoudule.util.DisplayUtils;
import com.sz.china.testmoudule.view.ScrollMarkView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2016/8/24.
 */
public class TestFt extends FragmentActivity {
    private static final String TAG = "TestFt";
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    @ViewInject(R.id.mark_view)
    private ScrollMarkView markView;

    private TabsAdapter tabsAdapter;
    private List<Fragment> fragmentList;
    private Fragment1 f1;
    private Fragment2 f2;
    private Fragment3 f3;
    private int currentPosition = 0;
    private float averageWidth = DisplayUtils.getWidth() / 3f;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_test_ft);
        ViewInjectUtil.injectView(this);

        initResources();
        initViewState();
    }

    private void initResources() {
        f1 = new Fragment1();
        f2 = new Fragment2();
        f3 = new Fragment3();
        fragmentList = new ArrayList<>();
        fragmentList.add(f1);
        fragmentList.add(f2);
        fragmentList.add(f3);
        tabsAdapter = new TabsAdapter(getSupportFragmentManager(), fragmentList);
        markView.setPositionX(averageWidth * 0.5f);
    }

    private void initViewState() {
        viewpager.setAdapter(tabsAdapter);
        viewpager.addOnPageChangeListener(pageChangeListener);
        viewpager.setCurrentItem(currentPosition);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        float currX = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrolled  position = " + position + "  ,positionOffset = " + positionOffset + "  ,positionOffsetPixels = " + positionOffsetPixels + "  ,currX = " + currX);
            markView.setPositionX((position + 0.5f + positionOffset) * averageWidth);
        }

        @Override
        public void onPageSelected(int position) {
            Log.i(TAG, "onPageSelected  position = " + position);
            currentPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.v(TAG, "state = " + state + "  ,ViewPager.SCROLL_STATE_DRAGGING = " + ViewPager.SCROLL_STATE_DRAGGING
                    + "  ,ViewPager.SCROLL_STATE_IDLE = " + ViewPager.SCROLL_STATE_IDLE
                    + "  ,ViewPager.SCROLL_STATE_SETTLING = " + ViewPager.SCROLL_STATE_SETTLING
            );

            if (state == ViewPager.SCROLL_STATE_DRAGGING) {//开始滑动

            } else if (state == ViewPager.SCROLL_STATE_SETTLING) {//滑动状态 当前位置更新

            } else if (state == ViewPager.SCROLL_STATE_IDLE) {//滑动结束

            }
        }
    };

}
