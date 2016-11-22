package com.cc.musiclist.util;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by zhangyu on 2016/11/22.
 */

public class FileSortUtil {

    private static int compare(File file1, File file2) {
        String name1 = file1.getName();
        String name2 = file2.getName();

        String py1 = PinYinUtils.ccs2Pinyin(name1).toLowerCase();
        String py2 = PinYinUtils.ccs2Pinyin(name2).toLowerCase();

        return StringUtil.compare(py1, py2);
    }

    /**
     * 给一个集合排序
     * 排序原理为快速排序
     *
     * @param list         待排序的集合
     * @return 排序后的集合
     */
    public static List<File> sortList(List<File> list) {
        quickSequence(list, 0, list.size() - 1);
        Collections.sort(list);
        return list;
    }

    /**
     * 快速排序
     * 按照某个基准值，将数组分为大于该值和小于该值的两个子序列，然后递归的对子序列继续按此方法排序，直到最终子序列长度为1时，排序完成.
     * 小规模实验测得，排序效率并不稳定，时快时慢，与具体数组有关.
     *
     * @param list  待排序集合
     * @param start
     * @param end
     */
    private static void quickSequence(List<File> list, int start, int end) {
        if (start >= end)
            return;
        File mid = list.get(end);//将最后一位元素作为基准值
        int left = start, right = end - 1;
        //设定：左边序列小于基准值，右边序列大于基准值
        //从左右两边向中间分别搜索小于和大于基准值的元素，将两边不符合条件的元素相互交换
        while (left < right) {
            while (compare(list.get(left), mid) < 0 && compare(list.get(left), list.get(right)) < 0)
                left++;
            while (compare(list.get(right), mid) >= 0 && compare(list.get(left), list.get(right)) < 0)
                right--;
            Collections.swap(list, left, right);
        }

        //搜索完毕后，如果左边的元素还大于基准值，则将其与基准值交换，这种情况一股出现在基准值恰好选成了序列中的最小值
        if (compare(list.get(left),list.get(end)) >= 0)
            Collections.swap(list, left, end);
        else
            left++;

        //递归操作左边序列
        quickSequence(list, start, left - 1);
        //递归操作右边序列
        quickSequence(list, left + 1, end);
    }
}
