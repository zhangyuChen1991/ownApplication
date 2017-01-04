package com.czy.algorithm;

import android.util.Log;

import java.util.Arrays;

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
        int length = Utils.getLengthOfInteger(digitIn);

        int count = 1;
        while (digitIn != 0) {
            ret += (digitIn % 10) * Utils.pow(10, (length - count));
            tempResult += (digitIn % 10) * Utils.pow(10, (length - count));
            //倒过来的时候 计算结果超过整型数上下限
            if (tempResult != ret)
                return 0;
//            System.out.println("digitIn = " + digitIn + "     (digitIn % 10) = " + (digitIn % 10) + "    ret = " + ret + "   tempResult = " + tempResult + "    pow(10 , (length - count)) = " + pow(10, (length - count)));
            digitIn /= 10;
            count++;
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

    public String zigZagConversion(String s, int numRows) {
        String ret = "";
        char[] chars = new char[s.length()];
        s.getChars(0,s.length(),chars,0);
//        for(int i = 0;i < chars.length;i++){
//            System.out.print(chars[i]);
//        }

        return ret;
    }

    /**
     * 你有g.length个孩子 s.length个饼干。饼干大小为元素数值大小，每个孩子拿到不同大小的饼干(g元素大小)就会满足，每个孩子最多发一个饼干
     * 判断能使孩子满足的最大人数 返回答案
     *
     * @param g 令孩子满足的饼干大小数组   饼干大小数组
     * @param s
     * @return
     */
    public int assignCookies(int[] g, int[] s) {
        int answer = 0;
        //先排序 g s由小到大
        //从s里依次取数据比较g里的元素 大于或等于则匹配成功  继续往下匹配 直到最后一个

        Utils.sortArray(g);
        Utils.sortArray(s);

        int markG = -1;
        int markS = -1;
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < g.length; j++) {
                if (i > markS && j > markG && s[i] >= g[j]) {
                    markS = i;
                    markG = j;
                    answer++;
                }
            }
        }
        return answer;
    }

    /**
     * 同样的思路 更简洁的代码
     *
     * @param g
     * @param s
     * @return
     */
    public int assignCookies1(int[] g, int[] s) {
        Arrays.sort(g);//由小到大排序
        Arrays.sort(s);
        int i = 0;
        for (int j = 0; i < g.length && j < s.length; j++) {
            if (g[i] <= s[j]) i++;
        }
        return i;
    }


    /**
     * 求两个数字二进制形式下，对应位置上数字不同的位数。
     * 思路:异或运算之后获取二进制中1的个数
     */
    public int hammingDistance(int x, int y) {
        int distance = 0;

        int z = x ^ y;
        while (z != 0){
            if((z & 1) == 1)//末位是1
                distance++;
            z = z>>1;//右移一位
        }
        return distance;
    }

    /**
     * 一个字符串是否可以由其某个片段重复累加组成
     *
     * @param str 长度小于10000 全小写
     * @return
     */
    public boolean repeatedSubstringPattern(String str) {

        return false;
    }


}
