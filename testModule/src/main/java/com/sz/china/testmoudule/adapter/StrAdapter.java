package com.sz.china.testmoudule.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.base.BaseRecyclerViewAdapter;
import com.sz.china.testmoudule.bean.AdapterBean;


/**
 * Created by zhangyu on 2019/1/25.
 */
public class StrAdapter extends BaseRecyclerViewAdapter<AdapterBean> {
    public StrAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.mTitleTv.setText(mDatas.get(i).str);
        holder.mContentTv.setText(mDatas.get(i).str);
        if (mDatas.get(i).isFirst) {
            holder.mTitleTv.setVisibility(View.VISIBLE);
        }else{
            holder.mTitleTv.setVisibility(View.GONE);
        }
    }

    public class ViewHolder extends BaseRecyclerViewHolder {

        public TextView mTitleTv;
        public TextView mContentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.title);
            mContentTv = (TextView) itemView.findViewById(R.id.content);
            mTitleTv.setVisibility(View.GONE);
        }
    }
}
