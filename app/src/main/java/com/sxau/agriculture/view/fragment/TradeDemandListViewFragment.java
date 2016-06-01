package com.sxau.agriculture.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sxau.agriculture.adapter.TradeDemandAdapter;
import com.sxau.agriculture.agriculture.R;

/**
 * Created by Administrator on 2016/5/28.
 */
public class TradeDemandListViewFragment extends BaseFragment{
    private View mview;
    private BaseAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.fragment_trade_listview, container, false);
        return mview;
    }
}
