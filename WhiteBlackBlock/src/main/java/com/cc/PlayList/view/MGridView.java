package com.cc.PlayList.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.cc.PlayList.util.DisplayUtils;

/**
 * Created by zhangyu on 2016-06-16 15:24.
 */
public class MGridView extends GridView {
    private Context context;
    public MGridView(Context context) {
        super(context);
        init(context);
    }

    public MGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                smoothScrollBy(-DisplayUtils.dip2px(context,900),400);
                break;
        }
        return super.onTouchEvent(ev);
    }
}
