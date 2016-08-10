package com.sz.china.testmoudule.util;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.sz.china.testmoudule.application.BaseApplication;


/**
 * Created by Administrator on 2016/8/4.
 */
public class SystemUtil {
    /**
     * 获取手机的imei.（手机设备ID）
     *
     * @return
     */
    public static String getDeviceId() {
        String imei = "";
        TelephonyManager telephonyManager = null;
        try {
            telephonyManager = (TelephonyManager) BaseApplication.context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imei;
    }
}
