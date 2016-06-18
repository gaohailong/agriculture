package com.sxau.agriculture.view.fragment_interface;

import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

/**
 * 个人收藏问题p的接口
 * @author 李秉龙
 */
public interface IPresonalCollectQuestionFragment {
    //显示网络请求失败
    void showRequestTimeout();
    //切换fragment时finish该对象
    void finishPersonalQuestionPresenter();
    //没有网络提示
    void showNoNetworking();
    //请求到数据后对页面进行更新
    void updateView(ArrayList<MyPersonalCollectionQuestion> myPersonalQuestions);
    //关闭掉下拉刷新控件
    void closeRefresh();
}
