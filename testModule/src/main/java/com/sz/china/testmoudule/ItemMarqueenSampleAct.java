package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

import java.util.zip.Inflater;

/**
 * 文字、图片轮播
 * 利用ViewFlipper实现布局(文字、图片等)竖直轮播效果
 * ViewFlipper不仅可以实现轮播  配合复杂布局和不同的anim，可以做出多种效果，平移、透明度渐变、缩放、旋转都可以融合起来做
 * 这里只用了平移动画，实现轮播效果
 * Created by zhangyu on 2017/2/13.
 */

public class ItemMarqueenSampleAct extends Activity {

    @ViewInject(R.id.avm_view_flipper)
    ViewFlipper viewFlipper;
    @ViewInject(R.id.avm_view_flipper1)
    ViewFlipper viewFlipper1;

    private String[] texts = {"这是啥广告", "这不是广告", "这是轮播器", "轮到我没有", "我是最后一个"};
    private int[] imgs = {R.drawable.ig2, R.drawable.ig3, R.drawable.img1, R.drawable.ig2, R.drawable.ig3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vertical_marqueen);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {

        for (int i = 0; i < texts.length; i++) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.text_for_view_flipper, null);
            textView.setText(texts[i]);
            viewFlipper.addView(textView);
        }

        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(1000);
        viewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_in));
        viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));

        for (int i = 0; i < texts.length; i++) {
            LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_for_view_flipper, null);
            TextView tv = (TextView) linearLayout.findViewById(R.id.ifvf_tv);
            ImageView iv = (ImageView) linearLayout.findViewById(R.id.ifvf_iv);

            iv.setImageResource(imgs[i]);
            tv.setText(texts[i]);
            viewFlipper1.addView(linearLayout);
        }

        viewFlipper1.setAutoStart(true);
        viewFlipper1.setFlipInterval(1500);
        viewFlipper1.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.bottom_in));
        viewFlipper1.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
        //子view同样可以写在xml文件中
        //这里的anim文件是上下移动  水平移动改写anim即可  同时可以加入渐变 旋转等等效果  自行扩展
    }
}
