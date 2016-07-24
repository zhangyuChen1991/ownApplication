package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.cc.library.view.UnlockView;

/**
 * Created by zhangyu on 2016-07-15 14:55.
 */
public class TestUnlockViewActivity extends Activity {

    private UnlockView unlockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unlock_activity);

        unlockView = (UnlockView) findViewById(R.id.unlock_view);

//        //设置回调监听
//        unlockView.setUnlockListener(new UnlockView.UnlockListener() {
//            @Override
//            public void drawOver(int[] pwd) {//绘制完成，获取绘制的密码
//                unlockView.getDrawedPwd();
//            }
//            @Override
//            public void isPwdRight(boolean isRight) {//密码是否校验正确
//                if(isRight)
//                    Toast.makeText(TestUnlockViewActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(TestUnlockViewActivity.this,"密码错误",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        int[] pwd = {0,5,7,6};
//        unlockView.setRightPwd(pwd);    //设置密码

    }
}
