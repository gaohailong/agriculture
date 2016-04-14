package com.sxau.agriculture.view.fragment;



import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;



public class InfoFragment extends BaseFragment {
    private  View mview;

    private ViewPager viewpager;
//    private ImageButton ibHead;
//    private TextView name;
//    private TextView date;
//    private TextView distance;
//    private TextView title;
//    private TextView content;
    public InfoFragment() {
    }
@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview=inflater.inflate(R.layout.fragment_info, container, false);


        viewpager= (ViewPager) mview.findViewById(R.id.vp_infoSupplyOrDemand);
        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
            , FragmentPagerItems.with(getContext())
            .add("供应",InfoListViewFragment.class)
            .add("需求", InfoListViewFragment.class)
            .create());


    viewpager.setAdapter(fragmentadapter);
    SmartTabLayout viewPagerTab = (SmartTabLayout) mview.findViewById(R.id.viewpagertab);
    viewPagerTab.setViewPager(viewpager);
    return mview;

    }

    
}
