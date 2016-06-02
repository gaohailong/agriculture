package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IPersonalCollectQuestionPresenter {
    Object findItemByPosition(int position);
    ArrayList<MyPersonalCollectionQuestion> getDatas();
    //判断网络是否可用
    boolean isNetAvailable();
    void doRequest();
    void pullRefersh();
    void pushRefersh();
}
