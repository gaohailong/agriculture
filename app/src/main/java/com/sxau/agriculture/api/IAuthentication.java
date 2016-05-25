package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;
import com.sxau.agriculture.view.activity.MainActivity;

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
     * （String）userName
     * （String）password
     * （long）phone
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


    /**
     *
     * @param map
     * 封装在HashMap  对象中
     *   （String）password
     *   （long）phone
     * @return
     * response body
     *      登录成功    （String）authToken   注意当用户每次登陆都会刷新token，所以要做好token的缓存以及刷新
     *      登录失败    （String）"error": {  "message": "Incorrect phone or password" }
     * response code
     *      登录成功    （int）200
     *      登录失败    （int）400
     */
    @POST("login")
    Call<JsonObject> doLogin(@Body Map map);
}
