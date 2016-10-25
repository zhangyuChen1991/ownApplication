package com.example.rxjavademo.bean;

/**
 * 电影数据 用以获取豆瓣电影前250信息的一部分
 * 数据结构对应获取的数据bean
 * Created by zhangyu on 2016/10/24.
 */
public class MovieBean {
    public String title;//电影名
    public ImgUrls images;//电影海报
    //海报url，服务器数据分为大中小三个图片地址
    public class ImgUrls{
        public String small;
        public String large;
        public String medium;
    }
}
