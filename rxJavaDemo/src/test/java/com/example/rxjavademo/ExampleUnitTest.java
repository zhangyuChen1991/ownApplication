package com.example.rxjavademo;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void test1(){
        HashMap<String,String> hashMap = new HashMap<>();
        for (int i = 0;i < 10;i++){
            hashMap.put("key_"+i,"value_"+i);
        }

        System.out.println(hashMap.toString());
    }

}