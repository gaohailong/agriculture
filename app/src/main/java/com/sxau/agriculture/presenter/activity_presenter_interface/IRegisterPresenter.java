package com.sxau.agriculture.presenter.activity_presenter_interface;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IRegisterPresenter {
    boolean isPasswordEnable();
    boolean isUsernameEnable();
    boolean isPhoneEnable();
    boolean isCheckNumEnable();
    void initData();
    void doRegist();
}
