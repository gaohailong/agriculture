package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.DetailQuestionData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * 获取文章详情的api
 * @author 高海龙
 */
public interface IDetailQuestion {
    @GET("question/{id}")
    Call<DetailQuestionData> getDetailQuestionData(@Path("id") String id);
}
