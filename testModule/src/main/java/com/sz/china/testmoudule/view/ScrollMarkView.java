package com.sz.china.testmoudule.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangyu on 2016/8/24.
 */
public class ScrollMarkView extends View {
    private float positionX = 0,positionY,radius;
    private Paint paint;
    public ScrollMarkView(Context context) {
        super(context);
        init();
    }

    public ScrollMarkView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public ScrollMarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(positionX,positionY,radius,paint);
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        positionY = radius = height / 2;

    }
}
