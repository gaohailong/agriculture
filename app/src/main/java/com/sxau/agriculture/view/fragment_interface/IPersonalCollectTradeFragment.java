package com.sxau.agriculture.view.fragment_interface;

import android.widget.TextView;

import com.sxau.agriculture.bean.MyPersonalCollectTrades;

import java.util.ArrayList;

/**
 * 个人收藏交易p的接口
 * @author 李秉龙
 */
public interface IPersonalCollectTradeFragment {

    void showRequestTimeout();
    void showNoNetworking();
    void updateView(ArrayList<MyPersonalCollectTrades> myPersonalCollectTrades);
    void closeRefresh();
}
