package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/6/6.
 */
public interface ITradeRelease {
    @POST("trade")
    Call<JsonObject> postTrade(@Body Map map,@Header("X-AUTH-TOKEN") String authToken);
}
