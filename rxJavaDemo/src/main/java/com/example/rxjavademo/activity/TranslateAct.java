package com.example.rxjavademo.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.cc.library.annotation.ViewInject;
import com.example.rxjavademo.R;
import com.example.rxjavademo.base.BaseActivity;
import com.example.rxjavademo.bean.Teacher;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by zhangyu on 2016/10/20.
 */
public class TranslateAct extends BaseActivity {
    private static final String TAG = "TranslateAct";
    @ViewInject(R.id.at_iv)
    private ImageView iv;
    @ViewInject(R.id.at_tv)
    private TextView tv;

    private StringBuffer sb;

    @Override
    protected void initView() {
        setContentView(R.layout.act_translate);

    }

    @Override
    protected void initResources() {
        sb = new StringBuffer();
        doMap();
        doFloatMap();
        doFloatMap1();
        doScan();
    }

    @Override
    protected void initViewState() {
        tv.setText(sb.toString());
    }

    String imgFilePath = "dog_img.jpeg";


    private void doMap() {

        Runnable run = new Runnable() {
            @Override
            public void run() {
                //将文件路径转换为bitmap发出 观察者直接收到bitmap进行处理
                Observable observable = Observable.just(imgFilePath);
                observable.map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String imgFilePath) {
                        return getBitmapFromAssets(imgFilePath);
                    }
                }).subscribeOn(Schedulers.immediate())//当前线程(子线程)发布
                        .observeOn(AndroidSchedulers.mainThread())//UI线程执行(更新图片)
                        .subscribe(new Subscriber<Bitmap>() {
                            @Override
                            public void onCompleted() {
                                Log.i(TAG, "observable.map(..)  onCompleted");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, "observable.map(..)  onError" + e.getMessage());
                            }

                            @Override
                            public void onNext(Bitmap bitmap) {
                                //显示图片
                                iv.setImageBitmap(bitmap);
                            }
                        });
            }
        };
        new Thread(run).start();
    }

    private void doFloatMap() {
        sb.append("doFloatMap():\n"+"array1 = {1, 2, 3, 4}, array2 = {5, 6, 7, 8}\n\n");

        Subscriber subscriber = new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"Observable.just(array1,array2).flatMap  onCompleted\n\n");
                sb.append("Observable.just(array1,array2).flatMap  onCompleted\n\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(array1,array2).flatMap   onError  "+e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG,"Observable.just(array1,array2).flatMap  integer = "+integer);
                sb.append("Observable.just(array1,array2).flatMap\n  integer = "+integer+"\n");
            }
        };

        //flatMap可以实现一个双重转换，在它的回调方法中会返回一个observable对象，但它并不会直接发射这个对象
        //而是将这个observable对象要发射的值 集中到一个新的observable对象中依次发射
        //如本例，第一层Observable依次发射两个数组，经过flatmap转换之后，变成变成两个依次发射数组元素的observable
        // 最后在subscriber中接收到的直接是整型数，等于将两个数组"铺开"了，直接发射整数，这就是大概地"flat"的含义吧
        // flatMap方法可以很灵活的使用，实现双重变换，满足很多不同情况下的需求,比如处理嵌套的异步代码等，非常棒!
        Integer[] array1 = {1, 2, 3, 4}, array2 = {5, 6, 7, 8};
        Observable.just(array1,array2).flatMap(new Func1<Integer[], Observable<?>>() {
            @Override
            public Observable<?> call(Integer[] ints) {
                Observable observable = Observable.from(ints);
                return observable;
            }
        }).subscribe(subscriber);
    }

    private void doFloatMap1() {

        Subscriber subscriber = new Subscriber<Teacher>() {
            @Override
            public void onCompleted() {
                Log.i(TAG,"Observable.just(teachers).flatMap  onCompleted\n\n");
                sb.append("Observable.just(teachers).flatMap  onCompleted\n\n");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG,"Observable.just(teachers).flatMap   onError  "+e.getMessage());
            }

            @Override
            public void onNext(Teacher teacher) {
                Log.d(TAG,"Observable.just(teachers).flatMap  teacher = "+teacher.toString());
                sb.append("Observable.just(teachers).flatMap\n  teacher = "+teacher.toString()+"\n");
            }
        };

        List<Teacher> teachers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            teachers.add(new Teacher("name" + i, i, "place" + i));
        }
        //flatMap可以实现一个双重转换，在它的回调方法中会返回一个observable对象，但它并不会直接发射这个对象
        //而是将这个observable对象要发射的值 集中到一个新的observable对象中依次发射
        //如本例，第一层Observable依次发射两个数组，经过flatmap转换之后，变成变成两个依次发射数组元素的observable
        // 最后在subscriber中接收到的直接是整型数，等于将两个数组"铺开"了，直接发射整数，这就是大概地"flat"的含义吧
        // flatMap方法可以很灵活的使用，实现双重变换，满足很多不同情况下的需求,比如处理嵌套的异步代码等，非常棒!
        Observable.just(teachers).flatMap(new Func1<List<Teacher>, Observable<?>>() {
            @Override
            public Observable<?> call(List<Teacher> teachers) {
                Observable observable = Observable.from(teachers);
                return observable;
            }
        }).subscribe(subscriber);
    }

    private void doScan() {
        sb.append("scan():\n");
        //scan 会将输入的第一个元素当作参数做一个函数运算(函数由你实现，规定需要两个参数，此时另一个默认没有)，然后发射结果
        // 同时，运算结果会被当作函数的与第二个参数与第二个元素再进行函数运算，完成后发射结果
        // 然后将这个结果与第三个元素作为函数的参数再次运算...直到最后一个元素
        Observable.just(1, 2, 3, 4).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                //integer是第一个元素或上一次计算的结果，integer2是下一轮运算中新的序列中元素
                Log.d(TAG, "scan call   integer:" + integer + "   integer2:" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "Observable.just(1,2,3,4).scan   onCompleted..");
                sb.append("Observable.just(1,2,3,4).scan   onCompleted..\n\n");
                initViewState();
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Observable.just(1,2,3,4).scan  onError  " + e.getMessage());
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "Observable.just(1,2,3,4).scan  onNext()..  integer = " + integer);
                /**
                 * 第一次为1，然后是3(1+2)，6(3+3),10(6+4)
                 */
                sb.append("onNext()..  integer = " + integer + "\n");
            }
        });
    }


    /**
     * 更多变换操作 如wiindow() buffer() 等，具体参见文档
     */


    /**
     * 获取assets目录的文件，转换成bitmap
     *
     * @param imgFilePath 文件路径
     * @return bitmap or null
     */
    private Bitmap getBitmapFromAssets(String imgFilePath) {
        try {
            InputStream is = getAssets().open(imgFilePath);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
