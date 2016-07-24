package com.sz.china.testmoudule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.google.zxing.client.android.ViewfinderView;


/**
 * Created by zhangyu on 2016/7/23 09:28.
 */
public class TestScanAcy extends Activity implements View.OnClickListener{

    private static final int REQUEST_CODE = 220;
    private TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);

        result = (TextView) findViewById(R.id.tv);
        findViewById(R.id.parent).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null != data && requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    result.setText(data.getStringExtra(Intents.Scan.RESULT));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        callCapture(null);
    }

    private void callCapture(String characterSet) {

        Intent intent = new Intent();
        intent.setAction(Intents.Scan.ACTION);
        // intent.putExtra(Intents.Scan.MODE, Intents.Scan.QR_CODE_MODE);
        intent.putExtra(Intents.Scan.CHARACTER_SET, characterSet);
        intent.putExtra(Intents.Scan.WIDTH, 700);
        intent.putExtra(Intents.Scan.HEIGHT, 700);

        // intent.putExtra(Intents.Scan.PROMPT_MESSAGE, "type your prompt message");
        intent.setClass(this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
        /**
         *
         //设置扫描控件的各个颜色
         intent.putExtra(ViewfinderView.MaskColor, R.color.black);
         intent.putExtra(ViewfinderView.ResultColor, R.color.green);
         intent.putExtra(ViewfinderView.ResultPointColor, R.color.red);
         intent.putExtra(ViewfinderView.LaserColor, R.color.yellow);
         **/
    }
}
