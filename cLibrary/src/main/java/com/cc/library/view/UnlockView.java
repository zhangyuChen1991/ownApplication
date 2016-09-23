package com.cc.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * 图案解锁view
 * Created by zhangyu on 2016-07-15 15:05.
 */
public class UnlockView extends View {
    private static final String TAG = "UnlockView";
    //view宽高
    private float width, height;
    //平均宽高(分三份)
    private float averageWidth, averageHeight;
    //九个点的位置数据,从左到右、从上到下 123...789
    Location[] locations = new Location[9];
    //圆圈半径
    private float radius;
    //绘制密码
    private int[] drawingPwd = new int[9];
    //正确的密码
    private int[] rightPwd;
    //画笔
    private Paint whitePaint, cyanPaint;
    //已经绘制过了的点个数
    private int drawedNumber;
    //当前正被触摸的点
    private Location nowTouchedPosition = new Location();
    //监听
    private UnlockListener unlockListener;


    public void setUnlockListener(UnlockListener unlockListener) {
        this.unlockListener = unlockListener;
    }

    public UnlockView(Context context) {
        super(context);
        init();
    }

    public UnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnlockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        ViewTreeObserver vto = getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                height = getHeight();
                width = getWidth();
                Log.d(TAG, "width = " + width + "  ,height = " + height);
                averageWidth = width / 3f;
                averageHeight = height / 3f;
                radius = averageHeight > averageWidth ? averageWidth / 5f : averageHeight / 5f;

                initLocation();
                invalidate();
            }
        });

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setColor(Color.parseColor("#ffffff"));
        whitePaint.setStyle(Paint.Style.STROKE);

        cyanPaint = new Paint();
        cyanPaint.setAntiAlias(true);
        cyanPaint.setColor(Color.parseColor("#4169E1"));
        cyanPaint.setStyle(Paint.Style.STROKE);

        initDrawingPwd();
    }

    private void drawStart() {
        drawedNumber = 0;
    }

    private void drawOver() {
        //debug
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < drawingPwd.length; i++) {
            sb.append(drawingPwd[i] + ",");
        }
        Log.i(TAG, "drawingPwd:" + sb.toString());

        initLocation();
        initDrawingPwd();
        drawedNumber = 0;
        invalidate();
    }

    /**
     * 初始化绘制密码
     */
    private void initDrawingPwd() {
        for (int i = 0; i < 9; i++) {
            drawingPwd[i] = -1;
        }
    }

    /**
     * 初始化九个点坐标
     */
    private void initLocation() {
        for (int i = 0; i < 9; i++) {
            locations[i] = new Location();
            locations[i].deawed = false;

            //纵向1、2、3列x坐标
            if (i % 3 == 0) {
                locations[i].x = averageWidth * 0.5f;
            } else if (i % 3 == 1) {
                locations[i].x = averageWidth * 1.5f;
            } else if (i % 3 == 2) {
                locations[i].x = averageWidth * 2.5f;
            }

            //横向1、2、3排y坐标
            if (i / 3 == 0) {
                locations[i].y = averageHeight * 0.5f;
            } else if (i / 3 == 1) {
                locations[i].y = averageHeight * 1.5f;
            } else if (i / 3 == 2) {
                locations[i].y = averageHeight * 2.5f;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            if (!locations[i].deawed) {//没被画
                whitePaint.setStrokeWidth(4);
                canvas.drawPoint(locations[i].x, locations[i].y, whitePaint);
                whitePaint.setStrokeWidth(1.5f);
                canvas.drawCircle(locations[i].x, locations[i].y, radius, whitePaint);
            } else {//被画过了
                cyanPaint.setStrokeWidth(8);
                canvas.drawPoint(locations[i].x, locations[i].y, cyanPaint);
                cyanPaint.setStrokeWidth(3f);
                canvas.drawCircle(locations[i].x, locations[i].y, radius, cyanPaint);
            }

            int lastestDrawedPoint = -1;
            if (drawedNumber > 0)
                lastestDrawedPoint = drawingPwd[drawedNumber - 1];
            if (lastestDrawedPoint != -1) {
                Location lastestDrawedLocation = locations[lastestDrawedPoint];//最新一个被选中的点
                cyanPaint.setStrokeWidth(3f);
                canvas.drawLine(lastestDrawedLocation.x, lastestDrawedLocation.y, nowTouchedPosition.x, nowTouchedPosition.y, cyanPaint);
            }

            if (drawedNumber > 1) {
                for (int j = 0; j < drawedNumber - 1; j++) {
                    cyanPaint.setStrokeWidth(3f);
                    canvas.drawLine(locations[drawingPwd[j]].x, locations[drawingPwd[j]].y, locations[drawingPwd[j + 1]].x, locations[drawingPwd[j + 1]].y, cyanPaint);
                }
            }
        }
        super.onDraw(canvas);
    }

    float moveX, moveY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawStart();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = event.getX();
                moveY = event.getY();

                dealPosition(moveX, moveY);
                break;
            case MotionEvent.ACTION_UP:

                if (unlockListener != null) {
                    unlockListener.drawOver(drawingPwd);
                    if (verificationPwd(rightPwd)) {
                        unlockListener.isPwdRight(true);
                    } else {
                        unlockListener.isPwdRight(false);
                    }
                }
                drawOver();
                break;
        }
        return true;
    }

    private void dealPosition(float nowX, float nowY) {
        nowTouchedPosition.x = nowX;
        nowTouchedPosition.y = nowY;

        int nowTouched = getWhichOneBeTouched(nowX, nowY);
        if (nowTouched != -1) {//触摸到了点上

            if (!locations[nowTouched].deawed) {//如果这点没被触摸过
                drawingPwd[drawedNumber] = nowTouched;      //记录密码
                drawedNumber++;     //被触摸点数+1
                Log.v(TAG, "nowTouched " + nowTouched + "  ,drawedNumber = " + drawedNumber);

            }
            locations[nowTouched].deawed = true;
        }
        invalidate();
    }

    private class Location {
        public float x = -1, y = -1;
        public boolean deawed;//是否被画过了
    }

    /**
     * 获取被触摸到的点
     *
     * @param x 坐标x点
     * @param y 坐标y点
     * @return 被触摸的坐标点位置 或者-1
     */
    private int getWhichOneBeTouched(float x, float y) {
        for (int i = 0; i < locations.length; i++) {

            double lPowX = Math.pow(Math.abs(x - locations[i].x), 2);
            double lPowY = Math.pow(Math.abs(y - locations[i].y), 2);

            if (Math.sqrt(lPowX + lPowY) < radius)
                return i;
        }
        return -1;
    }

    /**
     * 校验密码是否正确
     *
     * @param rightPwd 正确的密码
     * @return 正确返回true 否则返回false
     */
    public boolean verificationPwd(int[] rightPwd) {
        if (rightPwd == null)
            return false;

        for (int i = 0; i < rightPwd.length; i++) {
            if (rightPwd[i] != drawingPwd[i])
                return false;
        }

        return true;
    }

    /**
     * 获取当前绘制的密码
     *
     * @return
     */
    public int[] getDrawedPwd() {
        return drawingPwd;
    }

    /**
     * 设置正确的密码
     *
     * @param rightPwd
     */
    public void setRightPwd(int[] rightPwd) {
        this.rightPwd = rightPwd;
    }

    //监听接口
    public interface UnlockListener {
        public void drawOver(int[] pwd);
        public void isPwdRight(boolean isRight);
    }
}
