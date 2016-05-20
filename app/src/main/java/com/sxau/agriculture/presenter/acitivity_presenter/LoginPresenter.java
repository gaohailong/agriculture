package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Intent;
import android.util.Log;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.presenter.activity_presenter_interface.ILoginPresenter;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 登录逻辑处理类
 *
 * @author Yawen_Li on 2016/4/19.
 */
public class LoginPresenter implements ILoginPresenter {

    private static long WAITTIME = 1500;
    private ILoginActivty iLoginActivty;
    private String strPhone;
    private String password;
    private long phone;
    private String authToken;

    public LoginPresenter(ILoginActivty iLoginActivty) {
        this.iLoginActivty = iLoginActivty;
    }


//--------------------接口方法开始--------------------

    @Override
    public void initData() {
        password = iLoginActivty.getPassword();
        strPhone = iLoginActivty.getPhone();
    }

    /**
     * 验证密码格式是否符合要求
     *
     * @return true or false
     */
    @Override
    public boolean isPasswordEnable() {
        if (password.length() > 5 && password.length() < 45) {
            return true;
        } else
            return false;
    }

    /**
     * 验证电话号码是否符合格式
     *
     * @return true or false
     */
    @Override
    public boolean isPhoneEnable() {
        Pattern pattern = null;
        Matcher matcher = null;
        boolean b = false;
        pattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        matcher = pattern.matcher(strPhone);
        b = matcher.matches();
        return b;
    }


    /**
     * 向服务器发送登录请求
     * 使用map对象将参数进行封装，注意，键值对的名称要与服务器端所要求的一致
     */
    @Override
    public void doLogin() {

        iLoginActivty.showProgress(true);
        LogUtil.d("LoginPresenter",strPhone+"");
        phone = Long.parseLong(strPhone);

        /**
         * 要实现progressdialog至少持续1.5秒，需要将请求延迟1.5秒后才发起请求，同时还不可以阻塞上级线程
         * 所以要new出新的线程来完成sleep操作。
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(WAITTIME);

                    Map map = new HashMap();
                    map.put("password", password);
                    map.put("phone", phone);

                    Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).doLogin(map);
                    call.enqueue(new retrofit.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                            int responseCode = response.code();
                            if (responseCode == 200) {
                                JsonObject joResponseBody = response.body();
                                authToken = joResponseBody.get("authToken").getAsString();
                                LogUtil.d("ResponseP", authToken + "");
                                ///////////////////////////////////////////////////
                                //还没有将authToken进行缓存，需要等缓存部分做好以后才能进行下一步。

                                //登录成功,跳转到主界面
                                iLoginActivty.showProgress(false);
                                iLoginActivty.showLoginSucceed();
                                Intent intent = new Intent(AgricultureApplication.getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                AgricultureApplication.getContext().startActivity(intent);
                                //将登录页面finish掉
                                iLoginActivty.finishLoginActivity();
                            } else {
                                //登录失败
                                iLoginActivty.showLoginFailed();
                                iLoginActivty.showProgress(false);
                            }
                            LogUtil.d("LoginPresenter", responseCode + "");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            iLoginActivty.showProgress(false);
                            iLoginActivty.showRequestTimeout();
                        }

                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
//--------------------------接口方法结束---------------------
}
