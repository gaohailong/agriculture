package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.MessageInfo;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IMessagePresenter {
    Object findItemByPosition(int position);
    ArrayList<MessageInfo> getDatas();
    void pullRefersh();
    void pushRefersh();
    void doRequest();
}
