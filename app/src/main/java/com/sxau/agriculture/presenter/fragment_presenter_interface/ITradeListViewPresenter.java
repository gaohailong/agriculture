package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.TradeData;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 交易
 *
 * @author 高海龙
 */
public interface ITradeListViewPresenter {

    void doRequest(String page,String pagesize,boolean isRefresh);
    ArrayList<TradeData> getDemandDatas();
    ArrayList<TradeData> getSupplyDatas();
//    void doRequest(String page,String pageSize,final boolean isRefresh);
   /* Object findItemByPosition(int position);
    void setCollectState();
    void pullRefersh();
    void pushRefersh();
    *//**
     * 网络请求所有数据
     * *//*
    *//**
     * 得到供应数据
     * *//*
    ArrayList<TradeData> getSupplyDatas();
    ArrayList<TradeData> getDemandDatas();
    //判断网络是否可用
    boolean isNetAvailable();*/

}
