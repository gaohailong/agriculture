package com.sxau.agriculture.presenter.fragment_presenter_interface;

import com.sxau.agriculture.bean.ExpertArticles;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;

import java.util.ArrayList;

/**
 * Created by Yawen_Li on 2016/4/22.
 */
public interface IExpertArticlePresenter {
    Object findItemByPosition(int position);
    ArrayList<HomeArticle> getDatas();
    //判断网络是否可用
    boolean isNetAvailable();
    void doRequest();
    void pullRefersh();
    void pushRefersh();
}
