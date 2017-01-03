package com.czy.algorithm;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 临时文件 为了避免同时操作同一个文件引发冲突
 * 合并完文件后删除掉吧
 * Created by zhangyu on 2017/1/3.
 */

public class QuestionSolutionTemp {

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

        sortArray(g);
        sortArray(s);

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

    /**
     * 对数组由小到大排序
     *
     * @param array
     */
    public void sortArray(int[] array) {
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
