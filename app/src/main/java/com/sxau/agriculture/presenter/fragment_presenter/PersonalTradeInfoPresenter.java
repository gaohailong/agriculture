package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IPersonalTradeInfoPresenter;
import com.sxau.agriculture.view.fragment_interface.IPersonalTradeInfoFragment;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public class PersonalTradeInfoPresenter implements IPersonalTradeInfoPresenter {

    private IPersonalTradeInfoFragment iPersonalTradeInfoFragment;

    public PersonalTradeInfoPresenter(IPersonalTradeInfoFragment iPersonalTradeInfoFragment) {
        this.iPersonalTradeInfoFragment = iPersonalTradeInfoFragment;
    }

    //----------------接口方法---------------------
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
//-----------------接口方法结束-----------------
}
