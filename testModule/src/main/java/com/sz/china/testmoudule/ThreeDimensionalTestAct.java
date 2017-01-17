package com.sz.china.testmoudule;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.view.ThreeDimensionalView;

/**
 * Created by zhangyu on 2017/1/17.
 */

public class ThreeDimensionalTestAct extends Activity {
    private static final String TAG = "TDAct";

    @ViewInject(R.id.three_d_view)
    private ThreeDimensionalView tdView;
    @ViewInject(R.id.atdv_seek_bar)
    private SeekBar seekBar;

    private int max = 90;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_dimensional_view);
        ViewInjectUtil.injectView(this);

        initView();
    }

    private void initView() {
        seekBar.setMax(max);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d(TAG, "progress = " + progress);
            tdView.setRotateDegreeX(progress);
            tdView.setDy((float) progress / (float) max * tdView.getBitmapHeight());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}
