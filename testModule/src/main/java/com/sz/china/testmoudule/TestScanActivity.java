package com.sz.china.testmoudule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.Intents;

import edu.swu.pulltorefreshswipemenulistview.library.PullToRefreshSwipeMenuListView;
import edu.swu.pulltorefreshswipemenulistview.library.pulltorefresh.interfaces.IXListViewListener;


/**
 * 测试二维码扫描框架 以及上下拉刷新侧滑菜单listview：PullToRefreshSwipeMenuListView
 * Created by zhangyu on 2016/7/23 09:28.
 */
public class TestScanActivity extends Activity implements View.OnClickListener, IXListViewListener {

    private PullToRefreshSwipeMenuListView listView;
    private static final int REQUEST_CODE = 220;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_scan);

        initView();
    }

    private void initView() {
        listView = (PullToRefreshSwipeMenuListView) findViewById(R.id.listView);
        listView.setAdapter(new MyAdapter());
        listView.setPullRefreshEnable(true);
        listView.setPullLoadEnable(true);
        listView.setXListViewListener(this);
        result = (TextView) findViewById(R.id.tv);
        result.setText("点我扫码.");
        findViewById(R.id.parent).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (null != data && requestCode == REQUEST_CODE) {
            switch (resultCode) {
                case Activity.RESULT_OK:    //扫码结果
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
        /**
         *
         //设置扫描控件的各个颜色
         intent.putExtra(ViewfinderView.MaskColor, R.color.black);
         intent.putExtra(ViewfinderView.ResultColor, R.color.green);
         intent.putExtra(ViewfinderView.ResultPointColor, R.color.red);
         intent.putExtra(ViewfinderView.LaserColor, R.color.yellow);
         **/
    }

    @Override
    public void onRefresh() {
        new Thread(loading).start();
    }

    @Override
    public void onLoadMore() {
        new Thread(loading).start();
    }

    private void onLoadOver() {
        listView.stopRefresh();
        listView.stopLoadMore();
    }

    private Runnable loading = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(1500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onLoadOver();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv = new TextView(TestScanActivity.this);
            tv.setText("test房间都是浪费");
            return tv;
        }
    }
}
