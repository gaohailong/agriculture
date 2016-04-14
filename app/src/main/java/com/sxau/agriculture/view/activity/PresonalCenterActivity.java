package com.sxau.agriculture.view.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.fragment.PersonalQuestionFragment;

import java.util.List;

/**
 * Created by Administrator on 2016/4/9.
 */
public class PresonalCenterActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager vTitlePaper;

    private ImageButton imageButtonBack;
    private Button buttonCompile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_presonal_center);

        initView();

       FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), FragmentPagerItems.with(this)
               .add(R.string.titleA,PersonalQuestionFragment.class)
               .add(R.string.titleB,PersonalQuestionFragment.class)
               .add(R.string.titleC,PersonalQuestionFragment.class)
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
                finish();
                break;
            case R.id.btn_compile:
                Intent intent2 = new Intent();
                intent2.setClass(PresonalCenterActivity.this,PresonalComplite.class);
                startActivity(intent2);
                break;
            default:
        }
    }
}
