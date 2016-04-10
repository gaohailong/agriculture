package com.sxau.agriculture.view.activity;

import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.view.fragment.HomeFragment;
import com.sxau.agriculture.view.fragment.InfoFragment;
import com.sxau.agriculture.view.fragment.MessageFragment;
import com.sxau.agriculture.view.fragment.QuestionFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private FragmentTabHost fragmentTabHost;
    private RadioGroup radioGroup;
    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;
    private final Class[] fragments = {HomeFragment.class, QuestionFragment.class, InfoFragment.class, MessageFragment.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
    public void onBackPressed() {
        if (System.currentTimeMillis() - currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
        } else {
            finish();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                fragmentTabHost.setCurrentTab(0);
                break;
            case R.id.rb_queston:
                fragmentTabHost.setCurrentTab(1);
                break;
            case R.id.rb_info:
                fragmentTabHost.setCurrentTab(2);
                break;
            case R.id.rb_message:
                fragmentTabHost.setCurrentTab(3);
                break;
            default:
                break;
        }
    }
}
