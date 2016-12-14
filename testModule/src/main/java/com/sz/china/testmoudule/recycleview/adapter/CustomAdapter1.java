package com.sz.china.testmoudule.recycleview.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangyu on 2016/12/14.
 */

public class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.Holder> {
    private static final String TAG = "CustomAdapter1";
    public final int HEAD = 0x1232, CONTENT = 0x1233;

    private List<Integer> data;
    private Context context;

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = null;
        if (viewType == HEAD) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header_view_viewpager, parent, false);
        } else if (viewType == CONTENT)
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recyclerview_demo, parent, false);
        Holder hodler = new Holder(view, viewType);
        return hodler;

    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (position == 0) {

            List<View> list = new ArrayList<>();
            int[] colors = {R.color.red, R.color.burlywood, R.color.yellow, R.color.green};
            for (int i = 0; i < 4; i++) {
                ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(R.layout.image_view, null);
                imageView.setBackgroundColor(context.getResources().getColor(colors[i]));
                list.add(imageView);
            }
            holder.viewPager.setAdapter(new ViewPagerAdapter(list));
        }

        if (data != null && position <= data.size()&& position > 0) {
            Log.d(TAG, "data.get(" + (position - 1) + ") = " + data.get(position - 1) + "   ,size = " + data.size());
            holder.tv.setText("" + data.get(position - 1));
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 1 : data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return HEAD;
        else
            return CONTENT;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView tv;
        public ViewPager viewPager;
        public int TYPE = 0;

        public Holder(View itemView) {
            super(itemView);
        }

        public Holder(View itemView, int type) {
            super(itemView);
            this.TYPE = type;
            if (type == HEAD) {
                viewPager = (ViewPager) itemView.findViewById(R.id.rhv_viewpager);
            } else if (type == CONTENT) {
                tv = (TextView) itemView.findViewById(R.id.ard_tv);
            }
        }
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }
}
