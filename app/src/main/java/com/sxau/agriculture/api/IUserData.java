package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.User;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * 获取用户信息接口
 * @author  Yawen_Li on 2016/6/6.
 */
public interface IUserData {
    @GET("curuser")
    Call<User> getUserData(@Header("X-AUTH-TOKEN") String authToken);
}
