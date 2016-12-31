package com.czy.algorithm;

import android.util.Log;

/**
 * 一些基础算法题目的解答方案
 * 题目来自leetcode等
 * 刷题用
 * Created by zhangyu on 2016/12/31 19:36.
 */
public class QuestionSolution {
    private static final String TAG = "QS";

    /**
     * 反转一个整数
     * Reverse digits of an integer.
     * Example1: x = 123, return 321
     * Example2: x = -123, return -321
     */
    public int reverseInteger(int digitIn) {
        int ret = 0;
        long tempResult = 0;
        //这个整数的长度 不包含正负符号
        int length = getLengthOfInteger(digitIn);

        int count = 1;
        while (digitIn != 0) {
            ret += (digitIn % 10) * pow(10, (length - count));
            tempResult += (digitIn % 10) * pow(10, (length - count));
            //倒过来的时候 计算结果超过整型数上下限
            if (tempResult != ret)
                return 0;
//            System.out.println("digitIn = " + digitIn + "     (digitIn % 10) = " + (digitIn % 10) + "    ret = " + ret + "   tempResult = " + tempResult + "    pow(10 , (length - count)) = " + pow(10, (length - count)));
            digitIn /= 10;
            count++;
        }

        return ret;
    }


    public int getLengthOfInteger(int integer) {
        int length = 0;
        while (integer != 0) {
            integer /= 10;
            length++;
        }
        return length;
    }

    /**
     * x的y次方
     *
     * @param x
     * @param y
     * @return
     */
    public long pow(int x, int y) {
        long ret = 1;
        for (int i = 0; i < y; i++) {
            ret *= x;
        }
        return ret;
    }

    /**
     * 反转整数 优质解法
     *
     * @param x
     * @return
     */
    public int reverse(int x) {
        int result = 0;

        while (x != 0) {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if ((newResult - tail) / 10 != result) {
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return result;
    }
}
