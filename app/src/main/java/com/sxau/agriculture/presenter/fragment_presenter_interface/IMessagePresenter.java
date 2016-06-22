package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.MessageInfo;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public interface IMessagePresenter {
    ArrayList<MessageInfo> getDatas();
    void doRequest(String page,String pageSize,boolean isRefresh);
    boolean isLoadOver();
}
