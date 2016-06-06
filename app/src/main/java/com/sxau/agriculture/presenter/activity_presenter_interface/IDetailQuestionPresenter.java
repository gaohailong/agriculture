package com.sxau.agriculture.presenter.activity_presenter_interface;

import com.sxau.agriculture.bean.DetailQuestionData;

/**
 * Created by gaohailong on 2016/6/4.
 * @author 高海龙
 */
public interface IDetailQuestionPresenter {
    void getDetailData(String id);
    DetailQuestionData getData();
}
