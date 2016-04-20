package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.fragment_presenter.HomePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IHomePresenter;
import com.sxau.agriculture.view.fragment_interface.IHomeFragment;

public class HomeFragment extends BaseFragment implements IHomeFragment {

    private IHomePresenter iHomePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //将HomeFragment与HomePresenter绑定起来
        iHomePresenter = new HomePresenter(HomeFragment.this);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
//----------------------接口方法----------------------------
    @Override
    public void updateView() {

    }
//----------------------接口方法结束------------------------
}
