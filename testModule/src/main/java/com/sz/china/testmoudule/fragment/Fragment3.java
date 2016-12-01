package com.sz.china.testmoudule.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.util.ToastUtil;

/**
 * Created by zhangyu on 2016/8/24.
 */
public class Fragment3 extends Fragment {
    private static final String TAG = "Fragment3";
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.ft3, container, false);
        rootView.findViewById(R.id.ft3_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast("container click",0);
            }
        });

//        rootView.findViewById(R.id.ft3_child).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtil.showToast("child click",0);
//            }
//        });
        return rootView;
    }
}
