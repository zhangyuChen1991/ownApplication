package com.sz.china.testmoudule.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.util.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面目录水平滑动控件
 * 可浏览页面缩略图，点击进入页面
 * Created by zhangyu on 2016/8/23.
 */
public class PageMenuHorizontalScrollView extends HorizontalScrollView implements View.OnClickListener {
    private static final String TAG = "PageMenuHorScroll";

    private Context context;
    private float imgWidth, imgHeight;
    private List<ImageView> images = new ArrayList<>();
    private LinearLayout container;//子容器
    private float[] nodeXs;
    private int initPosition = 2, nowClickPosition;
    private final int animDuration = 500;

    public PageMenuHorizontalScrollView(Context context) throws Exception {
        super(context);
        init(context);
    }

    public PageMenuHorizontalScrollView(Context context, AttributeSet attrs) throws Exception {
        super(context, attrs);
        init(context);
    }

    public PageMenuHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) throws Exception {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            initView();
        }
    }

    private void init(Context context) throws Exception {
        this.context = context;
    }

    public void initView() {

        imgWidth = DisplayUtils.getWidth() * 0.6f;
        imgHeight = DisplayUtils.getHeight() * 0.6f;

        int count = getChildCount();
        Log.d(TAG, "childCount = " + count);
        if (count > 0) {
            container = (LinearLayout) getChildAt(0);
            container.setOnClickListener(this);

            images.clear();
            container.removeAllViews();
            for (int i = 0; i < 7; i++) {
                ImageView iv;
                if (i == 0) {
                    iv = createImageView(getResources().getDrawable(R.drawable.t_img3), true, false);
                } else if (i == 2) {
                    iv = createImageView(getResources().getDrawable(R.drawable.img_menu), false, false);
                } else if (i == 6) {
                    iv = createImageView(getResources().getDrawable(R.drawable.t_img3), false, true);
                } else {
                    iv = createImageView(getResources().getDrawable(R.drawable.t_img3), false, false);
                }

                images.add(i, iv);
                container.addView(iv);
                pageSetOnclickListener(i, iv);
            }
        }

        nodeXs = new float[images.size()];
        for (int i = 0; i < nodeXs.length; i++) {
            float width = DisplayUtils.getWidth();
            if (initPosition != 0) {
                nodeXs[i] = i * 0.7f * width;
            }
        }

        setChildPosition(initPosition);
        invalidate();
    }

    /**
     * 滚动到目标位置(一般初始进入时用)
     */
    public void scrollToPst() {
        scrollTo((int) nodeXs[initPosition], 0);
    }

    /**
     * 设置目标page两边子view的位置，配合缩小动画作平移(先预设位置，然后由远靠近平移到合适的位置)
     *
     * @param position
     */
    private void setChildPosition(int position) {
        if (position > 0) {
            float curTranslationXL = images.get(position - 1).getTranslationX();
            images.get(position - 1).setTranslationX(curTranslationXL - DisplayUtils.getWidth() * 0.2f);
        }

        if (position < images.size() - 1) {
            float curTranslationXR = images.get(position + 1).getTranslationX();
            images.get(position + 1).setTranslationX(curTranslationXR + DisplayUtils.getWidth() * 0.2f);
        }
    }


    /**
     * 点击回调接口
     *
     * @param position
     * @param v
     */
    private void pageSetOnclickListener(final int position, View v) {
        v.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation(position, true);
            }
        });
    }

    /**
     * 创建ImageVIew
     *
     * @return
     */
    private ImageView createImageView(Drawable drawable, boolean isFirst, boolean isLast) {
        ImageView imageView = new ImageView(context);
        LinearLayout.LayoutParams params;
        params = new LinearLayout.LayoutParams((int) imgWidth, (int) imgHeight);

        params.gravity = Gravity.CENTER_VERTICAL;
        if (isFirst)
            params.leftMargin = (int) (DisplayUtils.getWidth() * 0.2f);
        else
            params.leftMargin = (int) (DisplayUtils.getWidth() * 0.05f);

        if (isLast)
            params.rightMargin = (int) (DisplayUtils.getWidth() * 0.2f);
        else
            params.rightMargin = (int) (DisplayUtils.getWidth() * 0.05f);
        imageView.setLayoutParams(params);

        imageView.setBackground(drawable);
//        imageView.setImageDrawable(drawable);

        return imageView;
    }


    /**
     * 开始放大/缩小动画
     *
     * @param position 目标image位置
     * @param zoomOut  true 放大；false 缩小
     */
    private void startAnimation(int position, boolean zoomOut) {
        ObjectAnimator zoomX;
        ObjectAnimator zoomY;

        ObjectAnimator translateLeft = null;//左侧image平移动画
        ObjectAnimator translateRight = null;//右侧image平移动画

        if (position < images.size()) {

            if (zoomOut) {
                zoomX = ObjectAnimator.ofFloat(images.get(position), "scaleX", 1f, DisplayUtils.getWidth() / imgWidth);
                zoomY = ObjectAnimator.ofFloat(images.get(position), "scaleY", 1f, DisplayUtils.getHeight() / imgHeight);

                if (position > 0) {
                    float curTranslationXL = images.get(position - 1).getTranslationX();
                    translateLeft = ObjectAnimator.ofFloat(images.get(position - 1), "translationX", curTranslationXL, curTranslationXL - DisplayUtils.getWidth() * 0.2f);
                }
                if (position < images.size() - 1) {
                    float curTranslationXR = images.get(position + 1).getTranslationX();
                    translateRight = ObjectAnimator.ofFloat(images.get(position + 1), "translationX", curTranslationXR, curTranslationXR + DisplayUtils.getWidth() * 0.2f);
                }
            } else {
                zoomX = ObjectAnimator.ofFloat(images.get(position), "scaleX", DisplayUtils.getWidth() / imgWidth, 1f);
                zoomY = ObjectAnimator.ofFloat(images.get(position), "scaleY", DisplayUtils.getHeight() / imgHeight, 1f);

                if (position > 0) {
                    float curTranslationXL = images.get(position - 1).getTranslationX();
                    translateLeft = ObjectAnimator.ofFloat(images.get(position - 1), "translationX", curTranslationXL, curTranslationXL + DisplayUtils.getWidth() * 0.2f);
                }

                if (position < images.size() - 1) {
                    float curTranslationXR = images.get(position + 1).getTranslationX();
                    translateRight = ObjectAnimator.ofFloat(images.get(position + 1), "translationX", curTranslationXR, curTranslationXR - DisplayUtils.getWidth() * 0.2f);
                }
            }

            AnimatorSet animSet = new AnimatorSet();
            if (null != translateLeft && null != translateRight) {
                translateLeft.setDuration(animDuration);
                translateRight.setDuration(animDuration);
                animSet.play(zoomX).with(zoomY).with(translateLeft).with(translateRight);
            } else if (null != translateLeft) {
                translateLeft.setDuration(animDuration);
                animSet.play(zoomX).with(zoomY).with(translateLeft);
            } else if (null != translateRight) {
                translateRight.setDuration(animDuration);
                animSet.play(zoomX).with(zoomY).with(translateRight);
            } else
                animSet.play(zoomX).with(zoomY);


            animSet.setDuration(animDuration);
            animSet.start();


        }
    }

    /**
     * 开始缩小动画
     */
    public void startZoomInAnim() {
        startAnimation(initPosition, false);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onclick..");
        scrollTo((int) nodeXs[initPosition], 0);
        invalidate();
    }

    //TODO 自动调整居中
}
