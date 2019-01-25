package com.sz.china.testmoudule;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.sz.china.testmoudule.view.DragView1;

/**
 * Created by zhangyu on 2018/9/3.
 */

public class ConstraintLayoutDemoAct extends Activity implements SensorEventListener {
    private static final String TAG = "ConstraintLayoutDemo";
    private SensorManager mSensorManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraintlayout_demo);
        initSensor();

        DragView1 dragView = (DragView1) findViewById(R.id.drag_view);
    }

    private void initSensor() {
        mSensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor lightSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); // 获取光线传感器
        if (lightSensor != null) { // 光线传感器存在时
            mSensorManager.registerListener(this, lightSensor,
                    SensorManager.SENSOR_DELAY_NORMAL); // 注册事件监听
        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        for (int i = 0;i < event.values.length;i++) {
            Log.d(TAG, "event["+i+"]: "+event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
