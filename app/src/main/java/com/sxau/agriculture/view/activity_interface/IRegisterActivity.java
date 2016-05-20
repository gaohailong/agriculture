package com.sxau.agriculture.view.activity_interface;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IRegisterActivity {
    String getUsername();
    String getPassword();
    String getAffirmPassword();
    String getPhone();
    String getCheckNum();
    void showProgress(boolean flag);
    void showRegisteSucceed();
    void showRegistFailed();
    void showRequestTimeout();
    void finishRegisterActivity();
}
