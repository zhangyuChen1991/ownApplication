package com.sz.china.testmoudule;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.enrique.stackblur.JavaBlurProcess;

/**
 * Created by zhangyu on 2016/11/24.
 */

public class BlurDemo extends Activity implements View.OnClickListener {
    private static final String TAG = "BlurDemo";
    private Button btn;
    private SeekBar seekBar;
    private ImageView imageView;
    private JavaBlurProcess blurProcess = new JavaBlurProcess();
    private Bitmap imageBitmap;
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_demo);

        initView();
    }

    private void initView() {
        seekBar = (SeekBar) findViewById(R.id.abd_seek_bar);
        imageView = (ImageView) findViewById(R.id.abd_imageview);
        btn = (Button) findViewById(R.id.abd_btn);

        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        btn.setOnClickListener(this);
        initImageBitmap();
    }

    private void initImageBitmap(){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        imageBitmap = drawable.getBitmap();
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            Log.d(TAG, "imageBitmap = " + imageBitmap + "  ,progress = " + seekBar.getProgress());

            Bitmap blurBitmap = blurProcess.blur(imageBitmap, seekBar.getProgress());
            imageView.setImageBitmap(blurBitmap);
        }
    };

    @Override
    public void onClick(View v) {
        if(flag) {
            imageView.setImageResource(R.drawable.ig3);
            flag = false;
        }
        else {
            imageView.setImageResource(R.drawable.ig2);
            flag = true;
        }
        initImageBitmap();

    }
}
