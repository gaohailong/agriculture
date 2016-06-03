package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalTrade;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalCollectTradePresenter {
    Object findItemByPosition(int position);
    ArrayList<MyPersonalCollectTrades> getDatas();
    boolean isNetAvailable();
    void doRequest();
    void pullRefersh();
    void pushRefersh();
}
