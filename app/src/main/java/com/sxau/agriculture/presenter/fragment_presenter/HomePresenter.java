package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.view.fragment_interface.IHomeFragment;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class HomePresenter implements IHomePresenter {
    private IHomeFragment iHomeFragment;

    public HomePresenter(IHomeFragment iHomeFragment) {
        this.iHomeFragment = iHomeFragment;
    }
//------------------接口方法------------------------
    @Override
    public Object findUrlByPosition(int position) {
        return null;
    }

    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }
//-------------------------接口方法结束---------------------
}
