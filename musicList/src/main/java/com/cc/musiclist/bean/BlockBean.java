package com.cc.musiclist.bean;

import java.util.Random;

/**
 * Created by zhangyu on 2016-06-16 17:20.
 */
public class BlockBean {
    private static final String TAG = "BlockBean";
    public int[][] blockData = new int[100][3];

    public BlockBean() {
        init();
    }

    private void init() {
        Random r = new Random();
        long numBefore = -1;
        for (int i = 0; i < blockData.length; i++) {
            long random = r.nextInt(3);
            while (random == numBefore)
                random = r.nextInt(3);
            numBefore = random;
            if (random % 3 == 0) {
                blockData[i][0] = 0;
                blockData[i][1] = 1;
                blockData[i][2] = 1;
            } else if (random % 3 == 1) {
                blockData[i][0] = 1;
                blockData[i][1] = 0;
                blockData[i][2] = 1;
            } else if (random % 3 == 2) {
                blockData[i][0] = 1;
                blockData[i][1] = 1;
                blockData[i][2] = 0;
            }
        }

        for (int m = 0; m < blockData.length; m++) {
            System.out.print("\n");
            for (int n = 0; n < blockData[n].length; n++) {
                System.out.print(blockData[m][n] + "\t");
            }
        }
    }

}
