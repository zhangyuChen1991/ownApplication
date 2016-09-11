package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;

import com.cc.library.anim.ActivityRotateAnimationUtil;
import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * 测试旋转activity翻页工具:ActivityRotateAnimationUtil
 * Created by zhangyu on 2016-07-13 14:22.
 */
public class TestRotateAnimationAct extends Activity{
    private static final String TAG = "TestRotateActivity";
    @ViewInject(R.id.bg_img)
    private ImageView bgImage;

    private ActivityRotateAnimationUtil activityRotateAnimationUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//无actionbar
        setContentView(R.layout.activity_immersion);

        ViewInjectUtil.injectView(this);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        activityRotateAnimationUtil = new ActivityRotateAnimationUtil(this);
        Log.d(TAG,"activityRotateAnimationUtil.onTouchListener = " + activityRotateAnimationUtil.onTouchListener + "  ,bgImage = " + bgImage);
        bgImage.setOnTouchListener(activityRotateAnimationUtil.onTouchListener);
    }
}
