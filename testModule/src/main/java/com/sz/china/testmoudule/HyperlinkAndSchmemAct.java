package com.sz.china.testmoudule;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.cc.library.annotation.ViewInjectUtil;

/**
 * Created by chenzhangyu on 17/3/3.
 */

public class HyperlinkAndSchmemAct extends Activity {
    @ViewInject(R.id.ahs_tv1)
    TextView tv1;
    @ViewInject(R.id.ahs_tv2)
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hyperlink_schmem);
        ViewInjectUtil.injectView(this);

        tv1.setText(Html.fromHtml("内容内容内容内容内容内容内容内容内容<a href='scheme://host:8888/path?key=xxx&key2=ccdd'>电影名</a>内容内容内容内容内容内容"));
        tv1.setMovementMethod(LinkMovementMethod.getInstance());

        tv2.setText(Html.fromHtml("<a href='tel:13567486895'>打电话 </a>,<a href='smsto:18565554482'>发短信 </a>,<a href='mailto:584991843@qq.com'>发邮件 </a>,<a href='http://www.baidu.com'>Go百度</a>"));
        tv2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
