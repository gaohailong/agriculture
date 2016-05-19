package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.HomeArticle;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * 首页文章列表
 *
 * @author 高海龙
 */
public interface IHomeArticleList {
    @GET("articles")
    Call<ArrayList<HomeArticle>> getArticleList(@Query("page") String page, @Query("pageSize") String pageSize);
}
