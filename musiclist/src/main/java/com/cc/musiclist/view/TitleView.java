package com.cc.musiclist.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.R;
import com.cc.musiclist.util.DisplayUtils;

/**
 * Created by zhangyu on 2016-06-30 17:01.
 */
public class TitleView extends RelativeLayout {
    private View markView;
    private TextView title1, title2, title3;

    private TitleViewCallBack callBack;

    private int nowChooseed = 2;    //初始化时默认位置在中间

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private RelativeLayout.LayoutParams params;

    private void init(Context context) {
        View.inflate(context, R.layout.title_layout, this);
        markView = findViewById(R.id.mark_view);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);

        title1.setOnClickListener(onClickListener);
        title2.setOnClickListener(onClickListener);
        title3.setOnClickListener(onClickListener);

        params = (RelativeLayout.LayoutParams) markView.getLayoutParams();
        params.width = DisplayUtils.getWidth() / 3;
        markView.setLayoutParams(params);
    }

    /**
     * 标记view移动动画
     *
     * @param fromPosition
     * @param toPosition
     */
    public void translateTo(int fromPosition, int toPosition) {
        float distance = (toPosition - fromPosition) * markView.getWidth();
        float currX = markView.getTranslationX();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(markView, "translationX", currX, currX + distance);
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addListener(animatorListener);
    }

    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!animationing) {        //动画过程中不允许点击
                animationing = true;
                switch (v.getId()) {
                    case R.id.title1:
                        translateTo(nowChooseed, 1);
                        nowChooseed = 1;
                        break;
                    case R.id.title2:
                        translateTo(nowChooseed, 2);
                        nowChooseed = 2;
                        break;
                    case R.id.title3:
                        translateTo(nowChooseed, 3);
                        nowChooseed = 3;
                        break;
                }

                if (null != callBack)
                    callBack.onSelected(nowChooseed - 1);//从0计
            }
        }
    };

    boolean animationing = false;
    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            animationing = false;
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void setCallBack(TitleViewCallBack callBack) {
        this.callBack = callBack;
    }

    public interface TitleViewCallBack {
        public void onSelected(int position);
    }
}
