package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cc.library.annotation.ViewInjectUtil;

/**
 * 横向滑动的页面菜单目录
 * Created by zhangyu on 2016/8/23.
 */
public class HorizontalScrollPageMenuAct extends Activity implements View.OnClickListener {

    private int zoomOutState = 0x13, zoomInState = 0x15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_page_menu);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv:
                break;
        }
    }

//    /**
//     * 开始缩放及平移动画
//     *
//     * @param zoomOut 放大或缩小，  true 放大；false缩小
//     */
//    private void startAnimation(boolean zoomOut) {
//        ObjectAnimator zoomOutX;
//        ObjectAnimator zoomOutY;
//        ObjectAnimator translateLeft;//左侧image平移动画
//        ObjectAnimator translateRight;//右侧image平移动画
//
//        if (zoomOut) {
//            zoomOutX = ObjectAnimator.ofFloat(iv2, "scaleX", 1f, 2f);
//            zoomOutY = ObjectAnimator.ofFloat(iv2, "scaleY", 1f, 2f);
//            float curTranslationXL = iv1.getTranslationX();
//            translateLeft = ObjectAnimator.ofFloat(iv1, "translationX", curTranslationXL, curTranslationXL - 200f);
//            float curTranslationXR = iv3.getTranslationX();
//            translateRight = ObjectAnimator.ofFloat(iv3, "translationX", curTranslationXR, curTranslationXR + 200f);
//        } else {
//            zoomOutX = ObjectAnimator.ofFloat(iv2, "scaleX", 2f, 1f);
//            zoomOutY = ObjectAnimator.ofFloat(iv2, "scaleY", 2f, 1f);
//            float curTranslationXL = iv1.getTranslationX();
//            translateLeft = ObjectAnimator.ofFloat(iv1, "translationX", curTranslationXL, curTranslationXL + 200f);
//            float curTranslationXR = iv3.getTranslationX();
//            translateRight = ObjectAnimator.ofFloat(iv3, "translationX", curTranslationXR, curTranslationXR - 200f);
//        }
//
//        AnimatorSet animSet = new AnimatorSet();
//        animSet.play(zoomOutX).with(zoomOutY).with(translateLeft).with(translateRight);
//        animSet.setDuration(500);
//        animSet.start();
//    }
}
