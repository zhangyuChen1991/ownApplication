package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * SwipeRefreshLayout Demo
 * Created by Administrator on 2016/8/9.
 */
public class SwipeRefreshLayoutAct extends Activity {
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh_layout);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);  // ！！注意：swipeRefreshLayout布局内必须是一个可滑动的View比如ListView或者ScrollView
        swipeRefreshLayout.setColorSchemeResources(R.color.green,R.color.red);      //loading圈的颜色，最多四种
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);     //设置滑动监听
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                swipeRefreshLayout.setRefreshing(false);//设置正在刷新的状态，即是否依旧转圈，false让圈消失
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };
}

