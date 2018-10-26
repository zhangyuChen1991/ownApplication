package com.cc.library.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
            locations[i].drawed = false;

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
            if (!locations[i].drawed) {//没被画
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

            if (!locations[nowTouched].drawed) {//如果这点没被触摸过
                int passThroughPoint = getPassThroughPoint(getLastPwNumber(),nowTouched);
                if (passThroughPoint != -1 && !locations[passThroughPoint].drawed){//被轨迹经过的店没被触摸过
                    drawingPwd[drawedNumber] = passThroughPoint;//记录经过的点进入密码
                    drawedNumber++;     //被触摸点数+1
                    locations[passThroughPoint].drawed = true;
                }
                drawingPwd[drawedNumber] = nowTouched;      //记录密码
                drawedNumber++;     //被触摸点数+1
                Log.v(TAG, "nowTouched " + nowTouched + "  ,drawedNumber = " + drawedNumber);

            }
            locations[nowTouched].drawed = true;
        }
        invalidate();
    }

    private class Location {
        public float x = -1, y = -1;
        public boolean drawed;//是否被画过了
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

    /**
     * 获取已绘制的最后一个密码数字
     * @return
     */
    private int getLastPwNumber(){
        for (int i = 0;i < drawingPwd.length;i++){
            if (drawingPwd[i] == -1 && i > 0){
                return drawingPwd[i - 1];
            }
        }
        return -1;
    }
    /**
     * 获取两个点之间经过的点
     * @param startPoint
     * @param endPoint
     * @return
     */
    private int getPassThroughPoint(int startPoint,int endPoint){
        int[] pGroup1 = {0,2};
        int[] pGroup2 = {0,8};
        int[] pGroup3 = {0,6};
        int[] pGroup4 = {1,7};
        int[] pGroup5 = {2,6};
        int[] pGroup6 = {2,8};
        int[] pGroup7 = {3,5};
        int[] pGroup8 = {6,8};
        if ((pGroup1[0] == startPoint && pGroup1[1] == endPoint)
                ||(pGroup1[1] == startPoint && pGroup1[0] == endPoint)){
            return 1;
        }
        if ((pGroup2[0] == startPoint && pGroup2[1] == endPoint)
                ||(pGroup2[1] == startPoint && pGroup2[0] == endPoint)){
            return 4;
        }
        if ((pGroup3[0] == startPoint && pGroup3[1] == endPoint)
                ||(pGroup3[1] == startPoint && pGroup3[0] == endPoint)){
            return 3;
        }
        if ((pGroup4[0] == startPoint && pGroup4[1] == endPoint)
                ||(pGroup4[1] == startPoint && pGroup4[0] == endPoint)){
            return 4;
        }
        if ((pGroup5[0] == startPoint && pGroup5[1] == endPoint)
                ||(pGroup5[1] == startPoint && pGroup5[0] == endPoint)){
            return 4;
        }
        if ((pGroup6[0] == startPoint && pGroup6[1] == endPoint)
                ||(pGroup6[1] == startPoint && pGroup6[0] == endPoint)){
            return 5;
        }
        if ((pGroup7[0] == startPoint && pGroup7[1] == endPoint)
                ||(pGroup7[1] == startPoint && pGroup7[0] == endPoint)){
            return 4;
        }
        if ((pGroup8[0] == startPoint && pGroup8[1] == endPoint)
                ||(pGroup8[1] == startPoint && pGroup8[0] == endPoint)){
            return 7;
        }
        return -1;
    }
    //监听接口
    public interface UnlockListener {
        public void drawOver(int[] pwd);
        public void isPwdRight(boolean isRight);
    }
}
