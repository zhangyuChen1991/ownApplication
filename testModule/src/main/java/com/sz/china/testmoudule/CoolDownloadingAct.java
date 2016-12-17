package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.cc.library.view.CoolDownloading;

/**
 * Created by zhangyu on 2016/12/17.
 */

public class CoolDownloadingAct extends Activity implements View.OnClickListener{

    @ViewInject(R.id.acd_cool_downloading)
    CoolDownloading coolDownloading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_downloading);
        ViewInjectUtil.injectView(this);

        coolDownloading.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.acd_cool_downloading:
                coolDownloading.startCircleToLinePathAnim();
                break;
        }
    }
}
