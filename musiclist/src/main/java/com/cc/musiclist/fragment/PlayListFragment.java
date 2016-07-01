package com.cc.musiclist.fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.MainActivity;
import com.cc.musiclist.R;
import com.cc.musiclist.manager.MapManager;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhangyu on 2016-07-01 10:51.
 */
public class PlayListFragment extends Fragment {
    private View rootView;
    private ListView listView;
    private MAdapter adapter;
    private TranslateUtil tu;
    private MediaPlayManager mediaPlayManager;
    private MainActivity.MHandler handler;
    private List<File> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.play_list_fragment, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        initView();
        initResources();
        initViewState();
        super.onStart();
    }

    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.listview_audio_file);
        listView.setDividerHeight(0);
        listView.setOnItemClickListener(onItemClickListener);

    }

    private void initResources() {
        list = MediaPlayManager.getInstance().getPlayLists();
        adapter = new MAdapter();
        tu = new TranslateUtil();
        mediaPlayManager = MediaPlayManager.getInstance();
        handler = ((MainActivity) getActivity()).getHandler();
    }

    private void initViewState() {
        listView.setAdapter(adapter);
    }

    private class MAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = null;
            ViewHolder holder = null;
            if (convertView == null) {
                v = View.inflate(getContext(), R.layout.dapter_view, null);
                holder = new ViewHolder();
                holder.songName = (TextView) v.findViewById(R.id.song_name);
                holder.itemContainer = (RelativeLayout) v.findViewById(R.id.container);
                holder.itemMenuIvContainer = (RelativeLayout) v.findViewById(R.id.item_menu_iv_container);
                holder.itemMenu = (LinearLayout) v.findViewById(R.id.menu_container);
                holder.addToPlayListIvContainer = (RelativeLayout) v.findViewById(R.id.item_add_iv_container);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();
            }


            return v;
        }
    }

    private class ViewHolder {
        private RelativeLayout addToPlayListIvContainer, itemMenuIvContainer;
        private TextView songName, delete;
        private RelativeLayout itemContainer;
        private LinearLayout itemMenu;
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.listview_audio_file:
                    if (position < list.size()) {
                        mediaPlayManager.setNowPlayFile(list.get(position));
                        mediaPlayManager.prepare();
                        mediaPlayManager.start();
                    }
                    break;
            }
        }
    };

}
