package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.QuestionData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * 网络请求问题数据的接口
 * @author 崔志泽
 */
public interface IQuestionList {
    @GET("questions")
    Call<ArrayList<QuestionData>> getQuestionList(@Header("X-AUTH-TOKEN") String authToken ,@Query("page") String page,@Query("pageSize") String pageSize,@Query("category") int category);
}
