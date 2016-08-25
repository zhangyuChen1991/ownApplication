package com.cc.musiclist.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.R;
import com.cc.musiclist.util.DisplayUtils;

/**
 * Created by zhangyu on 2016-06-30 17:01.
 */
public class TitleView extends RelativeLayout {
    public View markView;
    private TextView title1, title2;

    private TitleViewCallBack callBack;

    private int nowChooseed = 2;

    public TitleView(Context context) {
        super(context);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private RelativeLayout.LayoutParams params;

    private void init(Context context) {
        View.inflate(context, R.layout.title_layout, this);
        markView = findViewById(R.id.mark_view);
        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);

        title1.setOnClickListener(onClickListener);
        title2.setOnClickListener(onClickListener);

        params = (RelativeLayout.LayoutParams) markView.getLayoutParams();
        params.width = DisplayUtils.getWidth() / 2;
        markView.setLayoutParams(params);
    }


    OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title1:
                    nowChooseed = 1;
                    break;
                case R.id.title2:
                    nowChooseed = 2;
                    break;
            }

            if (null != callBack)
                callBack.onSelected(nowChooseed - 1);//从0计
        }
    };


    public void setCallBack(TitleViewCallBack callBack) {
        this.callBack = callBack;
    }

    public interface TitleViewCallBack {
        public void onSelected(int position);
    }

}
