package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.CategorieData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 网络请求分类信息的接口
 * @author 崔志泽
 */
public interface ICategoriesData {
    @GET("categories")
    Call<ArrayList<CategorieData>> getCategories();
}
