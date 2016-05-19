package com.sxau.agriculture.utils;

import android.view.View;

/**
 * 定义常量的Util
 *
 * @author 高海龙
 */
public class ConstantUtil {
    //============================字符常量的定义===================================
    //网络请求的地址
    public static final String BASE_URL = "http://sxnk110.workerhub.cn:9000/api/v1/";
    //每个listView的item显示的数量
    public static final String  ITEM_NUMBER = "3";
    //============================整形常量的定义===================================
    //下拉刷新的时间
    public static final int PULL_TIME = 3000;
    //初始化数据
    public static final int INIT_DATA = 0x00000001;
    //获取网络的数据
    public static final int GET_NET_DATA = 0x00000002;
    //下拉刷新
    public static final int PULL_REFRESH = 0x00000003;
    //上拉加载
    public static final int UP_LOAD = 0x00000004;

}
