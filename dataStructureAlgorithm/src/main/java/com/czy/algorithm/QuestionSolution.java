package com.czy.algorithm;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * 排z字形 如下
     * 原字符串0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
     * 排4行 如下(排n行自行推演 道理一样)
     * <p>
     * 0       6        12
     * 1     5 7     11 13
     * 2  4    8  10    14
     * 3       9        15
     * 输出：0 6 12 1 5 7 11 13 2 4 8 10 14 3 9 15
     *
     * @param s
     * @param numRows
     * @return
     */
    public String zigZagConversion(String s, int numRows) {
        if (numRows <= 1)
            return s;

        char[] chars = s.toCharArray();

        //用一个二维数组把它按规律放进去  往下y+1 往右上x+1 y-1
        int width = chars.length;
        char map[][] = new char[numRows][width];//注意这里是先高后宽 高即是第几个一维数组

        int nowY = 0;
        int nowX = 0;
        boolean directorUp = false;
        for (int i = 0; i < chars.length; i++) {
            map[nowY][nowX] = chars[i];
            if (nowY == numRows - 1) {//到底了 切换方向向上移动
                directorUp = true;
            } else if (nowY == 0) {//到顶了 切换方向向下移动
                directorUp = false;
            }

            if (directorUp) {//往右上走
                nowX++;
                nowY--;
            } else//往下走
                nowY++;
        }

        StringBuffer sb = new StringBuffer();

        for (int n = 0; n < numRows; n++) {
            for (int m = 0; m < width; m++) {
                if (map[n][m] != '\u0000')//char 的初始值
                    sb.append(map[n][m]);
            }
        }

        return sb.toString();
    }

    /**
     * 排z字形 较为优质的解法
     *
     * @param s
     * @param numRows
     * @return
     */
    public String zigZagConversion1(String s, int numRows) {
        if (numRows <= 1) return s;
        StringBuilder[] sb = new StringBuilder[numRows];
        for (int i = 0; i < sb.length; i++) {
            sb[i] = new StringBuilder("");   //init every sb element **important step!!!!
        }
        int incre = 1;
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            sb[index].append(s.charAt(i));
            if (index == 0) {
                incre = 1;
            }
            if (index == numRows - 1) {
                incre = -1;
            }
            index += incre;
        }
        String re = "";
        for (int i = 0; i < sb.length; i++) {
            re += sb[i];
        }
        return re.toString();
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
        while (z != 0) {
            if ((z & 1) == 1)//末位是1
                distance++;
            z = z >> 1;//右移一位
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
        char[] chars = str.toCharArray();

        boolean falied = true;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] == chars[0] && chars.length % i == 0) {
                falied = false;
                int copys = chars.length / i;//份数 i的大小即为每份的长度
                for (int j = 0; j < i; j++) {
                    if (falied)
                        break;
                    for (int z = 1; z < copys; z++)
                        if (chars[j] != chars[j + i * z]) {
                            falied = true;
                            break;
                        }

                }
            }
            if (!falied)//匹配成功
                return true;
        }
        return false;
    }

    /**
     * 时间复杂度为O(n)的一种解法
     * 字符串是否可以由其某个片段重复累加组成
     *
     * @param str
     * @return
     */
    public boolean repeatedSubstringPattern1(String str) {
        //This is the kmp issue
        int[] prefix = kmp(str);
        int len = prefix[str.length() - 1];
        int n = str.length();
        return (len > 0 && n % (n - len) == 0);
    }

    private int[] kmp(String s) {
        int len = s.length();
        int[] res = new int[len];
        char[] ch = s.toCharArray();
        int i = 0, j = 1;
        res[0] = 0;
        while (i < ch.length && j < ch.length) {
            if (ch[j] == ch[i]) {
                res[j] = i + 1;
                i++;
                j++;
            } else {
                if (i == 0) {
                    res[j] = 0;
                    j++;
                } else {
                    i = res[i - 1];
                }
            }
        }
        return res;
    }

    /**
     * 求岛屿边界问题
     * 一个数组中0代表水 1代表陆地  水包围陆地 陆地横向或纵向连在一起(不会斜着连)组成岛屿  陆地不包围水(没有内湖)
     * 求岛屿的总周长
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        if (null == grid || grid.length == 0)
            return 0;

        int length = 0;

        int width = grid[0].length;
        int height = grid.length;//一维数组的个数

        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++) {
                if (grid[j][i] == 1) {
                    length += getSeawardLength(i, j, grid, width, height);
                }
            }
        return length;
    }

    /**
     * 岛屿边界问题
     * 求一块陆地有几个朝海的面
     * 这里每个方块用这个方法有明显的重复计算 非边缘方块全部只用计算右下角应该就可以了
     *
     * @param x    陆地x坐标
     * @param y    陆地y坐标
     * @param grid 陆地所在的数组
     * @return 这块陆地朝海的面数
     */
    public int getSeawardLength(int x, int y, int[][] grid, int width, int height) {
        int count = 0;

        int left = 0, right = 0, up = 0, down = 0;
        if (y >= 0 && y < height && x >= 0 && x < width) {//目标陆地是否在数组范围内
            if (x - 1 >= 0)//左侧陆地未越界
                left = grid[y][x - 1];
            if (x + 1 < width)//右侧陆地未越界
                right = grid[y][x + 1];
            if (y - 1 >= 0)//上侧陆地未越界
                up = grid[y - 1][x];
            if (y + 1 < height)//下侧陆地未越界
                down = grid[y + 1][x];
            //如果越界 则当做海处理 设默认值0
        }

        //是海则+1
        count += left == 0 ? 1 : 0;
        count += right == 0 ? 1 : 0;
        count += up == 0 ? 1 : 0;
        count += down == 0 ? 1 : 0;

        System.out.println("y = " + y + "  ,x = " + x + "  ,count = " + count);
        return count;
    }

    /**
     * 岛屿边界问题优质简洁解法
     *
     * @param grid
     * @return
     */
    public int islandPerimeter1(int[][] grid) {
        int islands = 0, neighbours = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    islands++; // count islands
                    //只用计算每个方块的右下角就可以了
                    if (i < grid.length - 1 && grid[i + 1][j] == 1)
                        neighbours++; // count down neighbours
                    if (j < grid[i].length - 1 && grid[i][j + 1] == 1)
                        neighbours++; // count right neighbours
                }
            }
        }

        return islands * 4 - neighbours * 2;
    }

    /**
     * 给出一个数组 长度为n 元素大小都在1-n之间 有的出现两次 有的出现1次
     * 找出从1-n的数字里没出现在数组中的数字
     * Input:
     * [4,3,2,7,8,2,3,1]
     *
     * Output:
     * [5,6]
     *
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers(int[] nums) {
        LinkedList list = new LinkedList();
        int[] a = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            a[i] = i + 1;
        }

        for (int j = 0; j < nums.length; j++) {
            a[nums[j] - 1] = -1;
        }

        for (int k = 0; k < nums.length; k++) {
            if (a[k] > 0)
                list.add(a[k]);
        }
        return list;
    }

    /**
     * 简洁代码 处理过程只一个循环 思路从上面推演下来比较好理解
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers1(int[] nums) {
        List<Integer> ret = new ArrayList<Integer>();

        for(int i = 0; i < nums.length; i++) {
            int val = Math.abs(nums[i]) - 1;
            if(nums[val] > 0) {
                nums[val] = -nums[val];
            }
        }

        for(int i = 0; i < nums.length; i++) {
            if(nums[i] > 0) {
                ret.add(i+1);
            }
        }
        return ret;
    }
}
