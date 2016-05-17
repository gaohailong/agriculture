package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
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
     * 封装在HashMap 对象中
     * userName  password  phone
     *
     * @return:
     * response body
     *      注册成功    （String）authToken   注意当用户每次登陆都会刷新token，所以要做好token的缓存以及刷新
     *      注册失败    （String）"error": { "message": "User exists" }
     * response code
     *      注册成功    （int） 201
     *      注册失败    （int） 400
     */
    @POST("signup/one")
    Call<JsonObject> doRegister(@Body Map map);

    /**
     * 注册第二步
     *
     *
     */


    @GET("api/v1/signup/one")
    Call<ResponseBody> getResult();
}
