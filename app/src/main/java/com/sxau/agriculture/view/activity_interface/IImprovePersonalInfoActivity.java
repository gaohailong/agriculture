package com.sxau.agriculture.view.activity_interface;

/**
 * @author  Yawen_Li on 2016/6/1.
 */
public interface IImprovePersonalInfoActivity {
    String getAddress();
    String getRealName();
    String getIndustry();
    String getScale();

    void showProgress(boolean flag);
    void showSuccess();
    void showFailed();
}
