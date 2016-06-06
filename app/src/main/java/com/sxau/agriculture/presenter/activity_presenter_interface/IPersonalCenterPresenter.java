package com.sxau.agriculture.presenter.activity_presenter_interface;

import com.sxau.agriculture.bean.MyPersonalCenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/4.
 */
public interface IPersonalCenterPresenter {
    ArrayList<MyPersonalCenter> getDates();
    boolean isNetAvailable();
    void doRequest();
}
