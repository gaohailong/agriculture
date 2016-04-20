package com.sxau.agriculture.presenter.acitivity_presenter;

import com.sxau.agriculture.presenter.activity_presenter_interface.ILoginPresenter;
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
