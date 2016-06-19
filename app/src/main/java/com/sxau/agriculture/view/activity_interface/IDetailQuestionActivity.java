package com.sxau.agriculture.view.activity_interface;

/**
 * 回答详情页Activity接口
 *
 * @author 高海龙
 */
public interface IDetailQuestionActivity {
    int getQuestionId();
    void updateView();
    void showServiceError();
    void showFailed();
}
