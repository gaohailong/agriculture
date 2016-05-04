package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class MessagePresenter implements IMessagePresenter {
    private IMessageFragment iMessageFragment;

    public MessagePresenter(IMessageFragment iMessageFragment) {
        this.iMessageFragment = iMessageFragment;
    }
//-----------------接口方法-----------------------------
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
//------------------接口方法结束-------------------------
}
