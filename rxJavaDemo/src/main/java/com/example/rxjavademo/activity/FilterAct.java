package com.example.rxjavademo.activity;

import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by zhangyu on 2016/10/22 16:24.
 */

public class FilterAct extends BaseActivity {
    private static final String TAG = "SimpleDemoAct";

    @ViewInject(R.id.asd_tv)
    private TextView tv;
    private StringBuffer sb = new StringBuffer("过滤为一系列根据一定条件过滤掉相关元素的操作\n\n");
    @Override
    protected void initView() {
        setContentView(R.layout.act_simple_demo);
    }

    @Override
    protected void initResources() {
        doElementAt();
        doFilter();
        doSkip();
        doTake();
    }

    @Override
    protected void initViewState() {
        tv.setText(sb.toString());
    }

    private void doElementAt() {
        //elementAt(i) 只发射第i个元素
        // 还有first last方法分别为发射第一个元素、发射最后一个元素，比较简单，不写demo了
        Observable.just(1,2,3,4).elementAt(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Observable.just(1,2,3,4).elementAt(2)  onCompleted");
                sb.append("Observable.just(1,2,3,4).elementAt(2)  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(1,2,3,4).elementAt(2)  onError  "+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Observable.just(1,2,3,4).elementAt(2)  onNext()..  integer = " + integer);
                sb.append("onNext()..  integer = " + integer + "\n");
            }
        });
    }


    private void doFilter() {
        Observable.just(1,2,3,4,5).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                //true 则返回，false则被过滤掉
                // 这里返回小于4的值
                return (integer < 4);
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Observable.just(1,2,3,4,5).filter()  onCompleted");
                sb.append("Observable.just(1,2,3,4,5).filter()  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(1,2,3,4,5).filter()  onError  "+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Observable.just(1,2,3,4,5).filter()  onNext()..  integer = " + integer);
                sb.append("onNext()..  integer = " + integer + "\n");
            }
        });
    }


    private void doSkip() {
        //忽略掉前两项元素
        // skipLast(i) 忽略最后i个元素 省略demo
        //skip(long,TimeUnit));// 跳过前long时间的Observable，发射之后的
        Observable.just(1,2,3,4).skip(2).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Observable.just(1,2,3,4).skip(2)  onCompleted");
                sb.append("Observable.just(1,2,3,4).skip(2)  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(1,2,3,4).skip(2)  onError  "+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Observable.just(1,2,3,4).skip(2)  onNext()..  integer = " + integer);
                sb.append("onNext()..  integer = " + integer + "\n");
            }
        });

        //skip(long,TimeUnit));// 跳过前long时间的Observable，发射之后的
//        Observable.just(1,2,3,4).skip(100,TimeUnit.MICROSECONDS).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onCompleted() {
//                Log.d(TAG, "Observable.just(1,2,3,4).skip(2)  onCompleted");
//                sb.append("Observable.just(1,2,3,4).skip(2)  onCompleted\n\n");
//                initViewState();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG,"Observable.just(1,2,3,4).skip(2)  onError  "+e.getMessage());
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                Log.d(TAG, "Observable.just(1,2,3,4).skip(2)  onNext()..  integer = " + integer);
//                sb.append("onNext()..  integer = " + integer + "\n");
//            }
//        });

    }


    private void doTake() {

        //take(i) 取前i个
        //takeLast(i) 取最后i个 省略demo
        Observable.just(1,2,3,4).take(3).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Observable.just(1,2,3,4).take(3)  onCompleted");
                sb.append("Observable.just(1,2,3,4).take(3)  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(1,2,3,4).take(3)  onError  "+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Observable.just(1,2,3,4).take(3)  onNext()..  integer = " + integer);
                sb.append("onNext()..  integer = " + integer + "\n");
            }
        });
    }

    /**
     * 更多操作如：
     * IgnoreElements (不发射任何数据，只发射Observable的终止通知)
     * Debounce 仅在过了一段指定的时间还没发射数据时才发射一个数据
     * 具体参考文档
     *
     */
}
