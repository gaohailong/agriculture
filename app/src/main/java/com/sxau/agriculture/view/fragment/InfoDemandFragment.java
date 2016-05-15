package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.LogUtil;

/**
 * 问题详情的Fragment
 * @author  Yawen_Li on 2016/4/8.
 */
public class InfoDemandFragment extends BaseFragment {

    private static final String TAG = "TestFragment";
    private String hello;// = "hello android";
    private String defaultHello = "default value";

    static android.support.v4.app.Fragment newInstance(String s) {
        InfoDemandFragment newFragment = new InfoDemandFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);

        //bundle还可以在每个标签里传送数据
        return newFragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        LogUtil.d(TAG, "TestFragment-----onCreateView");
        Bundle args = getArguments();
        hello = args != null ? args.getString("hello") : defaultHello;
        View view = inflater.inflate(R.layout.fragment_info_listview, container, false);
        return view;
    }

}
