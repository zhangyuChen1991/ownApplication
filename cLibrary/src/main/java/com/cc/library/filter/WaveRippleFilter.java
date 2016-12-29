package com.cc.library.filter;

import android.graphics.Bitmap;
import android.opengl.GLES30;

/**
 * Created by zhangyu on 2016/12/28.
 */

public class WaveRippleFilter {
    public Bitmap getWaveBitmap(Bitmap bitmap){

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Bitmap waveBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.RGB_565);
        return  waveBitmap;
    }



    /**
     * 给bitmap加水波纹效果
     * 经过测试 处理一次图片需要耗费很长时间 效率太低且耗资源 实际不可行呐
     * @param bitmap
     * @return
     */
    public Bitmap addWaterWave(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] buf1 = new int[w * h];
        int[] buf2 = new int[w * h];
        int[] source = new int[w * h];

        Bitmap result = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);

        bitmap.getPixels(source, 0, w, 0, 0, w, h);

        int[] temp = new int[source.length];

        int x = w / 2;//x中心点
        int y = h / 2;//y中心点
        int stonesize = Math.max(w, h) / 4;//波纹 半径
        int stoneweight = Math.max(w, h);


        if ((x + stonesize) > w || (y + stonesize) > h || (x - stonesize) < 0 || (y - stonesize) < 0){
            return null;
        }

        //中心点半径范围内
        for (int posx = x - stonesize; posx < x + stonesize; posx++){
            for (int posy = y - stonesize; posy < y + stonesize; posy++){
                if ((posx - x) * (posx - x) + (posy - y) * (posy - y) <= stonesize * stonesize){
                    buf1[w * posy + posx] = (int)-stoneweight;
                }
            }
        }
        for(int i = 0 ; i < 170; i++){
            for (int j = w; j < w * h - w; j++){
                //波能扩散
                buf2[j] =(int)(((buf1[j-1]+buf1[j+1]+buf1[j-w]+buf1[j+w])>>1)- buf2[j]);
                //波能衰减
                buf2[j] -= buf2[j]>>5;
            }
            //交换波能数据缓冲区
            int[] tmp =buf1;
            buf1 = buf2;
            buf2 = tmp;

        /* 渲染水纹效果 */
            int xoff, yoff;
            int k = w;
            for (int m = 1; m < h - 1; m++) {
                for (int j = 0; j < w; j++) {
                    //计算偏移量
                    xoff = buf1[k-1]-buf1[k+1];
                    yoff = buf1[k-w]-buf1[k+w];
                    //判断坐标是否在窗口范围内
                    if ((m+yoff )< 0 || (m+yoff )>= h || (j+xoff )< 0 || (j+xoff )>= w) {
                        k++;
                        continue;
                    }
                    //计算出偏移象素和原始象素的内存地址偏移量
                    int pos1, pos2;
                    pos1=w * (m + yoff) + (j + xoff);
                    pos2=w * m + j;
                    temp[pos2++]=source[pos1++];
                    k++;
                }
            }
        }
        result.setPixels(temp, 0, w, 0, 0, w, h);

        return result;
    }


}
