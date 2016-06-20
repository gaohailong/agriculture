package com.sxau.agriculture.view.activity_interface;

import android.content.Context;

/**
 * Created by Yawen_Li on 2016/4/19.
 */
public interface ILoginActivty {
    String getPhone();
    String getPassword();
    void showProgress(boolean flag);
    void showLoginSucceed();
    void showLoginFailed();
    void showRequestTimeout();
    void finishLoginActivity();
    Context getContext();

    void doRequestUserInfo();
}
