package com.sz.china.testmoudule;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sz.china.testmoudule.R;
import com.sz.china.testmoudule.adapter.StrAdapter;
import com.sz.china.testmoudule.bean.AdapterBean;

import java.util.ArrayList;
import java.util.List;

public class RcvFloatTitleTestActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    StrAdapter mAdapter;
    RelativeLayout mFloatItem;
    private TextView mFloatTitleTv;
    private List<AdapterBean> mList;
    String[] strs = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rcv_float_title);
        mRecyclerView = (RecyclerView) findViewById(R.id.rcv);
        mFloatItem = (RelativeLayout) findViewById(R.id.float_item);
        mFloatItem.findViewById(R.id.content).setVisibility(View.GONE);
        mFloatItem.findViewById(R.id.bottom_line).setVisibility(View.GONE);

        mFloatTitleTv = (TextView) mFloatItem.findViewById(R.id.title);

        initRcv();
        mFloatTitleTv.setText(mList.get(0).str);
    }

    private void initRcv() {
        mAdapter = new StrAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        createData();
        mAdapter.setDatas(mList);
        mAdapter.notifyDataSetChanged();

        //第一条到顶部，浮层显示，更新字母,item字母隐藏
        //最后一条到顶部，浮层隐藏，item字母显示
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                if (mList.get(firstVisibleItemPosition).isFirst) {//第一条到顶部
                    //浮层显示，更新字母,item字母隐藏
                    mFloatItem.setVisibility(View.VISIBLE);
                    mFloatTitleTv.setText(mList.get(firstVisibleItemPosition).str);

                    StrAdapter.ViewHolder viewHolder = (StrAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
                    viewHolder.mTitleTv.setVisibility(View.GONE);

                } else if (mList.get(firstVisibleItemPosition).isLast) {//最后一条到顶部
                    //浮层隐藏，item字母显示
                    mFloatItem.setVisibility(View.GONE);

                    StrAdapter.ViewHolder viewHolder = (StrAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition);
                    viewHolder.mTitleTv.setVisibility(View.VISIBLE);

                    StrAdapter.ViewHolder viewHolderNext = (StrAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + 1);
                    if (null != viewHolderNext) {//下一部分的第一条item字母重新显示出来
                        viewHolderNext.mTitleTv.setVisibility(View.VISIBLE);
                    }

                }else if(mList.get(firstVisibleItemPosition).isLastSecond){
                    //浮层显示，更新字母
                    mFloatItem.setVisibility(View.VISIBLE);
                    mFloatTitleTv.setText(mList.get(firstVisibleItemPosition).str);

                    StrAdapter.ViewHolder viewHolderNext = (StrAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(firstVisibleItemPosition + 1);
                    if (null != viewHolderNext) {//本部分最后一条item字母隐藏
                        viewHolderNext.mTitleTv.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void createData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            AdapterBean adapterBean = new AdapterBean();
            if (i < 10) {
                adapterBean.str = strs[0];
            } else if (i < 20) {
                adapterBean.str = strs[1];
            } else if (i < 30) {
                adapterBean.str = strs[2];
            } else if (i < 40) {
                adapterBean.str = strs[3];
            } else {
                adapterBean.str = strs[4];
            }

            if (i % 10 == 0) {
                adapterBean.isFirst = true;
            }
            else if (i % 10 == 9) {
                adapterBean.isLast = true;
            }
            else if (i % 10 == 8) {
                adapterBean.isLastSecond = true;
            }
            mList.add(adapterBean);
        }

    }
}
