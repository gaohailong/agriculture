package com.sxau.agriculture.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.fragment.PersonalCollectQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalCollectTradeFragment;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;
import com.sxau.agriculture.view.fragment.PersonalTradeInfoFragment;

import java.util.List;

/**
 * 个人中心
 * 李秉龙
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager vPager = null,vTitlePaper;
    private List<View> viewlist;
    private View MyQusetionView, TradeInfoView;
    private ImageButton imageButtonBack;
    private Button buttonCompile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);

        initView();

       FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
               .add(R.string.MyQuestion,PersonalQuestionFragment.class)
               .add(R.string.TradeInfo,PersonalTradeInfoFragment.class)
               .add(R.string.CollectQuestion,PersonalCollectQuestionFragment.class)
               .add(R.string.CollectTrade,PersonalCollectTradeFragment.class)
               .create() );
        vTitlePaper  = (ViewPager) findViewById(R.id.viewpager);
        vTitlePaper.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(vTitlePaper);

    }
    private void  initView(){
        imageButtonBack = (ImageButton) this.findViewById(R.id.ib_back);
        buttonCompile = (Button) this.findViewById(R.id.btn_compile);
        imageButtonBack.setOnClickListener(this);
        buttonCompile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                Intent intent1 = new Intent();
                intent1.setClass(PersonalCenterActivity.this,MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_compile:
                Intent intent2 = new Intent();
                intent2.setClass(PersonalCenterActivity.this,PersonalCompile.class);
                startActivity(intent2);
                break;
            default:
        }
    }
}
