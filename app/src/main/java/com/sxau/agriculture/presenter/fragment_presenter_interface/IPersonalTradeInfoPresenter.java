package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.MyPersonalTrade;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalTradeInfoPresenter {
    //得到数据集合
    ArrayList<MyPersonalTrade> getDate();
    //判断网络是否可用
    boolean isNetAvailable();
    void doRequest();
    void pullRefersh();

}
