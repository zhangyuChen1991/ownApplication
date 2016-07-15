package com.sz.china.testmoudule;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    @ViewInject(R.id.round_image)
    private ImageView roundImage;

    @ViewInject(R.id.scale_big_image)
    private ImageView bigImage;

    @ViewInject(R.id.scale_big_image_container)
    private RelativeLayout bigImgContainer;

    @ViewInject(R.id.rotate_tv)
    private TextView rotateTv;

    private Drawable imageDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewInjectUtil.injectView(this);//给关联注解的字段设置findViewById()值
        initView();
    }


    private void initView() {
        imageDrawable = getImageSrc();

        roundImage.setOnClickListener(this);
        bigImgContainer.setOnClickListener(this);
        rotateTv.setOnClickListener(this);

        bigImgContainer.setVisibility(View.GONE);

    }

    public Drawable getImageSrc() {
        Drawable drawable = roundImage.getDrawable();
        return drawable;
    }


    @Override
    public void onClick(View v) {
        int[] loc = new int[2];
        roundImage.getLocationInWindow(loc);

        switch (v.getId()) {
            case R.id.round_image:
                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, loc[0] + roundImage.getWidth() / 2, loc[1]);
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

                AnimationSet animationSet = new AnimationSet(MainActivity.this, null);
                animationSet.addAnimation(scaleAnimation);
                animationSet.addAnimation(alphaAnimation);
                animationSet.setDuration(300);

                bigImgContainer.setAnimation(animationSet);
                animationSet.start();
                bigImgContainer.setVisibility(View.VISIBLE);
                bigImage.setImageDrawable(imageDrawable);

                break;
            case R.id.scale_big_image_container:
                bigImgContainer.setVisibility(View.GONE);
                ScaleAnimation scaleAnimation1 = new ScaleAnimation(1, 0, 1, 0, loc[0] + roundImage.getWidth() / 2, loc[1]);
                AlphaAnimation alphaAnimation1 = new AlphaAnimation(1, 0);

                AnimationSet animationSet1 = new AnimationSet(MainActivity.this, null);
                animationSet1.addAnimation(scaleAnimation1);
                animationSet1.addAnimation(alphaAnimation1);
                animationSet1.setDuration(300);

                bigImgContainer.setAnimation(animationSet1);
                animationSet1.start();
                bigImgContainer.setVisibility(View.GONE);

                break;

            case R.id.rotate_tv:

                Intent intent = new Intent(MainActivity.this, ImmersionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rotate_left_in, R.anim.rotate_right_out);
                Log.d(TAG, "add ..10:34");
                break;
        }
    }

}
