package com.sxau.agriculture.presenter.activity_presenter_interface;

import com.sxau.agriculture.bean.User;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalCompilePresenter {
    void doUpdate();
    void setInformation();
    User getData();
    void requestUserData();
    Object getInformation();
}
