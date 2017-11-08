package com.sz.china.testmoudule.view;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


public class StretchView extends View {
    private static final String TAG = "StretchView";
    private int viewWidth;
    private int viewHeight;
    private int initPosition = 0;
    private int initWidth;
    private int initHeight;
    private int stretchLength;
    private int stretchDirection = 0;
    public static int DirectionLeft = 33;
    public static int DirectionRight = 34;
    private long animDurection = 70L;
    private boolean isAnimating = false;
    private float radius = 0.0F;
    private float stretchRatio = 0.0F;
    private float verticalCtrlRatio = 0.4F;
    private int color = Color.parseColor("#42bd41");
    private ValueAnimator animator;
    private Paint paint;
    private Path path;
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        public void onAnimationUpdate(ValueAnimator animation) {
            StretchView.this.stretchRatio = ((Float)animation.getAnimatedValue()).floatValue();
            StretchView.this.invalidate();
            Log.i("StretchView", "onAnimationUpdate  ,stretchRatio = " + StretchView.this.stretchRatio);
        }
    };
    private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            StretchView.this.changeDirection();
            StretchView.this.resetInitData();
            StretchView.this.invalidate();
            Log.w("StretchView", "onAnimationEnd..");
        }
    };

    public StretchView(Context context) {
        super(context);
        this.init();
    }

    public StretchView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public StretchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.animator = ValueAnimator.ofFloat(new float[]{1.0F, 0.0F});
        this.animator.setDuration(this.animDurection);
        this.animator.addUpdateListener(this.animatorUpdateListener);
        this.animator.addListener(this.animatorListenerAdapter);
        this.paint = new Paint(1);
        this.paint.setColor(this.color);
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setStrokeCap(Paint.Cap.ROUND);
        this.path = new Path();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float circleCenter1X = (float)this.initPosition + this.radius;
        float circleCenter1Y = (float)this.initHeight / 2.0F;
        float circleCenter2X = (float)(this.initPosition + this.initWidth) - this.radius;
        float circleCenter2Y = (float)this.initHeight / 2.0F;
        float p1x = circleCenter1X;
        float p1y = 0.0F;
        float p2x = circleCenter2X;
        float p2y = 0.0F;
        float p4x = circleCenter1X;
        float p4y = (float)this.initHeight;
        float p3x = circleCenter2X;
        float p3y = (float)this.initHeight;
        float ctrl1x;
        float ctrl1y;
        float ctrl2x;
        float ctrl2y;
        if(this.stretchDirection == DirectionLeft) {
            circleCenter1X = (float)this.initPosition + this.radius - this.stretchRatio * (float)this.stretchLength;
            p1x = circleCenter1X;
            p4x = circleCenter1X;
            ctrl1x = (circleCenter1X - this.stretchRatio * (float)this.stretchLength + circleCenter2X) * 0.6F;
            ctrl1y = p1y + (float)this.initHeight * this.verticalCtrlRatio * this.stretchRatio;
            ctrl2x = (circleCenter1X - this.stretchRatio * (float)this.stretchLength + circleCenter2X) * 0.6F;
            ctrl2y = p4y - (float)this.initHeight * this.verticalCtrlRatio * this.stretchRatio;
        } else if(this.stretchDirection == DirectionRight) {
            circleCenter2X = (float)(this.initPosition + this.initWidth) - this.radius + this.stretchRatio * (float)this.stretchLength;
            p2x = circleCenter2X;
            p3x = circleCenter2X;
            ctrl1x = (circleCenter2X + this.stretchRatio * (float)this.stretchLength + circleCenter1X) * 0.4F;
            ctrl1y = p1y + (float)this.initHeight * this.verticalCtrlRatio * this.stretchRatio;
            ctrl2x = (circleCenter2X + this.stretchRatio * (float)this.stretchLength + circleCenter1X) * 0.4F;
            ctrl2y = p4y - (float)this.initHeight * this.verticalCtrlRatio * this.stretchRatio;
        } else {
            ctrl1x = (circleCenter1X + circleCenter2X) * 0.5F;
            ctrl1y = p1y;
            ctrl2x = (circleCenter2X + p3y) * 0.5F;
            ctrl2y = p3y;
        }

        canvas.drawCircle(circleCenter1X, circleCenter1Y, this.radius, this.paint);
        canvas.drawCircle(circleCenter2X, circleCenter2Y, this.radius, this.paint);
        this.path.reset();
        this.path.moveTo(p1x, p1y);
        this.path.quadTo(ctrl1x, ctrl1y, p2x, p2y);
        this.path.lineTo(p3x, p3y);
        this.path.quadTo(ctrl2x, ctrl2y, p4x, p4y);
        this.path.close();
        canvas.drawPath(this.path, this.paint);
    }

    private boolean startAnim() {
        this.isAnimating = true;
        this.animator.start();
        return true;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.viewWidth = this.getMeasuredWidth();
        this.viewHeight = this.getMeasuredHeight();
        if(this.viewHeight > 0) {
            this.radius = (float)(this.viewHeight / 2);
            this.initHeight = this.viewHeight;
        }

    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setInitPosition(int initPosition) {
        this.initPosition = initPosition;
    }

    public void setInitWidth(int initWidth) {
        this.initWidth = initWidth;
    }

    public void setParams(float stretchRatio, int direction, int stretchLength) {
        if(stretchLength != 0) {
            this.isAnimating = true;
        }

        this.stretchRatio = stretchRatio;
        this.stretchDirection = direction;
        this.stretchLength = stretchLength;
        this.invalidate();
    }

    public void setStretchLength(int stretchLength) {
        this.stretchLength = stretchLength;
    }

    public void startShrink(int initPosition) {
        this.initPosition = initPosition;
        this.changeDirection();
        this.startAnim();
    }

    private void changeDirection() {
        if(this.stretchDirection == DirectionLeft) {
            this.stretchDirection = DirectionRight;
        } else if(this.stretchDirection == DirectionRight) {
            this.stretchDirection = DirectionLeft;
        }

    }

    public void resetInitData() {
        this.stretchLength = 0;
        this.stretchRatio = 0.0F;
        this.stretchDirection = 0;
        this.isAnimating = false;
    }
}
