package com.sxau.agriculture.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.agriculture.R;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.AuthTokenUtil;
import com.sxau.agriculture.utils.ConstantUtil;

import java.util.Random;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends BaseActivity {
    private String authToken;
    private Context context;
    private ImageView iv_spl_background;
    private static final int ANIMATION_DURATION = 3000;
    private static final float SCALE_END = 1.13F;
    private static final int[] SPLASH_ARRAY = {
            R.drawable.splash0,
            R.drawable.splash1,
            R.drawable.splash2,
            R.drawable.splash3,
            R.drawable.splash4,
            R.drawable.splash5,
            R.drawable.splash6,
            R.drawable.splash7,
            R.drawable.splash8,
            R.drawable.splash9,
            R.drawable.splash10,
            R.drawable.splash11,
            R.drawable.splash12,
            R.drawable.splash13,
            R.drawable.splash14,
            R.drawable.splash15,
            R.drawable.splash16,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        iv_spl_background = (ImageView) this.findViewById(R.id.iv_spl_background);
        Random r = new Random(SystemClock.elapsedRealtime());
        iv_spl_background.setImageResource(SPLASH_ARRAY[r.nextInt(SPLASH_ARRAY.length)]);
        context = SplashActivity.this;
        authToken = AuthTokenUtil.findAuthToken();
        animateImage();

    }
    private void animateImage() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(iv_spl_background, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(iv_spl_background, "scaleY", 1f, SCALE_END);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATION_DURATION).play(animatorX).with(animatorY);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
               // handler.sendEmptyMessageDelayed(ConstantUtil.START_ACTIVITY, 3000);
                handler.sendEmptyMessage(ConstantUtil.START_ACTIVITY);
            }
        });
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

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(context);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(context);
    }
}
