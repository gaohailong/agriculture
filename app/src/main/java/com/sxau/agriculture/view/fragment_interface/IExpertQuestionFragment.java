package com.sxau.agriculture.view.fragment_interface;

import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

/**
 * 指派给专家的问题Fragment接口
 * @author Yawen_Li
 */
public interface IExpertQuestionFragment {
    //显示网络请求失败
    void showRequestTimeout();
    //切换fragment时finish该对象
    void finishPersonalQuestionPresenter();
    //没有网络提示
    void showNoNetworking();
    //请求到数据后对页面进行更新
    void updateView(ArrayList<MyPersonalQuestion> myPersonalQuestions);
    //关闭掉下拉刷新控件
    void closeRefresh();
}
