package com.sz.china.testmoudule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆形环形渐变效果
 * 适用于雷达扫描效果
 * Created by zhangyu on 2016/10/17.
 */
public class SweepGridentView extends View {

    private float centerX = 0;

    private float centerY = 0;

    private Paint paint = null;
    private int viewWidth;
    private int viewHeight;
    private float raidus;

    //渐变颜色列表 默认是半透明白色到透明状态的渐变
    private int[] colors = new int[]{Color.parseColor("#88ffffff"), Color.parseColor("#00ffffff")};

    public SweepGridentView(Context context) {
        super(context);
    }

    public SweepGridentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SweepGridentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(centerX, centerY, raidus, paint);
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        initResources();
    }

    private void initResources() {
        centerX = viewWidth / 2f;
        centerY = viewHeight / 2f;
        raidus = (viewHeight < viewWidth ? viewHeight : viewWidth) / 2f;

          /* 设置渐变色 */
        SweepGradient shader = new SweepGradient(centerX, centerY, colors, null);
        Matrix matrix = new Matrix();
        // 使用matrix改变渐变色起始位置，默认是在90度位置
        matrix.setRotate(-90, centerX, centerY);
        shader.setLocalMatrix(matrix);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(shader);
    }

    /**
     * 设置颜色渐变列表
     *
     * @param colors 颜色数组
     */
    public void setColors(int[] colors) {
        if (null != colors)
            this.colors = colors;
    }
}
