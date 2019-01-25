package com.cc.library.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by zhangyu on 2016-07-07 14:17.
 */
public class XferModeTestView1 extends ImageView {

    private static final String TAG = "RoundImageView";
    private PorterDuffXfermode porterDuffXFermode;//颜色渲染模式
    //控件宽、高
    private int viewWidth, viewHeight;
    private Paint paint;

    public XferModeTestView1(Context context) {
        super(context);
        init();
    }

    public XferModeTestView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public XferModeTestView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        porterDuffXFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //创建一个图层，在图层上演示图形混合后的效果
        @SuppressLint("WrongConstant") int sc = canvas.saveLayer(0, 0, viewWidth, viewHeight, null, Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);

        paint.setColor(Color.RED);
        canvas.drawRect(0,0,viewWidth,viewHeight,paint);
        paint.setXfermode(porterDuffXFermode);
        paint.setColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(0,0,viewWidth,viewHeight,20,20,paint);
        }
        paint.setXfermode(null);
        canvas.restoreToCount(sc);

    }

    /**
     * 获取一个圆形的bitmap 大小内切image尺寸矩形
     * @return
     */
    private Bitmap getRoundBitmap(float cX) {
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas roundCanvas = new Canvas(bitmap);

        int raidus = Math.min(viewHeight, viewWidth) / 8;
        roundCanvas.drawCircle(cX, viewHeight / 2, raidus, new Paint(Color.WHITE));

        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure  viewWidth = " + viewWidth + " ,viewHeight = " + viewHeight);
    }
}
