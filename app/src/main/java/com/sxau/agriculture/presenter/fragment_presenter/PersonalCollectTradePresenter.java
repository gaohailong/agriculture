package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalCollectTradePresenter;
import com.sxau.agriculture.view.fragment_interface.IPersonalCollectTradeFragment;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalCollectTradePresenter implements IPersonalCollectTradePresenter {

    private IPersonalCollectTradeFragment iPersonalCollectTradeFragment;

    public PersonalCollectTradePresenter(IPersonalCollectTradeFragment iPersonalCollectTradeFragment) {
        this.iPersonalCollectTradeFragment = iPersonalCollectTradeFragment;
    }

    //-----------------接口方法-----------------
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
//------------------接口方法结束--------------
}
