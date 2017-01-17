package com.sz.china.testmoudule.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sz.china.testmoudule.R;

/**
 * Created by zhangyu on 2017/1/17.
 */

public class ThreeDimensionalView extends View {
    private static final String TAG = "TDAct";

    private Context context;
    private Paint paint;
    private Camera camera;
    private Matrix matrix;

    private float rotateDegreeX;

    private float dy = 0;

    private int bitmapHeight;


    public ThreeDimensionalView(Context context) {
        super(context);
        init(context);
    }

    public ThreeDimensionalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public ThreeDimensionalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        matrix = new Matrix();
        this.context = context;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        BitmapDrawable bgDrawable = (BitmapDrawable) context.getResources().getDrawable(R.drawable.img1);
        Bitmap bitmap = bgDrawable.getBitmap();
        bitmapHeight = bitmap.getHeight();

        canvas.save();
        //
        camera.save();
        camera.rotateX(-rotateDegreeX);
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-bitmap.getWidth() / 2, 0);// -bitmap.getHeight() / 2
        matrix.postTranslate(bitmap.getWidth() / 2, dy);//bitmap.getHeight() / 2
        canvas.drawBitmap(bitmap, matrix, paint);

        //
        camera.save();
        camera.rotateX((90 - rotateDegreeX));
        camera.getMatrix(matrix);
        camera.restore();

        matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight());// -bitmap.getHeight() / 2
        matrix.postTranslate(bitmap.getWidth() / 2, dy);//bitmap.getHeight() / 2
        canvas.drawBitmap(bitmap, matrix, paint);

        canvas.restore();


        /**
         * 关于这几句代码的详细解释
         * 参考drawable文件夹中的图片explain_3d_img进行阅读
         *
         * 1.camera.rotateX(rotateDegreeX);  camera有一个三维坐标系系统 这里绕X轴设置旋转角度
         * 加粗*旋转的中心点是被旋转物的中心*加粗
         *
         *
         * 2.
         * matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight());
         * matrix.postTranslate(bitmap.getWidth() / 2, dy);
         *
         * matrix原点是(0,0)点  要做绕x轴旋转操作 首先要将物体中心点移到(0,0)点(pre)，再旋转，然后移回原位置(post)  然后显示的就是旋转后的效果了
         * 但是这样操作的话，旋转中心中规中矩在物体中心
         * 如果要控制旋转中心点 则像这里的代码那样  在preTranslate的时候 适当改变参数
         * 这样便将旋转中心点设置到了图片的上边缘(向左移动0.5宽，向上移动0)  和图片的下边缘(向左移动0.5宽，向上移动整个高)
         *
         *
         */

        //画bitmap的一部分
        int averageWidth = (int) (bitmap.getWidth() / 3f);
        for (int i = 0; i < 3; i++) {
            Rect rect = new Rect(i * averageWidth, 0, (i + 1) * averageWidth, bitmap.getHeight());
            Bitmap partBitmap = getPartBitmap(bitmap, i * averageWidth, 0, rect);
            canvas.drawBitmap(partBitmap, i * (averageWidth + 20), 600, paint);
        }

    }


    public void setRotateDegreeX(float rotateDegreeX) {
        this.rotateDegreeX = rotateDegreeX;
        invalidate();
    }

    public void setDy(float dy) {
        this.dy = dy;
        invalidate();
    }


    public int getBitmapHeight() {
        Log.d(TAG, "bitmapHeight = " + bitmapHeight);
        return bitmapHeight;
    }

    /**
     * 获取bitmap的一部分
     *
     * @param bitmap 被裁减的bitmap
     * @param rect   裁剪范围 方块
     * @return 裁剪后的bitmap
     */
    private Bitmap getPartBitmap(Bitmap bitmap, int x, int y, Rect rect) {
        Bitmap bitmap1 = Bitmap.createBitmap(bitmap, x, y, rect.width(), rect.height());
        return bitmap1;
    }
}
