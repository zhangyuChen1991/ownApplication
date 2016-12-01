package com.cc.musiclist;

import com.cc.musiclist.util.PinYinUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String in = "我放假";
        System.out.println(PinYinUtils.ccs2Pinyin(in));
        System.out.println("AAMNJfvd我是来得快".toLowerCase());

        assertEquals(4, 2 + 2);
    }
}