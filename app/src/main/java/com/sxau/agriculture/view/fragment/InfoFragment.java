package com.sxau.agriculture.view.fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.TopBarUtil;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {
    private View mview;
    private ViewPager viewpager;
    public InfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_info, container, false);



        viewpager = (ViewPager) mview.findViewById(R.id.vp_infoSupplyOrDemand);
        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
                , FragmentPagerItems.with(getContext())
                .add("供应", InfoListViewFragment.class)
                .add("需求", InfoListViewFragment.class)
                .create());
        viewpager.setAdapter(fragmentadapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) mview.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewpager);
        return mview;

    }


}
