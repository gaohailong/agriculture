package com.sxau.agriculture.presenter.acitivity_presenter;

import com.sxau.agriculture.presenter.activity_presenter_interface.IRegisterPresenter;
import com.sxau.agriculture.view.activity_interface.IRegisterActivity;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class RegisterPresenter implements IRegisterPresenter {

    private IRegisterActivity iRegisterActivity;


    public RegisterPresenter(IRegisterActivity iRegisterActivity) {
        this.iRegisterActivity = iRegisterActivity;
    }

    @Override
    public boolean isRegist() {
        return false;
    }

    @Override
    public boolean isPasswordSame() {
        return false;
    }

    @Override
    public void doRegist() {

    }
}
