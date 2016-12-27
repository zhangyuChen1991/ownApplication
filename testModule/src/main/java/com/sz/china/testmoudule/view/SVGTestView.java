package com.sz.china.testmoudule.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.cc.library.parser.SvgPathParser;

/**
 * 这个只是演示一下根据沿着svg path路径画动画的demo
 * 由于坐标值缩放等等问题，没办法适配显示所有的svg的图片。
 * Created by zhangyu on 2016/12/27.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SVGTestView extends View {
    private static final String TAG = "SVG";
    private Path path, partPath;
    private SvgPathParser svgPathParser;
    private PathMeasure pathMeasure;
    private Paint paint;
    private Context context;
    private String svgData;
    private int color;
    private float strokeWidth;
    private ValueAnimator valueAnimator;

    public SVGTestView(Context context) {
        super(context);
        init(context);
    }

    public SVGTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SVGTestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void init(Context context) {
        this.context = context;
        svgPathParser = new SvgPathParser();
        pathMeasure = new PathMeasure();
        path = new Path();
        partPath = new Path();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(3);

        getPath();
        initAnimator();
    }

    private void initAnimator() {
        Log.d(TAG, "pathMeasure.getLength() = " + pathMeasure.getLength());
        valueAnimator = ValueAnimator.ofFloat(0f, pathMeasure.getLength());
        valueAnimator.addUpdateListener(updateListener);
        valueAnimator.setDuration(3000);
    }


    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            float value = (float) animation.getAnimatedValue();
            Log.d(TAG, "value = " + value);
            partPath.reset();
//            setLayerType(LAYER_TYPE_SOFTWARE, null);//view层级关闭硬件加速
            partPath.lineTo(0, 0);//getSegment方法的bug 需要调一下这个来避免，否则partPath画不出来。如果不的话，就把硬件加速给关掉。
            pathMeasure.getSegment(0, value, partPath, true);
            invalidate();
        }
    };

    private void getPath() {
        //解析svg数据获取path
        if (!TextUtils.isEmpty(svgData)) {
            path = svgPathParser.parsePath(svgData);

            pathMeasure.setPath(path, false);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //svg数据太小 放大一点(临时)
        canvas.scale(5, 5);
        Log.d(TAG, "onDraw  = ");
        if (null != partPath) {
            canvas.drawPath(partPath, paint);
        }
        super.onDraw(canvas);
    }

    /**
     * 设置svg数据
     *
     * @param svgData
     */
    public void setSvgData(String svgData) {
        this.svgData = svgData;
        getPath();
        initAnimator();
        valueAnimator.start();
    }

    /**
     * 设置颜色
     *
     * @param color
     */
    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
        invalidate();
    }

    /**
     * 设置宽度
     *
     * @param strokeWidth
     */
    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
        invalidate();
    }
}
