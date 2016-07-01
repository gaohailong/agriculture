package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.ExpertArticles;
import com.sxau.agriculture.bean.HomeArticle;
import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Part;
import retrofit.http.Path;

/**
 * 专家接口
 * 包括指派给专家的问题、专家的文章
 * Created by Yawen_Li on 2016/7/1.
 */
public interface IExpert {
    @GET("expert/{id}/questions")
    Call<ArrayList<MyPersonalQuestion>> getExpertQuestions(@Path("id") String id);

    @GET("expert/{id}/articles")
    Call<ArrayList<HomeArticle>> getExpertArticles(@Path("id") String id);
}
