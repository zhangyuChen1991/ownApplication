package com.sz.china.testmoudule.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * RecyclerView Adapter
 * 封装了点击事件监听和普通列表数据的绑定
 *
 * 如有复杂列表的需求可覆写相关方法，或者另外自己重新实现Adapter  (比如同时包含多类数据、多种UI样式、列表嵌套)
 * Created by zhangyu on 2018/4/16.
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<T> mDatas;
    private boolean mHaveHeader = false;

    public BaseRecyclerViewAdapter(Context context){
        mContext = context;
        mDatas = new ArrayList<>();
    }
    public ItemViewOnClickListener mItemViewOnClickListener;

    public void setItemFooterViewOnClickListener(ItemFooterViewOnClickListener itemFooterViewOnClickListener) {
        mItemFooterViewOnClickListener = itemFooterViewOnClickListener;
    }

    public ItemFooterViewOnClickListener mItemFooterViewOnClickListener;

    /**
     * adapter设置item内部view 的点击事件监听
     * @param itemViewOnClickListener
     */
    public void setItemViewOnClickListener(ItemViewOnClickListener<T> itemViewOnClickListener) {
        mItemViewOnClickListener = itemViewOnClickListener;
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public void setDatas(List<T> datas) {
        mDatas = datas;
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void addAll(Collection<? extends T> collection) {
        if (collection != null && collection.size() > 0) {
            mDatas.addAll(collection);
        }
        int dataCount = collection == null ? 0 : collection.size();
        notifyItemRangeInserted(mDatas.size() - dataCount + 1, dataCount);
    }

    public void clearAll() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    public void setHaveHeader(boolean haveHeader) {
        mHaveHeader = haveHeader;
    }

    public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
        private View.OnClickListener mOnClickListener;
        private SparseArray<View> mViews;

        /**
         * item 内部的View设置点击监听
         * @param view
         */
        protected void setClickListener(View view){
            view.setOnClickListener(mOnClickListener);
        }

        public BaseRecyclerViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            mOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int targetPosition = getAdapterPosition();
                    if(mHaveHeader)
                        targetPosition--;
                    if(null != mItemViewOnClickListener){
                        if(null != mDatas && targetPosition < mDatas.size() && targetPosition >= 0){
                            mItemViewOnClickListener.OnItemViewOnClick(v,mDatas.get(targetPosition),BaseRecyclerViewHolder.this);
                        } else {
                            // do nothing
                        }
                    }

                    if(null != mItemFooterViewOnClickListener){
                        if(null != mDatas && targetPosition == mDatas.size()){
                            mItemFooterViewOnClickListener.OnItemFooterViewOnClick(v,BaseRecyclerViewHolder.this);
                        } else {
                            // do nothing
                        }
                    }
                }
            };
        }

        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        public void setText(int viewId, int resId) {
            TextView view = getView(viewId);
            view.setText(resId);
        }

        public void setText(int viewId, String value) {
            TextView view = getView(viewId);
            view.setText(value);
        }

        public void setText(int viewId, CharSequence value) {
            TextView view = getView(viewId);
            view.setText(value);
        }

        public void setTextColor(int viewId, int colorId) {
            TextView view = getView(viewId);
            view.setTextColor(colorId);
        }

        public void setImageResource(int viewId, int resId) {
            ImageView view = getView(viewId);
            view.setImageResource(resId);
        }

        public void setImageDrawable(int viewId, Drawable drawable) {
            ImageView view = getView(viewId);
            view.setImageDrawable(drawable);
        }

//        public void setImageUrl(int viewId, String imgUrl) {
//            ImageView view = getView(viewId);
//            //Glide.with(mContext).load(imgUrl).into(view);
//        }

        public void setViewVisible(int viewId, boolean visible) {
            View view = getView(viewId);
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }

        public void setViewOnClickListener(int viewId, View.OnClickListener listener) {
            View view = getView(viewId);
            view.setOnClickListener(listener);
        }
    }

    public interface ItemViewOnClickListener<T>{
        void OnItemViewOnClick(View view, T data, RecyclerView.ViewHolder viewHolder);
    }
    public interface ItemFooterViewOnClickListener<T>{
        void OnItemFooterViewOnClick(View view, RecyclerView.ViewHolder viewHolder);
    }
}
