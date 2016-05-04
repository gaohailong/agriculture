package com.sxau.agriculture.presenter.fragment_presenter;

import com.sxau.agriculture.presenter.fragment_presenter_interface.IInfoListViewPresenter;
import com.sxau.agriculture.view.fragment_interface.IInfoListViewFragment;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class InfoListViewPresenter implements IInfoListViewPresenter {

    private IInfoListViewFragment iInfoListViewFragment;

    public InfoListViewPresenter(IInfoListViewFragment iInfoListViewFragment) {
        this.iInfoListViewFragment = iInfoListViewFragment;
    }


//---------------接口方法-------------------------
    @Override
    public Object findItemByPosition(int position) {
        return null;
    }

    @Override
    public void setCollectState() {

    }

    @Override
    public void pullRefersh() {

    }

    @Override
    public void pushRefersh() {

    }
//-------------------接口方法结束------------------
}
