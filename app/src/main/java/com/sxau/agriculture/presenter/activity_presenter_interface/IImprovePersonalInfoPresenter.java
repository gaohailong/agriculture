package com.sxau.agriculture.presenter.activity_presenter_interface;

/**
 * @author  Yawen_Li on 2016/6/1.
 */
public interface IImprovePersonalInfoPresenter {
    //提交用户个人数据
    void submitPersonalData();
    //初始化数据
    void initData();
    //判断数据的有效性
    boolean isAddressAvailable();
    boolean isRealNameAvailable();

}
