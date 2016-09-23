package com.cc.library.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * 圆形imageview
 * 基本原理，图层上第一层放矩形图片，往上第二层放一个圆形的位图(bitmap)，
 * 利用PorterDuffXfermode设置渲染模式，只绘制两图层的交集并显示下一图层的内容
 * 最终的效果就是一个被切出一个圆形的imageview效果.
 * Created by zhangyu on 2016-07-07 14:17.
 */
public class RoundImageView extends ImageView {

    private static final String TAG = "RoundImageView";
    private PorterDuffXfermode porterDuffXFermode;//颜色渲染模式
    //控件宽、高
    private int viewWidth, viewHeight;
    private Paint paint;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);

        porterDuffXFermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);//取两层绘制交集,显示下层。
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);//创建一个与控件尺寸相同的基本位图

        Canvas drawCanvas = new Canvas(bitmap);//创建一个用于绘制内容的画布

        drawable.setBounds(0, 0, viewWidth, viewHeight);//设置drawable绘制区域
        drawable.draw(drawCanvas);//将所设置的图片绘制到画布上(第一层)

        paint.setFilterBitmap(false);
        paint.setXfermode(porterDuffXFermode);
        Bitmap roundBitmap = getRoundBitmap();//圆形位图
        drawCanvas.drawBitmap(roundBitmap, 0, 0, paint);//将圆形位图绘制到画布上(第二层)

        paint.setXfermode(null);
        canvas.drawBitmap(bitmap, 0, 0, paint);//将drawCanvas上绘制好的位图(已经有两层内容并按渲染模式绘制)绘制到控件画布上
    }

    /**
     * 获取一个圆形的bitmap 大小内切image尺寸矩形
     * @return
     */
    private Bitmap getRoundBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas roundCanvas = new Canvas(bitmap);

        int raidus = Math.min(viewHeight, viewWidth) / 2;
        roundCanvas.drawCircle(viewWidth / 2, viewHeight / 2, raidus, new Paint(Color.WHITE));

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
