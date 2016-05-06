package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalQuestionPresenter;
import com.sxau.agriculture.view.fragment_interface.IPersonalQuestionFragment;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalQuestionPresenter implements IPersonalQuestionPresenter {

    private IPersonalQuestionFragment iPersonalQuestionFragment;

    public PersonalQuestionPresenter(IPersonalQuestionFragment iPersonalQuestionFragment) {
        this.iPersonalQuestionFragment = iPersonalQuestionFragment;
    }

    //------------------接口方法--------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }
//---------------------接口方法结束--------------------
}
