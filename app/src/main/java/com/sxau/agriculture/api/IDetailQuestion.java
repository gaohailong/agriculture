package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.DetailQuestionData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

/**
 * 获取问题详情的api
 * @author 高海龙
 */
public interface IDetailQuestion {
    @GET("question/{id}")
    Call<DetailQuestionData> getDetailQuestionData(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") String id);
}
