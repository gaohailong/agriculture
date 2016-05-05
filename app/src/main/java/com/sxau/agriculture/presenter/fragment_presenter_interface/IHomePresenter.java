package com.sxau.agriculture.presenter.fragment_presenter_interface;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * 首页P层接口
 * @author 崔志泽
 */
public interface IHomePresenter {
    Object findUrlByPosition(int position);

    void pullRefersh(SwipeRefreshLayout srl_refresh, Handler handlerForRefresh);

    void pushRefersh();
}
