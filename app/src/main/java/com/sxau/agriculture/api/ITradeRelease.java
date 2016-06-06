package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Yawen_Li on 2016/6/7.
 */
public interface ITradeRelease {
    @POST("trade")
    Call<JsonObject> doPostTradeRelease(@Body Map map);
}
