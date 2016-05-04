package com.sxau.agriculture.presenter.acitivity_presenter;

import com.sxau.agriculture.presenter.activity_presenter_interface.IExpertAnswerPresenter;
import com.sxau.agriculture.view.activity_interface.IExpertAnswerActivity;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class ExpertAnswerPresenter implements IExpertAnswerPresenter{
    private IExpertAnswerActivity iExpertAnswerActivity;

    public ExpertAnswerPresenter(IExpertAnswerActivity iExpertAnswerActivity) {
        this.iExpertAnswerActivity = iExpertAnswerActivity;
    }

    //-----------------接口方法---------------------
    @Override
    public void setFavState() {

    }

    @Override
    public void setUrgeState() {

    }
//-------------------接口方法结束------------------
}
