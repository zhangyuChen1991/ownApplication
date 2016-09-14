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

/**
 * 由燕尾状图案组成的星形图案，可设置背景图片
 *
 * 星形图案盖在背景图上面，显示交集的下层内容
 * 最后再加一个外接的圈
 * Created by zhangyu on 2016/9/14.
 */
public class ForkedTailStarView extends View {
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
        Bitmap bitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);//创建一个与view尺寸相同的bitmap
        Canvas drawCanvas = new Canvas(bitmap);//创建绑定bitmap的画布

        Drawable drawable = getBackground();
        drawable.draw(drawCanvas);//画布放到背景drawable上

        drawCanvas.drawBitmap(createForkedTailStarBitmap(),0f,0f,paint);//画布上绘制星形燕尾图案

        paint.setXfermode(null);
        canvas.drawBitmap(bitmap,0f,0f,paint);
        super.onDraw(canvas);
    }

    /**
     * 创建燕尾星形bitmap
     * @return
     */
    private Bitmap createForkedTailStarBitmap() {
        Bitmap forkStarBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(forkStarBitmap);

        Path path = new Path();
        path.moveTo(centerPoint.x,centerPoint.y);
        //TODO 计算12个点的分布位置，连接path

        canvas.drawPath(path,new Paint());
        return forkStarBitmap;
    }

    /**
     * 设置背景图
     * @param background
     */
    public void setBackground(Drawable background){
        setBackground(background);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
        Log.d(TAG, "onMeasure  viewWidth = " + viewWidth + " ,viewHeight = " + viewHeight);
        centerPoint = new Point(viewWidth / 2f,viewHeight / 2f);
        radius = Math.min(viewWidth,viewHeight) / 2f;
    }

    private class Point{
        //x、y坐标
        public float x,y;
        Point(float x,float y){
            this.x = x;
            this.y = y;
        }
    }
}
