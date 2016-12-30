package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by zhangyu on 2016/12/29.
 */

public class OpenGLAct extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao);
         TextView tv = (TextView) findViewById(R.id.agd_text);
        tv.setText("妈蛋 还没开始入门呢。。");

    }
}
