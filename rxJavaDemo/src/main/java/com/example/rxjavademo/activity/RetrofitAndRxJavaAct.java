package com.example.rxjavademo.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.adapter.MovieShowAdapter;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.MovieBean;
import com.example.rxjavademo.bean.MoviePoster;
import com.example.rxjavademo.net.RequestTask;

import java.util.ArrayList;
import java.util.HashMap;

import rx.Subscriber;

/**
 * Created by zhangyu on 2016/10/24.
 */
public class RetrofitAndRxJavaAct extends BaseActivity {
    private static final String TAG = "Ret&RxAct";

    @ViewInject(R.id.arar_gridview)
    private GridView gridView;
    @ViewInject(R.id.arar_loading_container)
    private RelativeLayout loadingContainer;
    private HashMap<String, Bitmap> posterBitmaps;
    private ArrayList<MovieBean> movieList;
    private MovieShowAdapter adapter;

    @Override
    protected void initView() {
        setContentView(R.layout.act_retrofit_and_rxjava);
    }

    @Override
    protected void initResources() {
        adapter = new MovieShowAdapter(this);
        movieList = new ArrayList<>();
        posterBitmaps = new HashMap<>();
        RequestTask.getInstance().getMovieTop250(getMovieTop250Subscriber);

    }

    @Override
    protected void initViewState() {
        gridView.setAdapter(adapter);
    }

    private Subscriber getMovieTop250Subscriber = new Subscriber<MovieBean>() {
        @Override
        public void onCompleted() {
            loadingContainer.setVisibility(View.GONE);
            Log.i(TAG, "getMovieTop250Subscriber   onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "getMovieTop250Subscriber   onError" + e.toString());
        }

        @Override
        public void onNext(MovieBean movieBean) {
            movieList.add(movieBean);
            Log.d(TAG, "getMovieTop250Subscriber   onNext:\n" + movieBean.toString());
            adapter.setMovieList(movieList);
            adapter.notifyDataSetChanged();

            RequestTask.getInstance().getImage(new Subscriber<MoviePoster>() {
                @Override
                public void onCompleted() {
                    Log.i(TAG, "getMovieImg   onCompleted");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "getMovieImg   onError" + e.toString());
                }

                @Override
                public void onNext(MoviePoster moviePoster) {
                    Log.d(TAG, "getImageSubscriber onNext.." + moviePoster.url);
                    posterBitmaps.put(moviePoster.url, moviePoster.bitmap);
                    adapter.setPosterBitmaps(posterBitmaps);
                    adapter.notifyDataSetChanged();
                }
            }, movieBean.images.large);
        }
    };
}
