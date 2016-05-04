package com.sxau.agriculture.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.presenter.fragment_presenter.MessagePresenter;
import com.sxau.agriculture.presenter.fragment_presenter_interface.IMessagePresenter;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.fragment_interface.IMessageFragment;

/**
 * 消息的Fragment
 * @author 高海龙
 */
public class MessageFragment extends BaseFragment implements IMessageFragment {


    private IMessagePresenter iMessagePresenter;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //将MessageFragment与MessagePresenter绑定
        iMessagePresenter = new MessagePresenter(MessageFragment.this);

        View view = inflater.inflate(R.layout.fragment_message, container, false);
        return view;
    }

//-------------------接口方法----------------
    @Override
    public void updateView() {

    }
//-------------------接口方法结束--------------
}
