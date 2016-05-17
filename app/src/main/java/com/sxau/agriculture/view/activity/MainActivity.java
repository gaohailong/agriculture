package com.sxau.agriculture.view.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.utils.ActivityCollectorUtil;
import com.sxau.agriculture.utils.TopBarUtil;
import com.sxau.agriculture.view.fragment.HomeFragment;
import com.sxau.agriculture.view.fragment.TradeFragment;
import com.sxau.agriculture.view.fragment.MessageFragment;
import com.sxau.agriculture.view.fragment.QuestionFragment;

/**
 * 主界面的activity
 *
 * @author 高海龙
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    private FragmentTabHost fragmentTabHost;
    private RadioGroup radioGroup;

    private Context context;
    private long currentBackPressedTime = 0;
    private static final int BACK_PRESSED_INTERVAL = 2000;
    private final Class[] fragments = {HomeFragment.class, QuestionFragment.class, TradeFragment.class, MessageFragment.class};
    private int flag = 0;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniTitle();
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);

        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.fl_content);
        int count = fragments.length;
        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(i + "").setIndicator(i + "");
            fragmentTabHost.addTab(tabSpec, fragments[i], null);
        }
        radioGroup.setOnCheckedChangeListener(this);
        fragmentTabHost.setCurrentTab(0);
    }

    /**
     * 初始化标题
     */
    private void iniTitle() {
        TopBarUtil topBar = (TopBarUtil) findViewById(R.id.topBar);
        topBar.setLeftRoundImageIsVisible(false);
        topBar.setLeftImageIsVisible(true);
//        Bitmap leftRoundBitmapImage= BitmapUtil.decodedBitmapFromResource(getResources(),R.mipmap.img_default_user_portrait_150px,45,45);
//        topBar.setLeftRoundBitmapImage(leftRoundBitmapImage);

        if (flag == 0) {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_phone_white);
        } else {
            topBar.setRightImageIsVisible(true);
            topBar.setRightImage(R.mipmap.ic_search);
        }
        topBar.setOnTopbarClickListener(new TopBarUtil.TopbarClickListner() {
            @Override
            public void onClickLeftRoundImage() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickLeftImage() {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PersonalCenterActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickRightImage() {
                if (flag == 0) {
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(MainActivity.this);
                    final View view = getLayoutInflater().inflate(R.layout.dialog_phone, null);
                    aBuilder.setView(view);
                    LinearLayout ll_agriculture = (LinearLayout) view.findViewById(R.id.ll_agriculture);
                    LinearLayout ll_healthy = (LinearLayout) view.findViewById(R.id.ll_healthy);
                    ll_agriculture.setOnClickListener(MainActivity.this);
                    ll_healthy.setOnClickListener(MainActivity.this);
                    aBuilder.show();
                } else {
                    //TODO 将搜索功能写到这
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_agriculture:
                phoneNumber = "tel:0351-7537092";
                callPhone(v, phoneNumber);
                break;
            case R.id.ll_healthy:
                phoneNumber = "tel:0791-86665536";
                callPhone(v, phoneNumber);
                break;
            default:
                break;
        }
    }


    //打电话
    public void callPhone(View view, String phoneNumber) {
        Uri data = Uri.parse(phoneNumber);
        String action = Intent.ACTION_DIAL;
        Intent intent = new Intent(action, data);
        startActivity(intent);
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
