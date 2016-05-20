package com.sxau.agriculture.utils;

import android.os.CountDownTimer;
import android.widget.Button;

import com.sxau.agriculture.agriculture.R;

/**
 * 按钮的倒计时工具类
 * @author  Yawen_Li on 2016/5/20.
 */
public class TimerCount extends CountDownTimer {
    private Button btn;

    public TimerCount(long millisInFuture, long countDownInterval, Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn = btn;
    }

    public TimerCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onFinish() {
        // TODO Auto-generated method stub
        btn.setClickable(true);
        btn.setBackgroundResource(R.drawable.login_button_shape);
        btn.setText("获取验证码");
    }

    @Override
    public void onTick(long arg0) {
        // TODO Auto-generated method stub
        btn.setClickable(false);
        btn.setBackgroundResource(R.drawable.login_unablebutton_shape);
        btn.setText(arg0 / 1000 + "秒后重新获取");
    }
}


