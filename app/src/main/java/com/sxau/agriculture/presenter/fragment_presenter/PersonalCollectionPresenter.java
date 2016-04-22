package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectQuestionPresenter;
import com.sxau.agriculture.view.fragment_interface.IPresonalCollectQuestionFragment;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCollectionPresenter implements IPersonalCollectQuestionPresenter {
    private IPresonalCollectQuestionFragment iPresonalCollectQuestionFragment;

    public PersonalCollectionPresenter(IPresonalCollectQuestionFragment iPresonalCollectQuestionFragment) {
        this.iPresonalCollectQuestionFragment = iPresonalCollectQuestionFragment;
    }

    //------------------接口方法-----------------
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
//-----------------接口方法结束------------------
}
