package com.example.rxjavademo.activity;

import android.util.Log;
import android.widget.TextView;

import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/10/19.
 */
public class SimpleDemoAct extends BaseActivity {
    private static final String TAG = "SimpleDemoAct";
    private TextView tv;
    private String tvStr = "在子线程发布消息，在UI线程更新控件的的简单demo\n" +
            "主干流程就是：创建发布者(被观察者Observable)、创建观察者(subscriber/Observer) 发布者注册观察者实现关联\n\n";
    @Override
    protected void initView() {
        setContentView(R.layout.act_simple_demo);
        tv = (TextView) findViewById(R.id.asd_tv);
    }

    @Override
    protected void initResources() {

    }

    @Override
    protected void initViewState() {
        doTest();
    }



    private void doTest() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);//睡一下等待viewRoot构建完成，否则在子线程更新UI也不报错，没法测试.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //注册观察者(这个方法名看起来有点怪，还不如写成regisiterSubscriber(..)或者干脆addSubscriber(..))
                //注册后就会开始调用call()中的观察者执行的方法 onNext() onCompleted()等
                // 默认在当前线程执行，观察者方法操作了控件，所以在这里运行会挂掉
//                observable.subscribe(subscriber);

                //设置观察者和发布者代码所要运行的线程后注册观察者
                observable.subscribeOn(Schedulers.immediate())//在当前线程执行subscribe()方法
                        .observeOn(AndroidSchedulers.mainThread())//在UI线程执行观察者的方法
                        .subscribe(subscriber);

                /**
                 * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
                 Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
                 Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
                 Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
                 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
                 */

            }
        }).start();

    }

    //创建一个被观察者(发布者)
    //除了.create 还有.from .just .range .timer等方法创建发布者,更多创建方式可参见ObservableCreactAct页面的demo
    //相关文档https://mcxiaoke.gitbooks.io/rxdocs/content/operators/Create.html
    //参数还可以有AsyncOnSubscribe SyncOnSubscribe
    Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            subscriber.onNext(1001);
            subscriber.onNext(1002);
            subscriber.onNext(1003);
            subscriber.onCompleted();
        }
    });

    //创建一个观察者
    Subscriber<Integer> subscriber = new Subscriber<Integer>() {
        @Override
        public void onCompleted() {
            Log.d(TAG, "onCompleted.. ");
            tvStr += "执行onCompleted()";
            tv.setText(tvStr);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "subscriber onError.. ");
        }

        @Override
        public void onNext(Integer integer) {
            Log.d(TAG, "onNext.. integer:" + integer);
            tvStr += "执行onNext:" + integer + "\n";
        }
    };
}
