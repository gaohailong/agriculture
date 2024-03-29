package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * 专家回答的接口
 *
 * @author 高海龙
 */
public interface IExpertAnswer {
    @POST("question/answer")
    Call<JsonObject> doAnswer(@Header("X-AUTH-TOKEN") String token, @Body Map map);
}
