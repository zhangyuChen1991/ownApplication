package com.cc.test.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.cc.test.designpattern.builder.impl.ToyPerson;
import com.cc.test.designpattern.factory.Factory;
import com.cc.test.designpattern.factory.SFactory;
import com.cc.test.designpattern.factory.SProduct;
import com.cc.test.designpattern.singleton.SingletonEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SingletonEnum instance = SingletonEnum.INSTANCE;
        instance.doSomething();

        SProduct product = new SFactory().create(SProduct.class);

        GridView gridView = new GridView(this);
    }
}
