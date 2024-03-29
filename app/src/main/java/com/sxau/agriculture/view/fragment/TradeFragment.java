package com.sxau.agriculture.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.activity.TradeReleaseActivity;

import java.util.ArrayList;

/**
 * 信息专区的Fragment
 * @author 田帅
 */
public class TradeFragment extends BaseFragment implements View.OnClickListener{

    private TextView view1, view2;
    private RadioButton rbSupply, rbDemand;
    private ViewPager mPager;
    private ArrayList<Fragment> fragmentList;
    private int currIndex;//当前页卡编号
    private int bmpW;//横线图片宽度
    private int offset;//图片移动的偏移量
    public static Button btn_info_float;
    private Context mcontext;
    private View mView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mView == null) {

            mView = inflater.inflate(R.layout.fragment_trade, container, false);
            mcontext = TradeFragment.this.getActivity();

            initView(mView);

            btn_info_float.setOnClickListener(this);
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);
        }
        return mView;
    }

    private void initView(View convertView) {

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.supply, TradeSupplyListViewFragment.class)
                .add(R.string.demand, TradeDemandListViewFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) convertView.findViewById(R.id.viewpager);
        btn_info_float = (Button) convertView.findViewById(R.id.btn_info_float);

        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) convertView.findViewById(R.id.viewpagertab);

        viewPagerTab.setViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_info_float:
                Intent intent = new Intent(TradeFragment.this.getActivity(), TradeReleaseActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
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
            int i = currIndex + 1;
            Toast.makeText(getContext(), "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
        }
    }

}