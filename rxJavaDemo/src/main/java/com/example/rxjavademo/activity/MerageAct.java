package com.example.rxjavademo.activity;

import android.util.Log;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.FuncN;

/**
 * Created by zhangyu on 2016/10/22 17:19.
 */

public class MerageAct extends BaseActivity {
    private static final String TAG = "MerageAct";
    @ViewInject(R.id.asd_tv)
    private TextView tv;
    private StringBuffer sb = new StringBuffer("合并为按条件将若干被观察者合并为一个观察者的操作\n\n");

    @Override
    protected void initView() {
        setContentView(R.layout.act_simple_demo);
    }

    @Override
    protected void initResources() {
        doMerage();
        doZip();
//        doJoin();
    }

    @Override
    protected void initViewState() {
        tv.setText(sb.toString());
    }

    private void doMerage() {
        sb.append("merage合并两个observable为一个observable并依次发射值\n");
        Observable observable1 = Observable.just(1, 2, 3);
        Observable observable2 = Observable.just(4, 5, 6);
        Observable observable3 = Observable.merge(observable1, observable2);
        observable3.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Observable.merge(observable1, observable2)  onCompleted");
                sb.append("Observable.merge(observable1, observable2)  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observable.merge(observable1, observable2) onError  " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "Observable.merge(observable1, observable2)  onNext()..  obj = " + o.toString());
                sb.append("onNext()..  obj = " + o.toString() + "\n");
            }
        });


    }

    private void doZip() {
        sb.append("zip合并三个observable为一个observable并发射Teacher对象\n");

        Observable observable1 = Observable.just(25, 27, 39);
        Observable observable2 = Observable.just("Jake", "Jhon", "Harden");
        Observable observable3 = Observable.just("SanFrancisco", "Washington", "Seattle");

        List observables = new ArrayList();
        observables.add(observable1);
        observables.add(observable2);

        //zip()通过一个函数将多个Observables的发射物结合到一起，基于这个函数的结果为每个结合体发射单个数据项。
        //最多可以加入9个参数 下面分别为 通过集合合并，合并两个，合并三个的demo  要注意的是后面Func2 Func3 FuncN的写法
//        Observable observable4 = Observable.zip(observables, new FuncN() {
//            @Override
//            public Teacher call(Object... args) {
//                Object obj = args[0];
//                for(int i = 0;i < args.length;i++)
//                    Log.v(TAG,args[i].toString());
//                return null;
//            }
//        });
//
//        //Func2<Integer,String,Teacher> 作为call方法的<参数，参数，返回值类型>
//        Observable observable5 = Observable.zip(observable1, observable2, new Func2<Integer,String,Teacher>() {
//
//            @Override
//            public Teacher call(Integer age, String name) {
//                return new Teacher(name,age,"");
//            }
//        });

        //Func3<Integer,String,String,Teacher> 作为call方法的<参数，参数，参数，返回值类型>
        Observable observable6 = Observable.zip(observable1, observable2, observable3, new Func3<Integer, String, String, Teacher>() {

            @Override
            public Teacher call(Integer age, String name, String workPlace) {
                return new Teacher(name, age, workPlace);
            }
        });


        observable6.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "observable = Observable.zip  onCompleted");
                sb.append("observable = Observable.zip  onCompleted\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "observable = Observable.zip  onError e " + e.getMessage());
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "observable = Observable.zip  onNext obj = " + o.toString());
                sb.append("onNext obj = " + o.toString() + "\n");
            }
        });
    }

    private void doJoin() {
        //跟时间有关系，待研究
        Observable observable1 = Observable.just(1, 2, 3);
        Observable observable2 = Observable.just(4, 5,6);
        Observable observable3 = observable1.join(observable2,
                new Func1() {
                    @Override
                    public Object call(Object o) {
                        return null;
                    }
                },
                new Func1() {
                    @Override
                    public Object call(Object o) {
                        return null;
                    }
                },
                new Func2() {
                    @Override
                    public Object call(Object o, Object o2) {
                        return null;
                    }
                });
        //发射
        observable3.subscribe();
    }

}
