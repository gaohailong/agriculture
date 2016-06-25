package com.sxau.agriculture.presenter.activity_presenter_interface;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IForgetPwdPresenter {
    boolean isPasswordEnable();
    boolean isPhoneEnable();
    boolean isCheckNumEnable();
    void initData();
    void doUpdata();
    void sendPhoneRequest();
}
