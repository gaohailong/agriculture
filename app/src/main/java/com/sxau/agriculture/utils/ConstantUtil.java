package com.sxau.agriculture.utils;

import android.view.View;

/**
 * 定义常量的Util
 *
 * @author 高海龙
 */
public class ConstantUtil {
    //============================字符常量的定义===================================
    //农科项目网络请求的地址
    public static final String BASE_URL = "http://sxnk110.workerhub.cn:9000/api/v1/";
    //农科项目网络请求图片的地址
    public static final String BASE_PICTURE_URL = "http://storage.workerhub.cn/";
    //首页文章的基本URL
    public static final String ARTICLE_BASE_URL = "http://sxnk110.workerhub.cn/#/article/";
    //后台资源根地址
    public static final String STORAGE_URL = "http://storage.workerhub.cn/";
    //每个listView的item显示的数量
    public static final String ITEM_NUMBER = "10";
    //登录成功之后的缓存文件名（主要为了保存authToken）
    public static final String CACHE_KEY = "Cache_User";
    //收藏交易的缓存文件名
    public static final String CACHE_PERSONALCOLLECTTRADE_KEY = "Cache_PersonalCollectTradeList";
    //我的问题的缓存文件名
    public static final String CACHE_PERSONALQUESTION_KEY = "Cache_PersonalQuestionList";
    //供应交易的缓存文件名
    public static final String CACHE_TRADESUPPLY_KEY = "Cache_TradeSupplyList";
    //需求交易的缓存文件名
    public static final String CACHE_TRADEDEMAND_KEY = "Cache_TradeDemandList";
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
    //下拉刷新底部文字的改变
    public static final int LOAD_MORE = 0x00000005;
    public static final int LOAD_FAIL = 0x00000006;
    public static final int LOAD_OVER = 0x00000007;
    //启动activity
    public static final int START_ACTIVITY = 0x00000005;

}
