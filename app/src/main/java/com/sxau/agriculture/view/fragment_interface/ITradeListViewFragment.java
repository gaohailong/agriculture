package com.sxau.agriculture.view.fragment_interface;

import com.sxau.agriculture.bean.TradeData;

import java.util.ArrayList;

/**
 * 信息专区更新ListView
 * @author 田帅
 */
public interface ITradeListViewFragment {
/**
 * 更新数据
 * */
    void updateView(ArrayList<TradeData> supplyDatas);
    void changeItemView();
    int getCollectState();
    /**
     * 点击加载判断
     * */
    void isLoadOver(boolean isLoadover);
    /**
     * 数据加载失败
     * */
    void onFailure();
    /**
     * toast收藏成功
     * */
    void CollectionSuccess();
    /**
     * toast收藏失败
     * */
    void CollectionFailure();
}
