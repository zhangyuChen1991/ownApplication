package com.sz.china.testmoudule;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
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
    @ViewInject(R.id.three_d_view1)
    private ThreeDimensionalView tdView1;
    @ViewInject(R.id.three_d_view2)
    private ThreeDimensionalView tdView2;
    @ViewInject(R.id.three_d_view3)
    private ThreeDimensionalView tdView3;
    @ViewInject(R.id.three_d_view4)
    private ThreeDimensionalView tdView4;
    @ViewInject(R.id.three_d_view5)
    private ThreeDimensionalView tdView5;
    @ViewInject(R.id.three_d_view6)
    private ThreeDimensionalView tdView6;
    @ViewInject(R.id.three_d_view8)
    private ThreeDimensionalView tdView8;
    @ViewInject(R.id.three_d_view9)
    private ThreeDimensionalView tdView9;


    @ViewInject(R.id.atdv_seek_bar)
    private SeekBar seekBar;
    @ViewInject(R.id.atdv_seek_bar1)
    private SeekBar seekBar1;
    @ViewInject(R.id.atdv_seek_bar2)
    private SeekBar seekBar2;

    private int max = 90;
    private int max1 = 210;
    private int max2 = 180;

    BitmapDrawable bgDrawable1;

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

        seekBar1.setMax(max1);
        seekBar1.setOnSeekBarChangeListener(onSeekBarChangeListener1);

        seekBar2.setMax(max2);
        seekBar2.setOnSeekBarChangeListener(onSeekBarChangeListener2);

        bgDrawable1 = (BitmapDrawable) getResources().getDrawable(R.drawable.img1);
        tdView.addImageBitmap(bgDrawable1.getBitmap());
        tdView1.addImageBitmap(bgDrawable1.getBitmap());
        tdView2.addImageBitmap(bgDrawable1.getBitmap());
        tdView3.addImageBitmap(bgDrawable1.getBitmap());
        tdView4.addImageBitmap(bgDrawable1.getBitmap());
        tdView5.addImageBitmap(bgDrawable1.getBitmap());
        tdView6.addImageBitmap(bgDrawable1.getBitmap());
        tdView8.addImageBitmap(bgDrawable1.getBitmap());
        tdView9.addImageBitmap(bgDrawable1.getBitmap());

    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d(TAG, "progress = " + progress);


            tdView.setDirection(1);
            tdView.setPartNumber(3);
            tdView.setRollMode(ThreeDimensionalView.RollMode.SepartConbine);
            tdView.setRotateDegree(progress);

            tdView1.setDirection(0);
            tdView1.setRollMode(ThreeDimensionalView.RollMode.Whole3D);
            tdView1.setRotateDegree(progress);

            tdView2.setDirection(1);
            tdView2.setRollMode(ThreeDimensionalView.RollMode.Whole3D);
            tdView2.setRotateDegree(progress);

            tdView3.setDirection(0);
            tdView3.setPartNumber(3);
            tdView3.setRollMode(ThreeDimensionalView.RollMode.SepartConbine);
            tdView3.setRotateDegree(progress);

            tdView4.setDirection(1);
            tdView4.setPartNumber(3);
            tdView4.setRollMode(ThreeDimensionalView.RollMode.SepartConbine);
            tdView4.setRotateDegree(progress);


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener1 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            tdView8.setDirection(1);
            tdView8.setPartNumber(5);
            tdView8.setRollMode(ThreeDimensionalView.RollMode.RollInTurn);
            tdView8.setRotateDegree(progress);
            tdView8.setAxisY((float) progress / (float) max * tdView8.getWholeBitmapHeight());

            tdView9.setDirection(0);
            tdView9.setPartNumber(5);
            tdView9.setRollMode(ThreeDimensionalView.RollMode.RollInTurn);
            tdView9.setRotateDegree(progress);
            tdView9.setAxisX((float) progress / (float) max * tdView9.getWholeBitmapWidth());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener2 = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            tdView5.setDirection(0);
            tdView5.setPartNumber(5);
            tdView5.setRollMode(ThreeDimensionalView.RollMode.Jalousie);
            tdView5.setRotateDegree(progress);

            tdView6.setDirection(1);
            tdView6.setPartNumber(5);
            tdView6.setRollMode(ThreeDimensionalView.RollMode.Jalousie);
            tdView6.setRotateDegree(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
