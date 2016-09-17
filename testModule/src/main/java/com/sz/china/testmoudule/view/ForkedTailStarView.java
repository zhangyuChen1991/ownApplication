package com.sz.china.testmoudule.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 由燕尾状图案组成的星形图案，可设置背景图片
 * <p/>
 * 星形图案盖在背景图上面，显示交集的下层内容
 * 最后再加一个外接的圈
 * Created by zhangyu on 2016/9/14.
 */
public class ForkedTailStarView extends ImageView {
    private static final String TAG = "ForkedTailStarView";
    private Context context;
    private int viewWidth;
    private int viewHeight;
    private PorterDuffXfermode porterDuffXFermode;//颜色渲染模式
    private Paint paint;
    //中心点
    private Point centerPoint;
    //半径
    private float radius;

    public ForkedTailStarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public ForkedTailStarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);

    }

    public ForkedTailStarView(Context context) {
        super(context);
        init(null, context);
    }

    private void init(AttributeSet attr, Context context) {
        this.context = context;
        porterDuffXFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);//取两层绘制交集,显示下层。
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(porterDuffXFermode);
        paint.setColor(Color.parseColor("#a9a9a9"));//深灰色
    }


    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);//创建一个与view尺寸相同的bitmap
        Canvas drawCanvas = new Canvas(bitmap);//创建绑定bitmap的画布

        Drawable drawable = getDrawable();
        drawable.setBounds(0, 0, viewWidth, viewHeight);
        drawable.draw(drawCanvas);//画布放到背景drawable上

        paint.setFilterBitmap(false);
        paint.setXfermode(porterDuffXFermode);
        drawCanvas.drawBitmap(createForkedTailStarBitmap(), 0f, 0f, paint);//画布上绘制星形燕尾图案

        paint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0f, 0f, paint);
        //画圆圈
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerPoint.x, centerPoint.y, radius - 5, paint);
    }

    /**
     * 创建燕尾星形bitmap
     *
     * @return
     */
    private Bitmap createForkedTailStarBitmap() {
        Bitmap forkStarBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(forkStarBitmap);


        float radian = (float) (2f * Math.PI * 20f / 360f);
        float cosValue = (float) ((radius - 5) * Math.cos(radian));
        float sinValue = (float) ((radius - 5) * Math.sin(radian));

        float shortLineFraction = 2 / 3f;    //短边与半径的比例
        float shortLine = radius * shortLineFraction;

        Path path = new Path();
        path.moveTo(centerPoint.x, centerPoint.y);
        path.lineTo(translateCorrdinateX(cosValue), translateCorrdinateY(sinValue));
        path.lineTo(translateCorrdinateX(shortLine), translateCorrdinateY(0));
        path.lineTo(translateCorrdinateX(cosValue), translateCorrdinateY(-sinValue));
        path.lineTo(centerPoint.x, centerPoint.y);
        path.lineTo(translateCorrdinateX(sinValue), translateCorrdinateY(-cosValue));
        path.lineTo(translateCorrdinateX(0), translateCorrdinateY(-shortLine));
        path.lineTo(translateCorrdinateX(-sinValue), translateCorrdinateY(-cosValue));
        path.lineTo(centerPoint.x, centerPoint.y);
        path.lineTo(translateCorrdinateX(-cosValue), translateCorrdinateY(-sinValue));
        path.lineTo(translateCorrdinateX(-shortLine), translateCorrdinateY(0));
        path.lineTo(translateCorrdinateX(-cosValue), translateCorrdinateY(sinValue));
        path.lineTo(centerPoint.x, centerPoint.y);
        path.lineTo(translateCorrdinateX(-sinValue), translateCorrdinateY(cosValue));
        path.lineTo(translateCorrdinateX(0), translateCorrdinateY(shortLine));
        path.lineTo(translateCorrdinateX(sinValue), translateCorrdinateY(cosValue));
        path.lineTo(centerPoint.x, centerPoint.y);
        path.close();

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        canvas.drawPath(path, paint);
        return forkStarBitmap;
    }

    /**
     * 将view的坐标值转换为 以view中心为坐标原点的坐标值 转换X坐标
     *
     * @param corrX 待转换的X坐标值
     * @return 转换后以view中心为坐标原点的X坐标值
     */
    private float translateCorrdinateX(float corrX) {
        return corrX + 0.5f * viewWidth;
    }

    /**
     * 将view的坐标值转换为 以view中心为坐标原点的坐标值 转换Y坐标
     *
     * @param corrY 待转换的Y坐标值
     * @return 转换后以view中心为坐标原点的Y坐标值
     */
    private float translateCorrdinateY(float corrY) {
        return corrY + 0.5f * viewHeight;
    }

    /**
     * 设置背景图
     *
     * @param background
     */
    public void setBackgroundImg(Drawable background) {
        setBackground(background);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure  viewWidth = " + viewWidth + " ,viewHeight = " + viewHeight);
        centerPoint = new Point(viewWidth / 2f, viewHeight / 2f);
        radius = Math.min(viewWidth, viewHeight) / 2f;
    }

    private class Point {
        //x、y坐标
        public float x, y;

        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
