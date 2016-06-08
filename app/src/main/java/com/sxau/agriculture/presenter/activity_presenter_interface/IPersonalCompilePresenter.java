package com.sxau.agriculture.presenter.activity_presenter_interface;

import com.sxau.agriculture.bean.User;

import java.io.File;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalCompilePresenter {
    void doUpdate();
    void setInformation();
    User getData();
    void requestUserData();
    void setImageUri(File photoFile);
    Object getInformation();
}
