package com.cc.library.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * 一个很酷的加载进度View
 * 仿 https://github.com/Ajian-studio/GADownloading
 * 目的是学习。。
 * Created by zhangyu on 2016/12/17.
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class CoolDownloading extends View {
    private int vWidth, vHeight;
    private Point center;
    private final double sin45 = Math.sin(45 * 2 * Math.PI / 360);
    private Context context;
    private Paint paint;
    //左右两段三阶贝塞尔曲线的起点、终点、各自的两个控制点
    private Point startP, stopPL, stopPR, ctrlL1, ctrlL2, ctrlR1, ctrlR2;
    //左右两段三阶贝塞尔曲线的path
    private Path pathLeft, pathRight;

    //圆圈的半径
    private float circleRadius = 200;

    public CoolDownloading(Context context) {
        super(context);
        init(context);
    }

    public CoolDownloading(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CoolDownloading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);

        pathLeft = new Path();
        pathRight = new Path();

        circleToLinePathAnim = ValueAnimator.ofFloat(0, 1);
        circleToLinePathAnim.setDuration(200);
        circleToLinePathAnim.addUpdateListener(C2LUpdateListener);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCurvePath(canvas, paint);
//        drawCtrlLine(canvas);
    }

    /**
     * 绘制贝塞尔曲线的轨迹
     *
     * @param canvas
     */
    private void drawCurvePath(Canvas canvas, Paint paint) {
        pathLeft.reset();
        pathRight.reset();

        pathLeft.moveTo(startP.x, startP.y);
        pathRight.moveTo(startP.x, startP.y);

        pathLeft.cubicTo(ctrlL1.x, ctrlL1.y, ctrlL2.x, ctrlL2.y, stopPL.x, stopPL.y);
        pathRight.cubicTo(ctrlR1.x, ctrlR1.y, ctrlR2.x, ctrlR2.y, stopPR.x, stopPR.y);

        canvas.drawPath(pathLeft, paint);
        canvas.drawPath(pathRight, paint);
    }

    private ValueAnimator circleToLinePathAnim;

    public void startCircleToLinePathAnim() {
        circleToLinePathAnim.start();
    }


    Path ctrlPath = new Path();

    private void drawCtrlLine(Canvas canvas) {
        ctrlPath.reset();
        ctrlPath.moveTo(ctrlL1.x, ctrlL1.y);
        ctrlPath.lineTo(ctrlL2.x, ctrlL2.y);
        ctrlPath.lineTo(ctrlR2.x, ctrlR2.y);
        ctrlPath.lineTo(ctrlR1.x, ctrlR1.y);
        ctrlPath.close();

        paint.setStrokeWidth(3);
        canvas.drawPath(ctrlPath, paint);

        paint.setStrokeWidth(7);
        canvas.drawPoint(ctrlL1.x, ctrlL1.y, paint);
        canvas.drawPoint(ctrlL2.x, ctrlL2.y, paint);
        canvas.drawPoint(ctrlR1.x, ctrlR1.y, paint);
        canvas.drawPoint(ctrlR2.x, ctrlR2.y, paint);
    }

    final float ctrlWRate = 1.35f;

    private void initData() {
        center = new Point(vWidth / 2f, vHeight / 2f);

        //初始数据模拟画圆
        //将圆分为左半边曲线和右半边曲线，起点为圆上正下方的点，终点为正上方的点
        startP = new Point(center.x, center.y + circleRadius);
        stopPL = new Point(center.x, center.y - circleRadius);
        stopPR = new Point(center.x, center.y - circleRadius);

        //圆的外切正方形，两段贝塞尔曲线，控制点分别为正方形的四个顶点
        //左下角顶点 ctrlL1 左上角顶点 ctrlL2; 右下角顶点 ctrlR1 右上角顶点 ctrlR2

        ctrlL1 = new Point(center.x - ctrlWRate * circleRadius, center.y + circleRadius);
        ctrlL2 = new Point(center.x - ctrlWRate * circleRadius, center.y - circleRadius);
        ctrlR1 = new Point(center.x + ctrlWRate * circleRadius, center.y + circleRadius);
        ctrlR2 = new Point(center.x + ctrlWRate * circleRadius, center.y - circleRadius);

    }

    private ValueAnimator.AnimatorUpdateListener C2LUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();//value 0-->1
            startP.y = center.y + (1 - value) * circleRadius;

            stopPR.x = center.x + value * 2.3f * circleRadius;
            stopPR.y = center.y - (1 - value) * circleRadius;

            stopPL.x = center.x - value * 2.3f * circleRadius;
            stopPL.y = center.y - (1 - value) * circleRadius;

//            ctrlL1.x = (float) (center.x - ctrlWRate * circleRadius + function2(value)* circleRadius);
            ctrlL1.y = center.y + (1 - value) * circleRadius;

//            ctrlL2.x = (float) (center.x - ctrlWRate * circleRadius + function1(value) * circleRadius);
            ctrlL2.y = center.y - circleRadius + value * circleRadius;

//            ctrlR1.x = (float) (center.x + ctrlWRate * circleRadius - function2(value) * circleRadius);
            ctrlR1.y = center.y + (1 - value) * circleRadius;

//            ctrlR2.x = (float) (center.x + ctrlWRate * circleRadius - function1(value) * circleRadius);
            ctrlR2.y = center.y - circleRadius + value * circleRadius;

            invalidate();

            if(value == 1){
                //todo 上下弹的动画
            }
        }
    };

    private float function1(float in) {
        if (in >= 0 && in <= 0.2) {
            return 5f / 2f * in;
        } else if (in <= 1)
            return (5f / 8f) * (1 - in);
        else
            return in;
    }
    private float function2(float in) {
        if (in >= 0 && in <= 0.8) {
            return 5f / 8f * in;
        } else if (in <= 1)
            return (5f / 2f) * (1 - in);
        else
            return in;
    }

    /**
     * 贝塞尔曲线变形
     */
    private void circleToLinePath() {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        vHeight = getMeasuredHeight();
        vWidth = getMeasuredWidth();

        initData();

    }

    private class Point {
        float x, y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Point() {
        }

        ;
    }
}
