package com.sxau.agriculture.presenter;

import com.sxau.agriculture.presenter.presenter_interface.ILoginPresenter;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

/**
 * Created by Yawen_Li on 2016/4/19.
 */
public class LoginPresenter implements ILoginPresenter {

    ILoginActivty iLoginActivty;
    private String username;
    private String password;

    public LoginPresenter(ILoginActivty iLoginActivty) {
        this.iLoginActivty = iLoginActivty;
    }

    @Override
    public void doLogin() {
        password = iLoginActivty.getPassword();
        username = iLoginActivty.getUsername();
    }
}
