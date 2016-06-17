package com.jtest;

/**
 * Created by zhangyu on 2016-06-16 09:27.
 */
public class SelfPowUtil {

    public boolean isSelfPowNum(double num) {
        boolean ret = false;
        int numLength = getNumLength(num);

        double sum = 0;
        for (int i = 1; i <= numLength; i++) {
            int locationNum = getNumAtLocation(num, i);
            sum += Math.pow(locationNum, numLength);
        }

        if (sum == num) {
            ret = true;
            System.out.println(num + ":" + ret + "\t\tsum = " + sum);
        } else
            ret = false;

        return ret;
    }

    /**
     * 获取一个数字固定位上的数字，从最高位开始数(比如 997： 第一位为9，第三位为7)
     *
     * @param num      传入的数字
     * @param location 要获取的数字位置
     * @return 返回所求位置上的数字或者-1
     */
    public int getNumAtLocation(double num, int location) {
        int locationNum = 0;
        int numLength = getNumLength(num);

        if (location > numLength || location <= 0)
            return -1;

        locationNum = (int) (num / (Math.pow(10, numLength - location)) % 10);

        // System.out.println(num + "第" + location + "位数 ： " + locationNum);
        return locationNum;
    }

    /**
     * 获取一个数字的长度
     *
     * @param num
     * @return
     */
    public int getNumLength(double num) {
        int length = 0;
        if (num >= 0) {
            while (num >= 1) {
                num = num / 10;
                length++;

            }
        } else {
            while (num <= -1) {
                num = num / 10;
                length++;

            }
        }
        return length;
    }
}
