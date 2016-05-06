package com.sxau.agriculture.view.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.sxau.agriculture.utils.ActivityCollectorUtil;

/**
 * 基本的activity
 * @author 高海龙
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollectorUtil.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollectorUtil.removeActivity(this);
    }
}
