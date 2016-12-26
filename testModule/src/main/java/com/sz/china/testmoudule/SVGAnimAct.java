package com.sz.china.testmoudule;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by zhangyu on 2016/12/26.
 */

public class SVGAnimAct extends Activity {

    @ViewInject(R.id.asa_vector_iv2)
    ImageView vectorIv;
    @ViewInject(R.id.asa_vector_iv1)
    ImageView vectorHeartIv;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg_anim);
        ViewInjectUtil.injectView(this);
//在ImageView中引用动画资源(android:src="@drawable/vector_path_anim")后可以用以下代码，但是在5.0一下系统会崩溃，不建议这样写。
//        Drawable drawable = vectorIv.getDrawable();
//        if(drawable instanceof Animatable){
//            ((Animatable) drawable).start();
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 获取动画效果  vector_path_anim是改变path路径的动画
            AnimatedVectorDrawable mAnimatedVectorDrawable = (AnimatedVectorDrawable)
                    ContextCompat.getDrawable(getApplication(), R.drawable.vector_path_anim);
            vectorIv.setImageDrawable(mAnimatedVectorDrawable);
            if (mAnimatedVectorDrawable != null) {
                mAnimatedVectorDrawable.start();
            }

            AnimatedVectorDrawable mHeartAnimatedVectorDrawable = (AnimatedVectorDrawable)
                    ContextCompat.getDrawable(getApplication(), R.drawable.v_heard_animation);
            vectorHeartIv.setImageDrawable(mHeartAnimatedVectorDrawable);
            if (mHeartAnimatedVectorDrawable != null) {
                mHeartAnimatedVectorDrawable.start();
            }
        }else{
            ToastUtil.showToast("android版本太低 无法演示动画",0);
        }
    }
}
