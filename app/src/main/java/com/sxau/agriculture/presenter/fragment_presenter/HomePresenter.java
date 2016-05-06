package com.sxau.agriculture.presenter.fragment_presenter;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.view.fragment.HomeFragment;
import com.sxau.agriculture.view.fragment_interface.IHomeFragment;

import java.util.ArrayList;

/**
 * 首页P层接口实现
 * @author 崔志泽
 */
public class HomePresenter implements IHomePresenter {
    private IHomeFragment iHomeFragment;

    public HomePresenter(IHomeFragment iHomeFragment) {
        this.iHomeFragment = iHomeFragment;
    }

    //------------------接口方法------------------------
    @Override
    public Object findUrlByPosition(int position) {
        return null;
    }

    @Override
    public void pullRefersh(final SwipeRefreshLayout srl_refresh, Handler handlerForRefresh) {

    }


    @Override
    public void pushRefersh() {

    }

//-------------------------接口方法结束---------------------
}
