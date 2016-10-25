package com.example.rxjavademo.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.GridView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.adapter.MovieShowAdapter;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.MovieBean;
import com.example.rxjavademo.bean.MoviePoster;
import com.example.rxjavademo.bean.Movies;
import com.example.rxjavademo.net.RequestTask;

import java.util.HashMap;
import java.util.List;

import rx.Subscriber;

/**
 * Created by zhangyu on 2016/10/24.
 */
public class RetrofitAndRxJavaAct extends BaseActivity {
    private static final String TAG = "Ret&xAct";

    @ViewInject(R.id.arar_gridview)
    private GridView gridView;
    private HashMap<String,Bitmap> posterBitmaps;
    private List<MovieBean> movieList;
    private MovieShowAdapter adapter;
    private StringBuffer sb = new StringBuffer("Retrofit和RxJava结合的Demo测试\n\n");

    @Override
    protected void initView() {
        setContentView(R.layout.act_retrofit_and_rxjava);
    }

    @Override
    protected void initResources() {
        adapter = new MovieShowAdapter(this);
        posterBitmaps = new HashMap<>();
        RequestTask.getInstance().getMovieTop250(getMovieTop250Subscriber);
    }

    @Override
    protected void initViewState() {
        gridView.setAdapter(adapter);
    }

    private Subscriber<Movies> getMovieTop250Subscriber = new Subscriber<Movies>() {
        @Override
        public void onCompleted() {
            Log.i(TAG, "getMovieTop250Subscriber   onCompleted");
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "getMovieTop250Subscriber   onError" + e.toString());
        }

        @Override
        public void onNext(Movies movies) {
            movieList = movies.subjects;
            for(MovieBean movie:movieList){
                RequestTask.getInstance().getImage( new Subscriber<MoviePoster>() {
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
                        posterBitmaps.put(moviePoster.url,moviePoster.bitmap);
                        adapter.setPosterBitmaps(posterBitmaps);
                        adapter.notifyDataSetChanged();
                    }
                },movie.images.large);
            }
            Log.d(TAG, "getMovieTop250Subscriber   onNext:\n");
            adapter.setMovieList(movieList);
            adapter.notifyDataSetChanged();
        }
    };

}
