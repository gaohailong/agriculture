package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
import com.sxau.agriculture.bean.User;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * 获取用户信息接口
 * @author  Yawen_Li on 2016/6/6.
 */
public interface IUserData {
    @GET("curuser")
    Call<User> getUserData(@Header("X-AUTH-TOKEN") String authToken);

    @PUT("user/{id}")
    Call<JsonObject> upDataUserData(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") String id ,@Body Map map);
}
