package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IQuestionListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IQuestionListViewFragment;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class QuestionListViewPresenter implements IQuestionListViewPresenter {
    private IQuestionListViewFragment iQuestionListViewFragment;

    public QuestionListViewPresenter(IQuestionListViewFragment iQuestionListViewFragment) {
        this.iQuestionListViewFragment = iQuestionListViewFragment;
    }

//------------接口方法----------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public void setFavState() {

    }

    @Override
    public void setUrgeState() {

    }

    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }
//-------------接口方法结束------------------
}
