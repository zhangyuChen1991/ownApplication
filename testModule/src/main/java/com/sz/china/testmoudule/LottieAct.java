package com.sz.china.testmoudule;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.OnCompositionLoadedListener;
import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by zhangyu on 2017/2/22 16:16.
 */

public class LottieAct extends Activity {
    private static final String TAG = "Lottie";

    @ViewInject(R.id.lottie_view1)
    LottieAnimationView lottieAnimationView1;
    @ViewInject(R.id.lottie_view2)
    LottieAnimationView lottieAnimationView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottie);
        ViewInjectUtil.injectView(this);

        init();
    }

    private void init() {
        lottieAnimationView2.setAnimation("MotionCorpse-Jrcanest.json");
        //增加监听  开始、结束、重复播放、取消等
        lottieAnimationView2.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });

        lottieAnimationView2.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Log.d(TAG,valueAnimator.getAnimatedValue().toString());
            }
        });

        //通过JSONObject 加载动画数据
        //更多格式化方法  参考API  如：fromInputStream  fromAssetFileName 等
//        try {
//            LottieComposition.Factory.fromJson(getResources(), new JSONObject(""), new OnCompositionLoadedListener() {
//                @Override
//                public void onCompositionLoaded(LottieComposition composition) {
//
//                    lottieAnimationView2.setComposition(composition);
//                }
//            });
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        lottieAnimationView2.loop(true);
        lottieAnimationView2.playAnimation();

    }
}
