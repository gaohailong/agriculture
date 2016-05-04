package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.ActivityCollectorUtil;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.fragment.HomeFragment;
import com.sxau.agriculture.view.fragment.InfoFragment;
import com.sxau.agriculture.view.fragment.MessageFragment;
import com.sxau.agriculture.view.fragment.QuestionFragment;

/**
 * 主界面的activity
 * @author 高海龙
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    private FragmentTabHost fragmentTabHost;
    private RadioGroup radioGroup;
    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;
    private final Class[] fragments = {HomeFragment.class, QuestionFragment.class, InfoFragment.class, MessageFragment.class};
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniTitle();

        initView();
    }

    /**
     * 初始化标题
     */
    private void iniTitle() {
        TopBarUtil topBar = (TopBarUtil) findViewById(R.id.topBar);
        topBar.setLeftRoundImageIsVisible(true);
        topBar.setLeftRoundImage(R.mipmap.img_default_user_portrait_150px);

        topBar.setTitleIsVisible(true);
        topBar.setContent("文章");
        if (flag == 0) {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_phone_white_96px);
        } else {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_search_48px);
        }
        topBar.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,PersonalCenterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickLeftImage() {
                Toast.makeText(MainActivity.this, "天哪", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickRightImage() {
                Toast.makeText(MainActivity.this, "天哪", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(i + "").setIndicator(i + "");
            fragmentTabHost.addTab(tabSpec, fragments[i], null);
        }
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setOnCheckedChangeListener(this);
        fragmentTabHost.setCurrentTab(0);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                fragmentTabHost.setCurrentTab(0);
                flag = 0;
                iniTitle();
                break;
            case R.id.rb_queston:
                fragmentTabHost.setCurrentTab(1);
                flag = 1;
                iniTitle();
                break;
            case R.id.rb_info:
                fragmentTabHost.setCurrentTab(2);
                flag = 2;
                iniTitle();
                break;
            case R.id.rb_message:


//                fragmentTabHost.setCurrentTab(3);
                fragmentTabHost.setCurrentTab(3);
                flag = 3;
                iniTitle();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCollectorUtil.finishAll();
            finish();
        }
    }
}
