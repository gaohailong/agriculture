package com.sxau.agriculture.presenter.acitivity_presenter;

import android.content.Intent;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.IRegisterPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.NetUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.ImprovePersonalInfoActivity;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.activity_interface.IRegisterActivity;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * 注册操作的逻辑类
 * Created by Yawen_Li on 2016/4/20.
 */
public class RegisterPresenter implements IRegisterPresenter {

    private static long WAITTIME = 1500;                     //正式提交请求前等待时间
    private static int RESPONSE_SUCCESS = 201;               //请求成功返回编号
    private static int RESPONSE_FAILED = 400;                //请求失败返回编号
    private static boolean SHOWPROGRESS = true;             //显示progress
    private static boolean CLOSEPROGRESS = false;           //关闭progress
    private String VERIFY_UUID = new String();
    private IRegisterActivity iRegisterActivity;
    private String username;
    private String password;
    private String strPhone;
    private String checknum;
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
        strPhone = iRegisterActivity.getPhone();
        checknum = iRegisterActivity.getCheckNum();
    }

    /**
     * 判断准则：
     * userName 用户名      大于3位 小于12位
     * password 密码        高于6位 低于45位
     * phone    电话号码    使用正则表达判断
     *
     */

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
        if (username.length() > 2 && username.length() < 13){
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
        boolean b = false;
        if (strPhone.length() == 11){
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
            matcher = pattern.matcher(strPhone);
            b = matcher.matches();
        }
        return b;
    }

    /**
     * 验证一下验证码是否有效
     * @return true or false
     */
    @Override
    public boolean isCheckNumEnable() {
        if (checknum.length() == 4){
            return true;
        }else
            return false;
    }


    /**
     * 向服务器发送注册请求
     * 使用map对象将参数进行封装传递，请注意键-值对的名称要与服务器端一致
     */
    @Override
    public void doRegist() {

        iRegisterActivity.showProgress(SHOWPROGRESS);
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
                    map.put("verifyCode",checknum);

                    Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).doRegister(map,VERIFY_UUID);
                    call.enqueue(new retrofit.Callback<JsonObject>() {
                        @Override
                        public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                            int responseCode = response.code();
                            if (response.isSuccess()) {
                                JsonObject joResponseBody = response.body();
                                authToken = joResponseBody.get("authToken").getAsString();

                                //将数据封装成对象，方便缓存
                                Gson userGson = new Gson();
                                User user = new User();
                                user.setAuthToken(authToken);
                                user.setPhone(strPhone);
                                user.setName(username);
                                user.setUserType("PUBLIC");

                                //执行缓存
                                ACache mCache = ACache.get(AgricultureApplication.getContext());
                                mCache.put(ConstantUtil.CACHE_KEY,user);

                                //打印验证
                                LogUtil.d("RegisterP", userGson.toJson(user));

                                //注册成功，跳转到注册第二步界面
                                iRegisterActivity.showRegisteSucceed();
                                iRegisterActivity.showProgress(CLOSEPROGRESS);
                                Intent intent = new Intent(AgricultureApplication.getContext(), ImprovePersonalInfoActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                AgricultureApplication.getContext().startActivity(intent);
                                //将注册页面finish掉
                                iRegisterActivity.finishRegisterActivity();
                            } else {
                                //注册失败
                                iRegisterActivity.showRegistFailed();
                                iRegisterActivity.showProgress(CLOSEPROGRESS);
                            }
                            LogUtil.d("RegisterP", responseCode + "");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            iRegisterActivity.showRequestTimeout();
                            iRegisterActivity.showProgress(CLOSEPROGRESS);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 请求发送验证码操作
     */
    @Override
    public void sendPhoneRequest() {
        phone = Long.parseLong(strPhone);
        Map map = new HashMap();
        map.put("phone",phone);

        Call call = RetrofitUtil.getRetrofit().create(IAuthentication.class).sendPhoneRequest(map);
        call.enqueue(new retrofit.Callback<JsonObject>() {
            @Override
            public void onResponse(Response<JsonObject> response, Retrofit retrofit) {
                if (response.isSuccess()){
                    //验证码已发送
                    VERIFY_UUID = response.headers().get("VERIFY_UUID");
                    LogUtil.d("RegisterPresenter", "header：VERIFY_UUID : " + response.headers().get("VERIFY_UUID"));
                    LogUtil.d("RegisterPresenter", "验证码已经发送");
                }else {
                    //验证码发送出错
                    LogUtil.d("RegisterPersonter","header： "+response.headers().toString());
                    LogUtil.d("RegisterPresenter","电话号码："+strPhone);
                    LogUtil.d("RegisterPresenter","返回信息： "+response.message()+" 状态码： "+response.code()+"返回body："+response.body());
                    LogUtil.d("RegisterPresenter","验证码发送失败");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //请求发送验证码失败
                LogUtil.d("RegisterPersonter","请求发送验证码操作失败");
            }
        });
    }
//----------------------接口方法结束---------------------
}
