package com.sz.china.testmoudule.fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sz.china.testmoudule.R;

/**
 * Created by zhangyu on 2016/8/24.
 */
public class Fragment2 extends Fragment implements View.OnClickListener {
    private View rootView, topView, bottomView;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.ft2, container, false);
        topView = rootView.findViewById(R.id.top_view);
        bottomView = rootView.findViewById(R.id.bottom_view);
        btn = (Button) rootView.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        bottomView.setTag(false);
        return rootView;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                boolean nowPositionOut = (boolean) bottomView.getTag();
                if (nowPositionOut) {
                    startAnim(false);
                    bottomView.setTag(false);
                } else {
                    startAnim(true);
                    bottomView.setTag(true);
                }
                break;
        }
    }

    /**
     * 开始移动动画
     *
     * @param translateOut true移出/false移入
     */
    private void startAnim(boolean translateOut) {
        float currY = bottomView.getTranslationY();
        ObjectAnimator animator = null;
        if (translateOut)
            animator = ObjectAnimator.ofFloat(bottomView, "translationY", currY, currY + bottomView.getHeight());
        else
            animator = ObjectAnimator.ofFloat(bottomView, "translationY", currY, currY - bottomView.getHeight());
        animator.setDuration(300);
        animator.start();
    }
}
