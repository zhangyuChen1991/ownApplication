package com.sz.china.testmoudule;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;
import com.sz.china.testmoudule.bean.WifiInfoBean;
import com.sz.china.testmoudule.util.WifiUtil;

import java.util.List;

/**
 * Created by zhangyu on 2016/10/17.
 */
public class WifiInfoAct extends Activity {
    private static String TAG = "WifiInfoAct";

    @ViewInject(R.id.wi_tv)
    private TextView tv;
    private WifiManager wifiManager;
    private WifiInfo currentWifiInfo;
    private WifiInfoBean info;
    private String infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_info);
        ViewInjectUtil.injectView(this);
        initResources();
        initViewState();
    }

    private void initResources() {
        info = new WifiInfoBean();
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        currentWifiInfo = wifiManager.getConnectionInfo();
        try {
            getWifiInfos();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setInfoText();
    }

    private void initViewState() {
        tv.setText(infoText);
    }

    public void getWifiInfos() throws Exception {
        List<WifiInfoBean> wifiInfos = WifiUtil.getWifiInfo();
        for (WifiInfoBean wifiInfo : wifiInfos) {
//            Log.i(TAG,wifiInfo.toString());
            if (wifiInfo.getSsid().equals(currentWifiInfo.getSSID().replace("\"",""))) {
                info = wifiInfo;
                break;
            }
        }

        info.setIp(currentWifiInfo.getIpAddress());
        info.setNetSpeed(currentWifiInfo.getLinkSpeed());
    }


    private void setInfoText() {
        StringBuffer sb = new StringBuffer("wifi名称:");
        sb.append(info.getSsid());
        sb.append("\n");
        sb.append("密码:");
        sb.append(info.getPassword());
        sb.append("\n");
        sb.append("ip地址:");
        sb.append(WifiUtil.formatIp(info.getIp()));
        sb.append("\n");
        sb.append("网速:");
        sb.append(info.getNetSpeed());
        sb.append(" Mbps");
        infoText = sb.toString();
    }
}
