package com.sz.china.testmoudule;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by zhangyu on 2016/12/8.
 */

public class FrameAnimationAct extends Activity {
    @ViewInject(R.id.afa_iv)
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);
        ViewInjectUtil.injectView(this);
        iv.setImageResource(R.drawable.spindrift_anim);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getDrawable();
        animationDrawable.start();
    }
}
