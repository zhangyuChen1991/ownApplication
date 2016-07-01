package com.cc.musiclist.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cc.musiclist.R;

/**
 * Created by zhangyu on 2016-07-01 10:51.
 */
public class PlayListFragment extends Fragment {
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        rootView = inflater.inflate(R.layout.play_list_fragment, container,false);
        return rootView;
    }
}
