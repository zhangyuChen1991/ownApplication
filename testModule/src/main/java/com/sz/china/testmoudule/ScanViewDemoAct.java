package com.sz.china.testmoudule;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by zhangyu on 2016/10/17.
 */
public class ScanViewDemoAct extends Activity {
    @ViewInject(R.id.scan_bg_iv)
    private ImageView scanBgIv;
    @ViewInject(R.id.scan_progress_tv)
    private TextView progressTv;
    private int progress = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_demo);
        ViewInjectUtil.injectView(this);
        initResources();
        initViewState();
    }


    private void initResources() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (progress < 100) {
                        Thread.sleep(300);
                        progress++;
                        ScanViewDemoAct.this.runOnUiThread(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        progressTv.setText(progress + "%");
                                    }
                                }
                        );
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void initViewState() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(scanBgIv, "rotation", 360f, 0f);
        animator.setInterpolator(new DecelerateAccelerateInterpolator());
        animator.setDuration(2500);
        animator.setRepeatCount(-1);
        animator.start();

    }

    public class DecelerateAccelerateInterpolator implements TimeInterpolator {

        @Override
        public float getInterpolation(float input) {
            //匀速变化，不做计算
            return input;
        }
    }
}
