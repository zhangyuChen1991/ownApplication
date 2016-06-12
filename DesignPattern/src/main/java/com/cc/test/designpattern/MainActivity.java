package com.cc.test.designpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cc.test.designpattern.builder.impl.ToyPerson;
import com.cc.test.designpattern.singleton.SingletonEnum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SingletonEnum instance = SingletonEnum.INSTANCE;
        instance.doSomething();

    }
}
