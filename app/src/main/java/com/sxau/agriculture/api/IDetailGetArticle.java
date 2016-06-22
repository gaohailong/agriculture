package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.HomeArticle;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by gaohailong on 2016/6/21.
 */
public interface IDetailGetArticle {
    @GET("article/{id}")
    Call<HomeArticle> getDetailArticleData(@Path("id") int id);
}
