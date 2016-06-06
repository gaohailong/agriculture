package com.sxau.agriculture.view.fragment_interface;

import android.widget.TextView;

import com.sxau.agriculture.bean.MyPersonalCollectTrades;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalCollectTradeFragment {

    void showRequestTimeout();
    void showNoNetworking();
    void updateView(ArrayList<MyPersonalCollectTrades> myPersonalCollectTrades);
    void closeRefresh();
}
