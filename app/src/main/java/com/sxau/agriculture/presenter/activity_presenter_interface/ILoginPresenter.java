package com.sxau.agriculture.presenter.activity_presenter_interface;

/**
 * Created by Yawen_Li on 2016/4/19.
 */
public interface ILoginPresenter {
    boolean isPasswordEnable();
    boolean isPhoneEnable();
    void initData();
    void doLogin();
    void doRequestUserInfo();
}
