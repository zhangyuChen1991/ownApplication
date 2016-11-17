package com.cc.musiclist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cc.musiclist.R;
import com.cc.musiclist.manager.MediaPlayManager;
import com.cc.musiclist.util.StringUtil;

import java.io.File;
import java.util.List;

import static com.cc.musiclist.R.id.song_name;

/**
 * Created by zhangyu on 2016/11/17.
 */

public class PlayListApapter extends RecyclerView.Adapter<PlayListApapter.ViewHolder> {
    private static final String TAG = "PlayListApapter";
    private Context context;
    private List<File> list;
    private RecyclerViewOnClickListener recyclerViewOnClick;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG,"onCreateViewHolder");
        if(null == context)
            context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.play_list_dapter, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < list.size()) {
            String sName = list.get(position).getName();
            holder.songName.setText(StringUtil.subPostfix(sName));
        }

        if (position == MediaPlayManager.getInstance().getNowPlayPositionInList()) {
            holder.itemContainer.setBackgroundColor(context.getResources().getColor(R.color.mediumturquoise));
        } else
            holder.itemContainer.setBackgroundColor(context.getResources().getColor(R.color.whitesmoke));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG,"getItemCount:"+list.size());
        return null == list ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView songName;
        public RelativeLayout delete, itemContainer;


        public ViewHolder(View itemView) {
            super(itemView);
            songName = (TextView) itemView.findViewById(song_name);
            delete = (RelativeLayout) itemView.findViewById(R.id.item_delete_iv_container);
            itemContainer = (RelativeLayout) itemView.findViewById(R.id.container);

            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //用getAdapterPosition获取item在adapter中的位置
            if (v.getId() == itemView.getId()) {
                if (null != recyclerViewOnClick)
                    recyclerViewOnClick.onItemClick(getAdapterPosition());
            } else if (v.getId() == delete.getId()) {
                if (null != recyclerViewOnClick)
                    recyclerViewOnClick.deleteOnclick(getAdapterPosition());
            }
        }
    }

    /**
     * 设置数据
     *
     * @param list
     */
    public void setList(List<File> list) {
        this.list = list;
    }

    /**
     * 监听回调接口
     */
    public interface RecyclerViewOnClickListener {
        void onItemClick(int position);

        void deleteOnclick(int position);
    }

    public void setOnClickListener(RecyclerViewOnClickListener recyclerViewOnClick) {
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

}
