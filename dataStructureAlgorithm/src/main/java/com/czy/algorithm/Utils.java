package com.czy.algorithm;

/**
 * Created by zhangyu on 2017/1/1 10:33.
 */

public class Utils {
    public static int getLengthOfInteger(int integer) {
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
    public static long pow(int x, int y) {
        long ret = 1;
        for (int i = 0; i < y; i++) {
            ret *= x;
        }
        return ret;
    }

    /**
     * 对数组由小到大排序
     *
     * @param array
     */
    public static void sortArray(int[] array) {
        int temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                if (array[i] > array[j]) {
                    temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
//        for (int i : array)
//            System.out.print(i+" ");
//        System.out.println();
    }
}
