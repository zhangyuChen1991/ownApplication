package com.sz.china.testmoudule;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cc.utilcode.utils.ClipboardUtils;
import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;
import com.sz.china.testmoudule.util.ToastUtil;


/**
 * 测试二维码扫描框架 以及上下拉刷新侧滑菜单listview：PullToRefreshSwipeMenuListView
 * Created by zhangyu on 2016/7/23 09:28.
 */
public class TestScanAct extends Activity implements View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_CODE = 220;
    private TextView result;
    private final int cameraRequestCode = 0x110;
    private String resultStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);

        initView();
    }

    private void initView() {
        result = (TextView) findViewById(R.id.tv);
//        result.setText("点我扫码.");
        result.setText("渠道: " + getChannelFromPkg(this));
        findViewById(R.id.parent).setOnClickListener(this);
        findViewById(R.id.parent).setOnLongClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null != data && requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:    //扫码结果
                    resultStr = data.getStringExtra(Intents.Scan.RESULT);
                    result.setText("扫码结果:\n\n" + resultStr);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            callCapture(null);
        else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, cameraRequestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            if (requestCode == cameraRequestCode)
                callCapture(null);
        } else
            ToastUtil.showToast("摄像头权限未获取，请开启应用摄像头权限", 0);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 设置相关参数，调起扫码页面
     *
     * @param characterSet
     */
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
    }

    @Override
    public boolean onLongClick(View v) {
        ClipboardUtils.copyText(TestScanAct.this, resultStr);
        ToastUtil.show(this, "已复制扫码结果");
        return true;
    }

    private static String getChannelFromPkg(Context context) {
        String KEY_CHANNEL = "channel_id";
        ApplicationInfo info = null;
        try {
            info = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bundle bundle = info != null ? info.metaData : null;
        return bundle != null ? bundle.getString(KEY_CHANNEL) : null;
    }
}
