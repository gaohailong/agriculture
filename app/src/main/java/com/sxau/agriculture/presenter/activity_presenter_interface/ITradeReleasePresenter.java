package com.sxau.agriculture.presenter.activity_presenter_interface;

import com.sxau.agriculture.bean.CategorieData;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/4.
 */
public interface ITradeReleasePresenter {
    void doRequest();
    ArrayList<String> getCategorieinfo();
}
