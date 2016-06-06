package com.sxau.agriculture.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;

public class SplashActivity extends BaseActivity {
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ACache mCache = ACache.get(AgricultureApplication.getContext());
        String userData = mCache.getAsString(ConstantUtil.CACHE_KEY);
        Gson gson = new Gson();
        User user = gson.fromJson(userData, User.class);
        if (user != null) {
            authToken = user.getAuthToken();
        }
        handler.sendEmptyMessageDelayed(ConstantUtil.START_ACTIVITY, 3000);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ConstantUtil.START_ACTIVITY:
                    if (authToken != null) {
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    } else {
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    }
                    finish();
                    break;
            }
        }
    };
}
