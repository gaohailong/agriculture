package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.presenter.activity_presenter_interface.IRegisterPresenter;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.activity_interface.IRegisterActivity;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Yawen_Li on 2016/4/20.
 */
public class RegisterPresenter implements IRegisterPresenter {

    private static long WAITTIME = 1500;
    private IRegisterActivity iRegisterActivity;
    private String username;
    private String password;
    private String affirmpassword;
    private String strPhone;
    private long phone;
    private String authToken;


    public RegisterPresenter(IRegisterActivity iRegisterActivity) {
        this.iRegisterActivity = iRegisterActivity;
    }


//----------------------接口方法开始----------------------

    /**
     * 初始化数据
     */
    @Override
    public void initData() {
        username = iRegisterActivity.getUsername();
        password = iRegisterActivity.getPassword();
        affirmpassword = iRegisterActivity.getAffirmPassword();
        strPhone = iRegisterActivity.getPhone();
    }

    /**
     * 判断准则：
     * userName 用户名      大于3位 小于12位
     * password 密码        高于6位 低于45位
     * phone    电话号码    使用正则表达判断
     *
     */

    /**
     * 验证两次密码是否一致，且符合密码格式要求
     * @return true or false
     */
    @Override
    public boolean isPasswordSame() {
        if ((password.length() > 5 && password.length() < 45) && (password.length() == affirmpassword.length())){
            if (password.equals(affirmpassword)){
                return true;
            }else {
                return false;
            }
        }else
            return false;
    }

    /**
     * 验证密码格式是否符合要求
     * @return true or false
     */
    @Override
    public boolean isPasswordEnable() {
        if (password.length() > 5 && password.length() < 45 ){
            return true;
        }else
            return false;
    }

    /**
     * 验证用户名是否有效
     * @return true or false
     */
    @Override
    public boolean isUsernameEnable() {
        if (username.length() > 3 && username.length() < 12){
            return true;
        }else
            return false;
    }

    /**
     * 验证手机号码是否有效
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
     * 向服务器发送注册请求
     * 使用map对象将参数进行封装传递，请注意键-值对的名称要与服务器端一致
     */
    @Override
    public void doRegist() {

        iRegisterActivity.showProgress(true);
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
                    map.put("password",password);
                    map.put("userName",username);
                    map.put("phone", phone);
                    //打印输出一下JSON来看看
                    Gson gson = new Gson();
                    String jsonObject = gson.toJson(map);
                    LogUtil.d("RegisterP", jsonObject);

                    Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).doRegister(map);
                    call.enqueue(new retrofit.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                            int responseCode = response.code();
                            if (responseCode == 201) {
                                JsonObject joResponseBody = response.body();
                                authToken = joResponseBody.get("authToken").getAsString();
                                LogUtil.d("ResponseP", authToken + "");
                                ///////////////////////////////////////////////////
                                //还没有将authToken进行缓存，需要等缓存部分做好以后才能进行下一步。

                                //注册成功，跳转到主页面
                                iRegisterActivity.showRegisteSucceed();
                                iRegisterActivity.showProgress(false);
                                Intent intent = new Intent(AgricultureApplication.getContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                AgricultureApplication.getContext().startActivity(intent);
                                //将注册页面finish掉
                                iRegisterActivity.finishRegisterActivity();
                            } else {
                                //注册失败
                                iRegisterActivity.showRegistFailed();
                                iRegisterActivity.showProgress(false);
                            }
                            LogUtil.d("RegisterP", responseCode + "");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            iRegisterActivity.showRequestTimeout();
                            iRegisterActivity.showProgress(false);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
//----------------------接口方法结束---------------------
}
