package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;

/**
 * Created by Yawen_Li on 2016/4/8.
 */
public class TradeDemandFragment extends BaseFragment{
    private static final String TAG = "TestFragment";
    private String defaultHello = "default value";

    static android.support.v4.app.Fragment newInstance(String s) {
        TradeDemandFragment newFragment = new TradeDemandFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);

        //bundle还可以在每个标签里传送数据


        return newFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView");
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.fragment_trade_listview, container, false);


        Toast.makeText(getContext(),"aaa",Toast.LENGTH_LONG).show();
        return view;
    }
}
