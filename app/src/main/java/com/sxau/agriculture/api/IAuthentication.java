package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;
import com.sxau.agriculture.view.activity.MainActivity;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * 身份验证接口，包含登录验证、注册
 * @author  Yawen_Li on 2016/5/9.
 */
public interface IAuthentication {
    /**
     * 注册接口
     * 注册第一步
     *
     * @param:
     * 封装在HashMap 对象中
     * （String）userName
     * （String）password
     * （long）phone
     * Header参数，包含短信验证码发回的header参数，用于验证操作
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
    Call<JsonObject> doRegister(@Body Map map ,@Header("VERIFY_UUID") String VERIFY_UUID);


    /**
     * 注册第二步
     */
    @POST("signup/two")
    Call<JsonObject> submitPersonalData(@Body Map map , @Header("X-AUTH-TOKEN") String authToken);


    /**
     *
     * 登录验证接口
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

    /**
     * 短信验证码接口
     * @param map
     * 需要的参数是电话号码
     *      （Long）phone
     * @return
     * response body
     *      "success":{"message":"验证短信发送成功"}
     * response code
     *      成功      （int）200
     */
    @POST("smsVerifyCode")
    Call<JsonObject> sendPhoneRequest(@Body Map map);

    /**
     * 退出请求接口
     * @param authToken
     * @return
     */
    @GET("logout")
    Call<JsonObject> doExitRequest(@Header("X-AUTH-TOKEN") String authToken);
}
