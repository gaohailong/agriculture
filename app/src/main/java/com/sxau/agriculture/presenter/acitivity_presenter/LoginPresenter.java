package com.sxau.agriculture.presenter.acitivity_presenter;


import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.api.IUserData;
import com.sxau.agriculture.bean.User;
import com.sxau.agriculture.presenter.activity_presenter_interface.ILoginPresenter;
import com.sxau.agriculture.utils.ACache;
import com.sxau.agriculture.utils.ConstantUtil;
import com.sxau.agriculture.utils.JPushUtil;
import com.sxau.agriculture.utils.LogUtil;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity.MainActivity;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.security.auth.callback.Callback;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 登录逻辑处理类
 *
 * @author Yawen_Li on 2016/4/19.
 */
public class LoginPresenter implements ILoginPresenter {
    private static final int MSG_SET_ALIAS = 1001;
    private static final String TAG = "JPush";
    private static int RESPONSE_SUCCESS = 200;              //请求成功返回编号
    private static int RESPONSE_FAILED = 400;               //请求失败返回编号
    private static long WAITTIME = 1500;                    //正式提交请求前等待时间
    private static boolean SHOWPROGRESS = true;             //显示progress
    private static boolean CLOSEPROGRESS = false;           //关闭progress
    private ILoginActivty iLoginActivty;
    private String strPhone;
    private String password;
    private long phone;
    private String authToken;
    private ACache mCache;

    public LoginPresenter(ILoginActivty iLoginActivty) {
        this.iLoginActivty = iLoginActivty;
        mCache = ACache.get(AgricultureApplication.getContext());
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
        boolean b = false;
        if (strPhone.length() == 11) {
            Pattern pattern = null;
            Matcher matcher = null;
            pattern = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
            matcher = pattern.matcher(strPhone);
            b = matcher.matches();
        }
        return b;
    }


    /**
     * 向服务器发送登录请求
     * 使用map对象将参数进行封装，注意，键值对的名称要与服务器端所要求的一致
     */
    @Override
    public void doLogin() {

        iLoginActivty.showProgress(SHOWPROGRESS);
        LogUtil.d("LoginPresenter", strPhone + "");
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
                            if (responseCode == RESPONSE_SUCCESS) {
                                JsonObject joResponseBody = response.body();
                                authToken = joResponseBody.get("authToken").getAsString();
                                //将数据封装成对象，方便缓存
                                Gson userGson = new Gson();
                                User user = new User();
                                user.setAuthToken(authToken);
                                user.setPhone(String.valueOf(phone));
                                user.setPhone(strPhone);
//                                setAlias(String.valueOf(phone));
                                //执行缓存
                                mCache.put(ConstantUtil.CACHE_KEY, user);
//                                setAlias();
                                //打印验证
                                String userJson = userGson.toJson(user);
                                LogUtil.d("RegisterP", userJson);

//                                //登录成功,跳转到主界面
//                                iLoginActivty.showProgress(CLOSEPROGRESS);
//                                iLoginActivty.showLoginSucceed();
//
//                                Intent intent = new Intent(AgricultureApplication.getContext(), MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                AgricultureApplication.getContext().startActivity(intent);
//                                //将登录页面finish掉
//                                iLoginActivty.finishLoginActivity();

                                //登录成功，开始请求用户信息
                                iLoginActivty.doRequestUserInfo();

                            } else {
                                //登录失败
                                iLoginActivty.showLoginFailed();
                                iLoginActivty.showProgress(CLOSEPROGRESS);
                            }
                            LogUtil.d("LoginPresenter", responseCode + "");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            iLoginActivty.showProgress(CLOSEPROGRESS);
                            iLoginActivty.showRequestTimeout();
                        }

                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void doRequestUserInfo() {
        Call call = RetrofitUtil.getRetrofit().create(IUserData.class).getUserData(authToken);
        call.enqueue(new retrofit.Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    User user = (User) response.body();
                    user.setAuthToken(authToken);
                    mCache.put(ConstantUtil.CACHE_KEY, user);

                    //打印验证一下  成功了
//                    user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
//                    LogUtil.e("LoginP","用户类型："+user.getUserType());

                    //登录成功,获取用户信息成功，跳转到主界面
                    iLoginActivty.showProgress(CLOSEPROGRESS);
                    iLoginActivty.showLoginSucceed();

                    Intent intent = new Intent(AgricultureApplication.getContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    AgricultureApplication.getContext().startActivity(intent);
                    //将登录页面finish掉
                    iLoginActivty.finishLoginActivity();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //获取用户信息失败
            }
        });
    }

    //--------------------------接口方法结束---------------------
    //JPush方法别名的设置
/*    private void setAlias(String phone) {
        if (!JPushUtil.isValidTagAndAlias(String.valueOf(phone))) {
            return;
        }
        //调用JPush API设置Alias
        handler.sendMessage(handler.obtainMessage(MSG_SET_ALIAS, phone));
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(iLoginActivty.getContext(), (String) msg.obj, null, mAliasCallback);
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (JPushUtil.isConnected(AgricultureApplication.getContext())) {
                        handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
            JPushUtil.showToast(logs, AgricultureApplication.getContext());
        }
    };*/
}
