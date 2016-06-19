package com.sxau.agriculture.api;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

/**
 * 提问api
 * TODO 接口不对
 *
 * @author 高海龙
 */
public interface IAskQuestion {
    @POST("question")
    Call<String> sendQuestion(@Header("X-AUTH-TOKEN") String authToken, @Body Map map);
}
