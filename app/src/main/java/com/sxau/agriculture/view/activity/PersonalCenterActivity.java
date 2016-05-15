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
 * 个人中心Activity
 * @author 李秉龙
 */
public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager vPager = null;
    private ViewPager vTitlePaper;
    private List<View> viewlist;
    private View MyQusetionView, TradeInfoView;
    private ImageButton ib_back;
    private Button btn_compile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);

        initView();

       FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
               .add(R.string.MyQuestion, PersonalQuestionFragment.class)
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
        ib_back = (ImageButton) this.findViewById(R.id.ib_back);
        btn_compile = (Button) this.findViewById(R.id.btn_compile);
        ib_back.setOnClickListener(this);
        btn_compile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_compile:
                Intent intent2 = new Intent();
                intent2.setClass(PersonalCenterActivity.this,PersonalCompileActivity.class);
                startActivity(intent2);
                break;
            default:
        }
    }
}
