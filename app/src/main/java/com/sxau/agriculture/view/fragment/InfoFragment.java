package com.sxau.agriculture.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;

import java.util.ArrayList;

public class InfoFragment extends Fragment {

    private TextView view1, view2;
    private RadioButton rbSupply,rbDemand;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量


    public InfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_info, container, false);

        initView(convertView);
        return convertView;
    }

    private void initView(View convertView){

//        view1 = (TextView)convertView.findViewById(R.id.tv_supply);
//        view2 = (TextView)convertView.findViewById(R.id.tv_demand);

//        view1.setOnClickListener(new txListener(0));
//        view2.setOnClickListener(new txListener(1));

        rbDemand = (RadioButton) convertView.findViewById(R.id.rb_demand);
        rbSupply = (RadioButton) convertView.findViewById(R.id.rb_supply);

        rbDemand.setOnClickListener(new txListener(0));
        rbSupply.setOnClickListener(new txListener(1));

        mPager = (ViewPager)convertView.findViewById(R.id.viewpager);
        fragmentList = new ArrayList<Fragment>();
        Fragment infoSupplyFragment = InfoSupplyFragment.newInstance("this is second fragment");
        Fragment infoDemandFragment = InfoDemandFragment.newInstance("this is third fragment");


        fragmentList.add(infoSupplyFragment);
        fragmentList.add(infoDemandFragment);

        //给ViewPager设置适配器
        mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentList));
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页
        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int one = offset *2 +bmpW;//两个相邻页面的偏移量

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
            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//平移动画
            currIndex = arg0;
            animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
            animation.setDuration(200);//动画持续时间0.2秒
            // image.startAnimation(animation);//是用ImageView来显示动画的
            int i = currIndex + 1;
            Toast.makeText(getContext(), "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }

    public class txListener implements View.OnClickListener {
        private int index=0;

        public txListener(int i) {
            index =i;
        }
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            mPager.setCurrentItem(index);
        }
    }

}
