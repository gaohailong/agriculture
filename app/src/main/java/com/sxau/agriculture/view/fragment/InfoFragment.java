package com.sxau.agriculture.view.fragment;

import com.sxau.agriculture.agriculture.R;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;

import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.ViewPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;

import java.util.ArrayList;
import java.util.List;

public class InfoFragment extends Fragment {

    private TextView view1, view2;
    private RadioButton rbSupply, rbDemand;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量

//
//public class InfoFragment extends Fragment {
//    private  View mview;
//
//    private ViewPager viewpager;
//    public InfoFragment() {
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_info, container, false);

        initView(convertView);
        return convertView;
    }

    private void initView(View convertView) {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.supply, InfoSupplyFragment.class)
                .add(R.string.demand, InfoDemandFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) convertView.findViewById(R.id.viewpagertab);

        viewPagerTab.setCustomTabView(R.layout.custom_tab_icon_and_text, R.id.custom_tab_text);

        viewPagerTab.setViewPager(viewPager);

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset * 2 + bmpW;//两个相邻页面的偏移量

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            Animation animation = new TranslateAnimation(currIndex * one, arg0 * one, 0, 0);//平移动画
            currIndex = arg0;
            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
            animation.setDuration(200);//动画持续时间0.2秒
            // image.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;
            Toast.makeText(getContext(), "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }

    public class txListener implements View.OnClickListener {
        private int index = 0;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }
//@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        mview=inflater.inflate(R.layout.fragment_info, container, false);
//
//
//        viewpager= (ViewPager) mview.findViewById(R.id.vp_infoSupplyOrDemand);
//        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
//            , FragmentPagerItems.with(getContext())
//            .add("供应",InfoListViewFragment.class)
//            .add("需求", InfoListViewFragment.class)
//
//            .create());
//
//
//    viewpager.setAdapter(fragmentadapter);
//    SmartTabLayout viewPagerTab = (SmartTabLayout) mview.findViewById(R.id.viewpagertab);
//    viewPagerTab.setViewPager(viewpager);
//    return mview;

    }


}
