package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.QuestionData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 网络请求问题数据的接口
 * @author 崔志泽
 */
public interface IQuestionList {
    @GET("questions")
    Call<ArrayList<QuestionData>> getQuestionList();
}
