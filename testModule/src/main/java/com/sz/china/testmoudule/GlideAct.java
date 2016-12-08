package com.sz.china.testmoudule;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.module.GlideModule;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sz.china.testmoudule.util.DisplayUtils;

/**
 * 相关资源：
 * 一篇文章：https://inthecheesefactory.com/blog/get-to-know-glide-recommended-by-google/en
 * 上面文章中文版(推荐看原版就好了，容易看懂的)：http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0327/2650.html
 * github：https://github.com/bumptech/glide
 * 另一篇文章(有各类api调用demo)：http://www.cnblogs.com/whoislcj/p/5558168.html
 * Created by zhangyu on 2016/12/5.
 */

public class GlideAct extends Activity {
    private static final String TAG = "GlideAct";
    private RecyclerView recyclerView;
    private RelativeLayout bigImgContainer;
    private ImageView bigImg;
    private String[] imageUrls;
    private MAdapter mAdapter;
    private Object images;
    private ObjectAnimator alphaDismissAnim;
    private ObjectAnimator alphaShowAnim;

    private int nowClickPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridle);
        initImageUrls();
        bigImgContainer = (RelativeLayout) findViewById(R.id.ag_big_image_container);
        bigImg = (ImageView) findViewById(R.id.ag_big_image_show);
        recyclerView = (RecyclerView) findViewById(R.id.ag_recyclerview);
        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridManager);
        mAdapter = new MAdapter();
        recyclerView.setAdapter(mAdapter);
    }

    private void initImageUrls() {
        imageUrls = new String[20];
        imageUrls[0] = "http://image95.360doc.com/DownloadImg/2016/03/2115/68159708_2.jpg";
        imageUrls[1] = "http://img.sj33.cn/uploads/allimg/201005/20100509135319416.jpg";
        imageUrls[2] = "http://img5.niutuku.com/phone/1212/3131/3131-niutuku.com-20272.jpg";
        imageUrls[3] = "http://static5.photo.sina.com.cn/middle/4fdc4090x935e60a7b244&690";
        imageUrls[4] = "http://ww2.sinaimg.cn/mw600/7f166dc7jw1erpdvadatnj20pu0iygow.jpg";
        imageUrls[5] = "http://imgsrc.baidu.com/forum/pic/item/faf2b2119313b07e1d33eaad0cd7912396dd8c8d.jpg";
        imageUrls[6] = "http://img.sj33.cn/uploads/allimg/201008/20100824175454943.jpg";
        imageUrls[7] = "http://i3.hexunimg.cn/2013-12-28/160980834.jpg";
        imageUrls[8] = "http://img4.wowsai.com/home/attachment/2012/11/image/201211091046144177.jpg";
        imageUrls[9] = "http://img2.zol.com.cn/product/93_940x705/182/ceq2xr6c1TvSs.jpg";
        imageUrls[10] = "http://news.julur.com/uploadfile/2014/0227/20140227083153646.jpg";
        imageUrls[11] = "http://bbsatt.yineitong.com/forum/2011/07/14/11071407479e9a9509bc58aa95.jpg";
        imageUrls[12] = "http://img1.niutuku.com/design/1207/2317/ntk-2317-08353owu21ap5.jpg";
        imageUrls[13] = "http://www.wallcoo.com/nature/MSwanson_Macro_Autumn_Plant_1920/wallpapers/1280x1024/MSwanson%20-%20Grass%2003.jpg";
        imageUrls[14] = "http://img4q.duitang.com/uploads/item/201504/13/20150413H0302_fYWnt.jpeg";
        imageUrls[15] = "http://image30.360doc.com/DownloadImg/2011/05/2809/12182374_25.jpg";
        imageUrls[16] = "http://image75.360doc.com/DownloadImg/2014/06/1406/42578357_1.jpg";
        imageUrls[17] = "http://img.sj33.cn/uploads/allimg/201302/1-130201105044.jpg";
        imageUrls[18] = "http://img.sj33.cn/uploads/allimg/200906/20090615225216717.jpg";
        imageUrls[19] = "http://n1.itc.cn/img8/wb/recom/2016/04/27/146174853763319352.JPEG";
    }

    private class MAdapter extends RecyclerView.Adapter<MAdapter.MHolder> {

        @Override
        public MHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_glide_image_list, parent, false);
            MHolder mHolder = new MHolder(itemView);
            return mHolder;
        }

        @Override
        public void onBindViewHolder(MHolder holder, int position) {
            int resize = DisplayUtils.getWidth() / 3;
            Glide.with(getApplicationContext())
                    .load(imageUrls[position])
                    .override(resize, resize)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)//缓存源资源
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            Log.e(TAG, "load image exception  error:");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            Log.i(TAG, "model = " + model);
                            return false;
                        }
                    }).placeholder(R.mipmap.icon_m)
                    .into(holder.iv);
        }

        @Override
        public int getItemCount() {
            return imageUrls.length;
        }

        class MHolder extends RecyclerView.ViewHolder {

            private ImageView iv;

            public MHolder(View itemView) {
                super(itemView);
                iv = (ImageView) itemView.findViewById(R.id.adil_iv);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (bigImgContainer.getVisibility() != View.VISIBLE) {
                            nowClickPosition = getAdapterPosition();
                            Log.d(TAG, "item click  nowClickPosition = " + nowClickPosition);
                            showBigImg();
                        }
                    }
                });
            }
        }
    }

    /**
     * Glide配置类,在Mainfest的meta中进行配置
     */
    public class GlideConfiguration implements GlideModule {

        @Override
        public void applyOptions(Context context, GlideBuilder builder) {
            // Apply options to the builder here.
            //在Mainfest的meta中配置此类为默认选项后，下面一行代码设置图片默认显示质量为ARGB_8888，画质较高。
            builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
        }

        @Override
        public void registerComponents(Context context, Glide glide) {
            // register ModelLoaders here.
        }
    }


    private void showBigImg() {
        alphaShowAnim = ObjectAnimator.ofFloat(bigImgContainer, "alpha", 0, 1);
        alphaShowAnim.addListener(animatorListener);
        alphaShowAnim.setDuration(300);
        alphaShowAnim.start();
    }

    private void dissmissBigImg() {
        alphaDismissAnim = ObjectAnimator.ofFloat(bigImgContainer, "alpha", 1, 0);
        alphaDismissAnim.addListener(animatorListener);
        alphaDismissAnim.setDuration(300);
        alphaDismissAnim.start();
    }

    private Animator.AnimatorListener animatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {
            if (animation == alphaShowAnim) {
                bigImgContainer.setVisibility(View.VISIBLE);
                Glide.with(GlideAct.this).load(imageUrls[nowClickPosition]).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(bigImg);
            }
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (animation == alphaDismissAnim) {
                bigImgContainer.setVisibility(View.GONE);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //重写返回键
            if (bigImgContainer.getVisibility() == View.VISIBLE) {
                dissmissBigImg();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
