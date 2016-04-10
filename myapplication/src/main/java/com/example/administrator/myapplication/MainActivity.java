package com.example.administrator.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;
    private ViewPagerIndicator mIndicator;

    private List<String> mTtitle = Arrays.asList("短信", "收藏", "推荐");
    private List<VpSimpleFragment> mContents = new ArrayList<VpSimpleFragment>();
    private FragmentPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    private void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
    }

    private void initDatas() {
        for (String title : mTtitle) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //tabWidth*positionOffset+position*tabWidth  第三个宽度
        mIndicator.scroll(position, positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
