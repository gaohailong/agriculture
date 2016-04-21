package com.sxau.agriculture.presenter.fragment_presenter_interface;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IMessagePresenter {
    Object findItemByPosition(int position);
    void pullRefersh();
    void pushRefersh();
}
