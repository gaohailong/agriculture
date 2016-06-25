package com.sxau.agriculture.view.activity_interface;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IForgetPwdActivity {
    String getPassword();
    String getPhone();
    String getCheckNum();
    void showProgress(boolean flag);
    void showFindPwdSucceed();
    void showFindPwdFailed();
    void showRequestTimeout();
    void finishForgetPwdActivity();
}
