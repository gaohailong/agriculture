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

/**
 * 问答页面list的Fragment
 * @author 李秉龙
 */
public class QuestionFragment extends BaseFragment implements View.OnClickListener{
    private View mView;
    private ViewPager vPager = null;
    public static Button btn_ask;
    private Context context;

    private FragmentPagerItems.Creator creater;//对标题的动态添加

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_question, container, false);

        vPager = (ViewPager) mView.findViewById(R.id.vp_question_viewpager);
        btn_ask= (Button) mView.findViewById(R.id.btn_ask);
        //实现动态添加
        String list[] = {"肥料", "果树", "花卉", "技术"};
        creater = FragmentPagerItems.with(getContext());
        context=QuestionFragment.this.getActivity();
        for (int i = 0; i < list.length; i++) {
            creater.add(list[i], QuestionListViewFragment.class);
        }
        FragmentPagerItemAdapter fragmentadapter = new FragmentPagerItemAdapter(getChildFragmentManager()
                , creater.create());
        btn_ask.setOnClickListener(this);
        vPager.setAdapter(fragmentadapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) mView.findViewById(R.id.viewpager_question_title);
        viewPagerTab.setViewPager(vPager);
        return mView;

    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        intent.setClass(context, AskQuestion.class);
        startActivity(intent);
    }
}
