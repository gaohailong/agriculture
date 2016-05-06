package com.sxau.agriculture.presenter.acitivity_presenter;

import com.sxau.agriculture.presenter.activity_presenter_interface.IPersonalCompilePresenter;
import com.sxau.agriculture.view.activity_interface.IPersonalCompileActivity;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCompilePresenter implements IPersonalCompilePresenter {

    private IPersonalCompileActivity iPersonalCompileActivity;

    public PersonalCompilePresenter(IPersonalCompileActivity iPersonalCompileActivity) {
        this.iPersonalCompileActivity = iPersonalCompileActivity;
    }

    //-----------------------接口方法---------------------
    @Override
    public void doUpdate() {

    }

    @Override
    public void setInformation() {

    }

    @Override
    public Object getInformation() {
        return null;
    }
//------------------接口方法结束---------------------
}
