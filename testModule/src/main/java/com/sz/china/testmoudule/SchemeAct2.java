package com.sz.china.testmoudule;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by chenzhangyu on 17/3/3.
 */

public class SchemeAct2 extends Activity {

    @ViewInject(R.id.ahs_tv1)
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyperlink_schmem);
        ViewInjectUtil.injectView(this);

        Uri uri = getIntent().getData();
        if (uri != null) {
            String value = uri.getQueryParameter("key");
            String value2 = uri.getQueryParameter("key2");

            tv1.setText("key:"+value + " ,key2:" +value2);
        }else{
            ToastUtil.showToast("uri == null",0);
        }
    }

}
