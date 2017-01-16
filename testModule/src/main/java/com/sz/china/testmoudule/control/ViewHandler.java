package com.sz.china.testmoudule.control;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.bean.UserBean;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by zhangyu on 2017/1/12.
 */

public class ViewHandler {
    public static final String TAG = "ViewHandler";

    /**
     * dataBinding 绑定事件
     *
     * @param view
     */

    public void onButton2Click(View view) {
        ToastUtil.showToast("button2 click", 0);
        Log.d(TAG, "view.getId() = " + view.getId() + "  ,R.id.button2 = " + R.id.button2);
    }

}
