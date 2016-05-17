package com.sxau.agriculture.api;

import com.squareup.okhttp.ResponseBody;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * 身份验证接口，包含登录验证、注册
 * @author  Yawen_Li on 2016/5/9.
 */
public interface IAuthentication {
    /**
     *注册第一步
     *
     * @param:
     * userName 用户名      大于3位 小于12位
     * password 密码        高于6位 低于45位
     * phone    电话号码    使用正则表达判断
     *
     * @return:
     * response body
     *      注册成功    （String）authToken   注意当用户每次登陆都会刷新token，所以要做好token的缓存以及刷新
     *      注册失败    （String）"error": { "message": "User exists" }
     * response code
     *      注册成功    （int） 201
     *      注册失败    （int） 400
     */
    @POST("api/v1/signup/one")
    Call<ResponseBody> doRegister(@Body Map body);

    /**
     * 注册第二步
     *
     *
     */


    @GET("api/v1/signup/one")
    Call<ResponseBody> getResult();
}
