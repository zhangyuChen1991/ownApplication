package com.example.rxjavademo.activity;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 发布者(Observable)的不同创建方式
 * Created by zhangyu on 2016/10/19.
 */
public class ObservableCreateAct extends BaseActivity {
    private static final String TAG = "ObservableCreateAct";
    private TextView tv;
    private StringBuffer sbStr = new StringBuffer("通过不同的方法创建Observable，以满足不同情况下的需求\n\n");

    @Override
    protected void initView() {
        setContentView(R.layout.act_simple_demo);
        tv = (TextView) findViewById(R.id.asd_tv);

    }

    @Override
    protected void initViewState() {
        tv.setText(sbStr.toString());
    }


    @Override
    protected void initResources() {
        doFrom1();
        doFrom2();
        doJust1();
        doTimer1();
        doRangeAndRepeat();
        //这里只列举了常用的创建方法 还有Interval() Start() Defer()等具体看文档
    }

    private void doFrom1() {

        Integer[] array = {1, 2, 3, 4, 5};
        //通过from方式创建，将其它种类的对象和数据类型转换为Observable
        Observable fromObservale = Observable.from(array);
        //fromObservale 注册观察者 触发观察者的执行方法
        //在不写观察者的情况下，可以使用Action1和Action0这两个接口来实现不完整定义的回调； 参见：ActionSubscriber
        //Action1<T>可以代替实现onNext(); Action1<Throwable>可以代替实现onError(); Action0可以代替实现onConplete()
        fromObservale.subscribe(
                new Action1<Integer>() {

                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "from(array)  Action1 call " + integer);
                        refreshStr("from(array)  Action1 call " + integer);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "from(array)  " + throwable.getMessage());
                        refreshStr("from(array)  " + throwable.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "from(array)  Action0  onComplete..");
                        refreshStr("from(array)  Action0  onComplete..\n");
                    }
                });
    }

    private void doFrom2() {

        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            teachers.add(new Teacher("name" + i, i, "place" + i));
        }
        //from方法支持继承了Interable接口的参数，所以常用的数据结构(Map、List..)都可以转换
        Observable fromObservale = Observable.from(teachers);
        fromObservale.subscribe(new Subscriber<Teacher>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "from(teachers)  onCompleted");
                refreshStr("from(teachers)  onCompleted\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "from(teachers)  " + e.getMessage());
                refreshStr("from(teachers)  " + e.getMessage());
            }

            @Override
            public void onNext(Teacher teacher) {
                Log.d(TAG, "from(teachers)  onNext:" + teacher.toString());
                refreshStr("from(teachers)  onNext:" + teacher.toString());
            }
        });
    }

    private void doJust1() {
        //Just类似于From，但是From会将数组或Iterable的元素具取出然后逐个发射，而Just只是简单的原样发射，将数组或Iterable当做单个数据。
        //Just接受一至九个参数，返回一个按参数列表顺序发射这些数据的Observable
        Observable justObservable = Observable.just(1, "someThing", false, 3.256f, new Teacher("Jhon", 25, "NewYork"));
        justObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "just(...)  onCompleted");
                refreshStr("just(...)  onCompleted\n");
                SharedPreferences sp;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "just(...)  onError:" + e.getMessage());
                refreshStr("just(...)  onError:" + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "just(...)  onNext:" + o.toString());
                refreshStr("just(...)  onNext:" + o.toString());
            }
        });

    }


    private void doTimer1() {
        //timer()创建一个Observable，它在一个给定的延迟后发射一个特殊的值 设定执行方法在UI线程执行
        //延时两秒后发射值
        //实测 延时2s后发送了一个0
        Observable timerObservable = Observable.timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread());
        timerObservable.subscribe(
                new Subscriber() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "timer(...)  onCompleted");
                        refreshStr("timer(...)  onCompleted\n");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "timer(...)  onError:" + e.getMessage());
                        refreshStr("timer(...)  onError:" + e.getMessage());
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d(TAG, "timer(...)  onNext:" + o.toString());
                        refreshStr("timerObservable 延时两秒触发 发送值：" + o.toString());
                    }
                }
        );
    }


    private void doRangeAndRepeat() {
        //range 发射从n到m的整数序列 可以指定Scheduler设置执行方法运行的线程
        //repeat方法可以指定重复触发的次数
        Observable rangeObservable = Observable.range(3, 7).repeat(2);
        rangeObservable.subscribe(
                new Action1() {
                    @Override
                    public void call(Object o) {
                        Log.e(TAG, "range(3, 7).repeat(2)  onNext:"+o.toString());
                        refreshStr("range(3, 7).repeat(2)  onNext:"+o.toString());
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "range(3, 7).repeat(2)  "+throwable.getMessage());
                        refreshStr("range(3, 7).repeat(2)  "+throwable.getMessage());
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        Log.i(TAG, "range(3, 7).repeat(2)  onCompleted");
                        refreshStr("range(3, 7).repeat(2)  onCompleted\n");

                    }
                });
    }

    private void refreshStr(String str) {
        sbStr.append(str);
        sbStr.append("\n");
        tv.setText(sbStr.toString());
    }

}
