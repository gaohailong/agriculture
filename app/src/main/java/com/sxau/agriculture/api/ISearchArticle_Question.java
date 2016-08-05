package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.QuestionData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * 搜索文章和问题接口
 * @author  Yawen_Li on 2016/8/5.
 */
public interface ISearchArticle_Question {
    @GET("articles")
    Call<ArrayList<HomeArticle>> getArticleList(@Query("keyWord") String keyword, @Query("page") String page, @Query("pageSize") String pageSize);

    @GET("questions")
    Call<ArrayList<QuestionData>> getQuestionList(@Query("keyWord") String keyword, @Query("page") String page, @Query("pageSize") String pageSize);
}
