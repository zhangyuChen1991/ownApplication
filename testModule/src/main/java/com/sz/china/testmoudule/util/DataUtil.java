package com.sz.china.testmoudule.util;

public class DataUtil {
    /**
     * obj转double 多用于json解析后取出对象，如果是double类型则转换
     *
     * @param obj 被转换的对象
     * @return
     */
    public static double getDoubleFormObj(Object obj) {
        double ret = 0d;
        if (null != obj) {
            String str = obj.toString();
            try {
                ret = Double.parseDouble(str);
                // 保留三位小数
                return getDoublePointThree(ret);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0d;
            }
        }

        return 0d;
    }

    /**
     * obj转int 多用于json解析后取出对象，如果是int类型则转换
     *
     * @param obj 被转换的对象
     * @return
     */
    public static int getIntFormObj(Object obj) {
        int ret = 0;
        if (null != obj) {
            String str = obj.toString();
            try {
                ret = Integer.parseInt(str);
                // 保留三位小数
                return ret;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    /**
     * double保留三位小数
     *
     * @param d
     * @return
     */
    public static double getDoublePointThree(double d) {
        double ret = 0;
        long retInt = Math.round(d * 1000);
        ret = retInt / 1000d;
        return ret;
    }

    /**
     * double保留两位小数
     *
     * @param d
     * @return
     */
    public static double getDoublePointTwo(double d) {
        double ret = 0;
        long retInt = Math.round(d * 100);
        ret = retInt / 100d;
        return ret;
    }
}
