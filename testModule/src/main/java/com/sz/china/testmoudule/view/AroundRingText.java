package com.sz.china.testmoudule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sz.china.testmoudule.util.DisplayUtils;

import static android.R.attr.max;

/**
 * 文字贴圆环绕一圈效果 尝试
 * 方案可用
 * Created by zhangyu on 2017/2/18 18:37.
 */

public class AroundRingText extends View {
    private float radius;
    private int vWidth, vHeight;
    private Paint paint;
    private final float ratio = 0.45f;

    public AroundRingText(Context context) {
        super(context);
        init(context);
    }

    public AroundRingText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AroundRingText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(Color.BLACK);
        paint.setTextSize(DisplayUtils.dip2px(context,11));
        paint.setStyle(Paint.Style.STROKE);

        long maxMemmory = (Runtime.getRuntime().maxMemory() / 1024 / 1024);
        Log.d("maxMemmory","maxMemmory = "+maxMemmory+"MB");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        paint.setStrokeWidth(1);
        for (int i = 0; i < 12; i++) {
            canvas.drawText("text"+i, vWidth * 0.5f, (1f - 2f * ratio) * vHeight, paint);//正上方绘制文字
            canvas.rotate(30,vWidth/2,vHeight/2);//旋转
        }
        paint.setStrokeWidth(5);
        canvas.drawCircle(vWidth / 2f,vHeight /2f,radius,paint);
        canvas.restore();
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        vWidth = getMeasuredWidth();
        vHeight = getMeasuredHeight();
        int min = vWidth < vHeight ? vWidth : vHeight;
        radius = min * ratio;
    }
}
