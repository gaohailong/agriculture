package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.TradeData;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface ITradeListViewPresenter {

    Object findItemByPosition(int position);
    void setCollectState();
    void pullRefersh();
    void pushRefersh();
    /**
     * 网络请求所有数据
     * */
    void doRequest(String page,String pageSize,final boolean isRefresh);
    /**
     * 得到供应数据
     * */
    ArrayList<TradeData> getSupplyDatas();
    ArrayList<TradeData> getDemandDatas();
    //判断网络是否可用
    boolean isNetAvailable();

}
