package com.cc.musiclist.fragment;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
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
import com.cc.musiclist.adapter.PlayListApapter;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.util.MLog;
import com.cc.musiclist.util.StringUtil;
import com.cc.musiclist.util.ToastUtil;
import com.cc.musiclist.util.TranslateUtil;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangyu on 2016-07-01 10:51.
 */
public class PlayListFragment extends Fragment {
    private static final String TAG = "PlayListFragment";
    private View rootView;
    private RecyclerView recyclerView;
    private PlayListApapter rAdapter;
    private TranslateUtil tu;
    private MediaPlayManager mediaPlayManager;
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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_play_list);
        //参数：context,横向或纵向滑动，是否颠倒显示数据
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    private void initResources() {
        list = MediaPlayManager.getInstance().getPlayLists();
        rAdapter = new PlayListApapter();
        rAdapter.setList(list);
        rAdapter.setOnClickListener(onClickListener);

        tu = new TranslateUtil();
        mediaPlayManager = MediaPlayManager.getInstance();
    }

    public void scrollToNowPlay() {
        recyclerView.scrollToPosition(mediaPlayManager.getNowPlayPositionInList());
    }

    public void scrollToPosition(int position) {
        recyclerView.scrollToPosition(position);
    }

    private void initViewState() {
        recyclerView.setAdapter(rAdapter);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     * 使用itemTouchHelper完成拖拽效果
     * 由于拖拽与下拉刷新有冲突，所以暂时不建议放在一起使用
     */
    private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            return makeMovementFlags(dragFlag, 0);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

            int from = viewHolder.getAdapterPosition();
            int to = target.getAdapterPosition();
            Log.d(TAG, "onMove  from = " + from + "   ,to = " + to);
            Collections.swap(list, from, to);
            rAdapter.setList(list);
            rAdapter.notifyItemMoved(from, to);

            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            PlayListApapter.ViewHolder holder = (PlayListApapter.ViewHolder) viewHolder;

            //被选中时，改背景色
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.harf_transparent_mediumturquoise));
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            PlayListApapter.ViewHolder holder = (PlayListApapter.ViewHolder) viewHolder;
            holder.itemContainer.setBackgroundColor(getResources().getColor(R.color.whitesmoke));

            mediaPlayManager.refreshNowPlayPosition();
            rAdapter.notifyDataSetChanged();

//            Log.i(TAG,mediaPlayManager.playListToString());
        }
    });


    private PlayListApapter.RecyclerViewOnClickListener onClickListener = new PlayListApapter.RecyclerViewOnClickListener() {
        @Override
        public void onItemClick(int position) {
            if (position < list.size()) {
                mediaPlayManager.setNowPlayFile(list.get(position));
                mediaPlayManager.prepare();
                mediaPlayManager.start();
                rAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void deleteOnclick(int position) {
            mediaPlayManager.deleteSong(position);
            rAdapter.notifyDataSetChanged();
            mediaPlayManager.savePlayList();
            ToastUtil.showToast("已删除歌曲", 0);
        }
    };

    public void adapterNotifyDataChange() {
        rAdapter.notifyDataSetChanged();
    }

    /**
     * 设置搜索目标位置并更新列表状态
     * @param position
     */
    public void searchResultAt(int position) {
        rAdapter.searchTargetPosition = position;
        rAdapter.notifyDataSetChanged();
        if(position >= 0 && position < list.size())
            scrollToPosition(position);
    }
}
