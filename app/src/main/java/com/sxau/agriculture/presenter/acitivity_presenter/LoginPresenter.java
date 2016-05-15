package com.sxau.agriculture.presenter.acitivity_presenter;

import com.squareup.okhttp.ResponseBody;
import com.sxau.agriculture.api.IAuthentication;
import com.sxau.agriculture.presenter.activity_presenter_interface.ILoginPresenter;
import com.sxau.agriculture.utils.RetrofitUtil;
import com.sxau.agriculture.view.activity_interface.ILoginActivty;

import javax.security.auth.callback.Callback;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;


/**
 * 登录逻辑处理类
 * @author Yawen_Li on 2016/4/19.
 */
public class LoginPresenter implements ILoginPresenter {

    ILoginActivty iLoginActivty;
    private String username;
    private String password;

    public LoginPresenter(ILoginActivty iLoginActivty) {
        this.iLoginActivty = iLoginActivty;
    }

    @Override
    public void doLogin() {
        password = iLoginActivty.getPassword();
        username = iLoginActivty.getUsername();

        Call<ResponseBody> call = RetrofitUtil.getRetrofit().create(IAuthentication.class).getResult();
        call.enqueue(new retrofit.Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
