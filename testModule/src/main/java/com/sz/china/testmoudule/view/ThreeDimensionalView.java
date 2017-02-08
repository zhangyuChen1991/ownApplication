package com.sz.china.testmoudule.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.sz.china.testmoudule.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2017/1/17.
 */

public class ThreeDimensionalView extends View {
    private static final String TAG = "TDAct";
    private int viewWidth, viewHeight;
    private Context context;
    private Paint paint;
    private Camera camera;
    private Matrix matrix;

    private float rotateDegree = 0;
    //todo 设置图片为 铺满 和 保持原比例 两种模式

    //X方向旋转轴   Y方向旋转轴
    private float axisX = 0, axisY = 0;
    private int wholeBitmapHeight;
    private int wholeBitmapWidth;

    private int partNumber = 1;
    private Bitmap currWholeBitmap;
    private List<Bitmap> bitmapList;
    private Bitmap[] bitmaps;
    //滚动方向:1竖直方向 其他为水平方向
    private int direction = 1;

    //滚动模式
    private RollMode rollMode = RollMode.Whole3D;
    private int currIndex = 0, nextIndex = 1;

    //滚动模式
    public enum RollMode {
        //2d滚动  3D整体滚动  尾部逐渐分离再合并   各模块依次滚动  百叶窗
        Roll2D, Whole3D, SepartConbine, RollInTurn, Jalousie;
    }


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
        bitmapList = new ArrayList<>();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        camera = new Camera();
        matrix = new Matrix();
        this.context = context;
    }


    private void initBitmaps() {
        if (null == bitmapList || bitmapList.size() <= 0)
            return;
        bitmaps = new Bitmap[partNumber];
        currWholeBitmap = bitmapList.get(currIndex);
        wholeBitmapHeight = currWholeBitmap.getHeight();
        wholeBitmapWidth = currWholeBitmap.getWidth();

        int averageWidth = (int) (currWholeBitmap.getWidth() / partNumber);
        int averageHeight = (int) (currWholeBitmap.getHeight() / partNumber);
        Bitmap partBitmap;
//        for(int i = 0;i < bitmapList.size();i++) {
        for (int j = 0; j < partNumber; j++) {
            Rect rect;
            if (rollMode != RollMode.Jalousie) {
                if (direction == 1) {//纵向分块
                    rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, currWholeBitmap.getHeight());
                    partBitmap = getPartBitmap(currWholeBitmap, j * averageWidth, 0, rect);
                } else {//横向分块
                    rect = new Rect(0, j * averageHeight, currWholeBitmap.getWidth(), (j + 1) * averageHeight);
                    partBitmap = getPartBitmap(currWholeBitmap, 0, j * averageHeight, rect);
                }
            } else {
                if (direction == 1) {//纵向分块
                    rect = new Rect(0, j * averageHeight, currWholeBitmap.getWidth(), (j + 1) * averageHeight);
                    partBitmap = getPartBitmap(currWholeBitmap, 0, j * averageHeight, rect);
                } else {//横向分块
                    rect = new Rect(j * averageWidth, 0, (j + 1) * averageWidth, currWholeBitmap.getHeight());
                    partBitmap = getPartBitmap(currWholeBitmap, j * averageWidth, 0, rect);
                }
            }

            bitmaps[j] = partBitmap;
        }
//        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == bitmapList || bitmapList.size() <= 0)
            return;
        switch (rollMode) {
            case Whole3D:
                drawRollWhole3D(canvas);
                break;
            case SepartConbine:
                drawSepartConbine(canvas);
                break;
            case RollInTurn:
                drawRollInTurn(canvas);
                break;
            case Jalousie:
                drawJalousie(canvas);
                break;
        }

        //画bitmap的一部分
//        int averageWidth = (int) (bitmap.getWidth() / 3f);
//        for (int i = 0; i < 3; i++) {
//            Rect rect = new Rect(i * averageWidth, 0, (i + 1) * averageWidth, bitmap.getHeight());
//            Bitmap partBitmap = getPartBitmap(bitmap, i * averageWidth, 0, rect);
//            canvas.drawBitmap(partBitmap, i * (averageWidth + 20), 600, paint);
//        }

    }

    /**
     * 整体翻滚
     * degree 0->90 往下翻滚或者往右翻滚
     *
     * @param canvas
     */
    private void drawRollWhole3D(Canvas canvas) {

        canvas.save();
        //
        if (direction == 1) {
            camera.save();
            camera.rotateX(-rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-currWholeBitmap.getWidth() / 2, 0);
            matrix.postTranslate(currWholeBitmap.getWidth() / 2, axisY);
            matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            //
            camera.save();
            camera.rotateX((90 - rotateDegree));
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-currWholeBitmap.getWidth() / 2, -currWholeBitmap.getHeight());
            matrix.postTranslate(currWholeBitmap.getWidth() / 2, axisY);
            matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);

        } else {
            camera.save();
            camera.rotateY(rotateDegree);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(0, -wholeBitmapHeight / 2);
            matrix.postTranslate(axisX, wholeBitmapHeight / 2);

            matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);

            //
            camera.save();
            camera.rotateY(rotateDegree - 90);
            camera.getMatrix(matrix);
            camera.restore();

            matrix.preTranslate(-wholeBitmapWidth, -wholeBitmapHeight / 2);
            matrix.postTranslate(axisX, wholeBitmapHeight / 2);
            matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
            canvas.drawBitmap(currWholeBitmap, matrix, paint);
        }
        canvas.restore();
    }


    /**
     * 纵向  头部接合  尾部分离效果
     * degree 0->90 往下翻滚 或者 往右翻滚   90->0往上翻滚 或者往翻滚
     *
     * @param canvas
     */
    private void drawSepartConbine(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap bitmap = bitmaps[i];
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            canvas.save();
            if (direction == 1) {
                //
                camera.save();
                camera.rotateX(-rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmap.getWidth() / 2, 0);
                matrix.postTranslate(bitmap.getWidth() / 2 + i * bitmapWidth, axisY);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

                //
                camera.save();
                camera.rotateX((90 - rotateDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight());
                matrix.postTranslate(bitmap.getWidth() / 2 + i * bitmapWidth, axisY);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -bitmapHeight / 2);
                matrix.postTranslate(axisX, bitmapHeight / 2 + i * bitmapHeight);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

                //
                camera.save();
                camera.rotateY(rotateDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmapWidth, -bitmapHeight / 2);
                matrix.postTranslate(axisX, bitmapHeight / 2 + i * bitmapHeight);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);
            }
            canvas.restore();
        }
    }

    /**
     * 依次翻滚
     *
     * @param canvas
     */
    private void drawRollInTurn(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap bitmap = bitmaps[i];
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            float tDegree = rotateDegree - i * 30;
            if (tDegree < 0)
                tDegree = 0;
            if (tDegree > 90)
                tDegree = 90;


            canvas.save();
            if (direction == 1) {
                float tAxisY = axisY - i * bitmapHeight / 3f;
                if (tAxisY > bitmapHeight)
                    tAxisY = bitmapHeight;
                if (tAxisY < 0)
                    tAxisY = 0;
                //
                camera.save();
                camera.rotateX(-tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmapWidth, 0);
                matrix.postTranslate(bitmapWidth + i * bitmapWidth, tAxisY);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

                //
                camera.save();
                camera.rotateX((90 - tDegree));
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmapWidth, -bitmap.getHeight());
                matrix.postTranslate(bitmapWidth + i * bitmapWidth, tAxisY);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

            } else {
                float tAxisX = axisX - i * bitmapWidth / 3f;
                if (tAxisX > bitmapWidth)
                    tAxisX = bitmapWidth;
                if (tAxisX < 0)
                    tAxisX = 0;
                camera.save();
                camera.rotateY(tDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(0, -bitmapHeight / 2);
                matrix.postTranslate(tAxisX, bitmapHeight / 2 + i * bitmapHeight);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

                //
                camera.save();
                camera.rotateY(tDegree - 90);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmapWidth, -bitmapHeight / 2);
                matrix.postTranslate(tAxisX, bitmapHeight / 2 + i * bitmapHeight);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);
            }
            canvas.restore();
        }
    }


    /**
     * 百叶窗翻页
     *
     * @param canvas
     */
    private void drawJalousie(Canvas canvas) {
        for (int i = 0; i < partNumber; i++) {
            Bitmap bitmap = bitmaps[i];
            int bitmapHeight = bitmap.getHeight();
            int bitmapWidth = bitmap.getWidth();

            canvas.save();
            //注意 百叶窗的翻转方向和其他模式是相反的  横向的时候纵翻  纵向的时候横翻
            if (direction == 1) {
                //
                camera.save();
                camera.rotateX(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight() / 2);
                matrix.postTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2 + i * bitmapHeight);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);


            } else {
                camera.save();
                camera.rotateY(rotateDegree);
                camera.getMatrix(matrix);
                camera.restore();

                matrix.preTranslate(-bitmap.getWidth() / 2, -bitmapHeight / 2);
                matrix.postTranslate(bitmap.getWidth() / 2 + i * bitmapWidth, bitmapHeight / 2);
                matrix.postScale(viewWidth / (float) wholeBitmapWidth, viewHeight / (float) wholeBitmapHeight, 0, 0);
                canvas.drawBitmap(bitmap, matrix, paint);

            }
            canvas.restore();
        }
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

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initBitmaps();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }


    public void setRotateDegree(float rotateDegree) {
        this.rotateDegree = rotateDegree;
        if (direction == 1) {
            if (rollMode == RollMode.RollInTurn)
                setAxisY((float) rotateDegree / (float) ((partNumber - 2) * 30) * currWholeBitmap.getWidth());
            else
                setAxisY((float) rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * currWholeBitmap.getWidth());
        } else {
            if (rollMode == RollMode.RollInTurn)
                setAxisX((float) rotateDegree / (float) ((partNumber - 2) * 30) * currWholeBitmap.getWidth());
            else
                setAxisX((float) rotateDegree / (float) (rollMode == RollMode.Jalousie ? 180 : 90) * currWholeBitmap.getWidth());
        }
        invalidate();
    }

    /**
     * 设置Y方向旋转轴位置
     *
     * @param axisY
     */
    public void setAxisY(float axisY) {
        this.axisY = axisY;
        invalidate();
    }

    /**
     * 设置X方向旋转轴位置
     *
     * @param axisX
     */
    public void setAxisX(float axisX) {
        this.axisX = axisX;
        invalidate();
    }

    /**
     * 设置滚动模式
     *
     * @param rollMode
     */
    public void setRollMode(RollMode rollMode) {
        this.rollMode = rollMode;
    }

    public int getWholeBitmapHeight() {
        Log.d(TAG, "wholeBitmapHeight = " + wholeBitmapHeight);
        return wholeBitmapHeight;
    }

    public int getWholeBitmapWidth() {
        return wholeBitmapWidth;
    }

    /**
     * 设置滚动方向 1竖直方向  其他为水平方向
     *
     * @param direction
     */
    public void setDirection(int direction) {
        this.direction = direction;
        initBitmaps();
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
        initBitmaps();
    }

    /**
     * 关于这几句代码的详细解释
     * 参考drawable文件夹中的图片explain_3d_img进行阅读
     * <p>
     * 1.camera.rotateX(rotateDegree);  camera有一个三维坐标系系统 这里绕X轴设置旋转角度
     * 旋转的中心点是(0,0)
     * <p>
     * 2.
     * matrix.preTranslate(-bitmap.getWidth() / 2, -bitmap.getHeight());
     * matrix.postTranslate(bitmap.getWidth() / 2, axisY);
     * <p>
     * matrix原点是(0,0)点  要做绕x轴旋转操作 首先要将物体中心点移到(0,0)点(pre)，再旋转，然后移回原位置(post)  然后显示的就是旋转后的效果了
     * 但是这样操作的话，旋转中心中规中矩在物体中心
     * 如果要控制旋转中心点 则像这里的代码那样  在preTranslate的时候 适当改变参数
     * 这样便将旋转中心点设置到了图片的上边缘(向左移动0.5宽，向上移动0)  和图片的下边缘(向左移动0.5宽，向上移动整个高)
     */

    public void addImageBitmap(Bitmap bitmap) {
        bitmapList.add(bitmap);
        initBitmaps();
        invalidate();
    }

    public void addImageBitmap(List<Bitmap> bitmaps) {
        bitmapList.addAll(bitmaps);
    }

    public void removeBitmapAt(int index) {
        bitmapList.remove(index);
    }

    public void removeBitmap(Bitmap bitmap) {
        bitmapList.remove(bitmap);
    }
}
