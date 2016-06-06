package com.sxau.agriculture.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.view.menu.MenuView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.activity.AskQuestion;

import java.util.ArrayList;

/**
 * 问答页面list的Fragment
 * @author 李秉龙
 */
public class QuestionFragment extends BaseFragment implements View.OnClickListener,ViewPager.OnPageChangeListener{
    private View mView;
    private ViewPager vPager = null;
    public static Button btn_ask;
    private Context context;
    private ArrayList<String> list;

    private FragmentPagerItems.Creator creater;//对标题的动态添加

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_question, container, false);

        vPager = (ViewPager) mView.findViewById(R.id.vp_question_viewpager);
        btn_ask= (Button) mView.findViewById(R.id.btn_ask);
        list =new ArrayList<>();
        context=QuestionFragment.this.getActivity();

        list.add("肥料");
        list.add("农业");
        list.add("草业");
        list.add("产后护理");
        list.add("郭栋");
        list.add("高海龙");
        list.add("ddd");
        list.add("ppp");

        addData();

        btn_ask.setOnClickListener(this);
        return mView;

    }

    //伴随数据动态加载fragment
    public void addData(){
        creater = FragmentPagerItems.with(context);
        for (int i = 0; i < list.size(); i++) {
            creater.add(list.get(i), QuestionListViewFragment.class);
        }
        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
                , creater.create());
        vPager.setAdapter(fragmentadapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) mView.findViewById(R.id.viewpager_question_title);
        viewPagerTab.setViewPager(vPager);
        vPager.setOnPageChangeListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(context, AskQuestion.class);
        startActivity(intent);
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
