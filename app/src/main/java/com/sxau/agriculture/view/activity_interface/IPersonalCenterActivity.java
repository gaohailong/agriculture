package com.sxau.agriculture.view.activity_interface;

import com.sxau.agriculture.bean.MyPersonalCenter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/4.
 */
public interface IPersonalCenterActivity {
    void updateView(ArrayList<MyPersonalCenter> myPersonalCenters);
    void showNoNetworking();
    void showRequestTimeout();
}
