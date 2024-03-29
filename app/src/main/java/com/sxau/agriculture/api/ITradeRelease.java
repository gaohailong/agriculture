package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * 发布交易api
 * @author 高海龙
 */
public interface ITradeRelease {
    @POST("trade")
    Call<JsonObject> postTrade(@Body Map map,@Header("X-AUTH-TOKEN") String authToken);
}
