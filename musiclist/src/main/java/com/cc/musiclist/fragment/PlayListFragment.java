package com.cc.musiclist.fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.MainActivity;
import com.cc.musiclist.R;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.util.MLog;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.util.List;

/**
 * Created by zhangyu on 2016-07-01 10:51.
 */
public class PlayListFragment extends Fragment {
    private static final String TAG = "PlayListFragment";
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initView();
        initResources();
        initViewState();
        super.onActivityCreated(savedInstanceState);
    }


    private void initView() {
        listView = (ListView) rootView.findViewById(R.id.listview_play_list);
        MLog.d(TAG, "listView = " + listView);
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

    public void scrollToNowPlay() {
        listView.setSelection(mediaPlayManager.getNowPlayPositionInList());
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
                v = View.inflate(getContext(), R.layout.play_list_dapter, null);
                holder = new ViewHolder();
                holder.itemContainer = (RelativeLayout) v.findViewById(R.id.container);
                holder.songName = (TextView) v.findViewById(R.id.song_name);
                holder.delete = (RelativeLayout) v.findViewById(R.id.item_delete_iv_container);

                v.setTag(holder);
            } else {
                v = convertView;
                holder = (ViewHolder) v.getTag();
            }

            if (position < list.size()) {
                String sName = list.get(position).getName();
                holder.songName.setText(StringUtil.subPostfix(sName));
            }

            if (position == mediaPlayManager.getNowPlayPositionInList()) {
                holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.mediumturquoise));
            } else
                holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.whitesmoke));

            onItemViewClick(position, holder.delete);
            return v;
        }
    }

    private class ViewHolder {
        private RelativeLayout delete, itemContainer;
        private TextView songName;

    }

    private void onItemViewClick(final int position, View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.item_delete_iv_container://条目菜单
                        mediaPlayManager.deleteSong(position);
                        adapter.notifyDataSetChanged();
                        mediaPlayManager.savePlayList();
                        ToastUtil.showToast("已删除歌曲", 0);
                        break;
                }
            }
        });
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.listview_play_list:
                    if (position < list.size()) {
                        mediaPlayManager.setNowPlayFile(list.get(position));
                        mediaPlayManager.prepare();
                        mediaPlayManager.start();
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    public void adapterNotifyDataChange() {
        adapter.notifyDataSetChanged();
    }
}
