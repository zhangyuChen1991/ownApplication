package com.example.rxjavademo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rxjavademo.R;
import com.example.rxjavademo.bean.MovieBean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhangyu on 2016/10/25.
 */

public class MovieShowAdapter extends BaseAdapter {

    private Context context;

    private HashMap<String, Bitmap> posterBitmaps;
    private ArrayList<MovieBean> movieList;


    public void setPosterBitmaps(HashMap<String, Bitmap> posterBitmaps) {
        this.posterBitmaps = posterBitmaps;
    }

    public MovieShowAdapter(Context context) {
        this.context = context;
    }

    public void setMovieList(ArrayList<MovieBean> movieList) {
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList == null ? 0 : movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        Holder holder;
        if (convertView == null) {
            v = View.inflate(context, R.layout.adapter_show_movie, null);
            holder = new Holder();
            v.setTag(holder);
        } else {
            v = convertView;
            holder = (Holder) v.getTag();
        }

        holder.iv = (ImageView) v.findViewById(R.id.asm_iv);
        holder.tv = (TextView) v.findViewById(R.id.asm_tv);

        if (movieList != null && position < movieList.size()) {
            MovieBean move = movieList.get(position);
            holder.tv.setText(move.title + "\n" + move.rating.average);
            //通过图片的url来对应从Map中寻找图片的bitmap
            if (posterBitmaps != null)
                holder.iv.setImageBitmap(posterBitmaps.get(move.images.large));
        }

        return v;
    }

    class Holder {
        ImageView iv;
        TextView tv;
    }
}
