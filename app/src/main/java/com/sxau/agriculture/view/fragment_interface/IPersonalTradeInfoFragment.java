package com.sxau.agriculture.view.fragment_interface;

import com.sxau.agriculture.bean.MyPersonalQuestion;
import com.sxau.agriculture.bean.MyPersonalTrade;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalTradeInfoFragment {
    //显示网络请求失败
    void showRequestTimeout();
    //切换fragment时finish该对象
    void finishPersonalQuestionPresenter();
    //没有网络提示
    void showNoNetworking();
    //请求到数据后对页面进行更新
    void updateView(ArrayList<MyPersonalTrade> myPersonalTrades);
    //关闭掉下拉刷新控件
    void closeRefresh();
}
