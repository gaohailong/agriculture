package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.HomeRotatePicture;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 轮播图片的api
 * @author 高海龙
 */
public interface IHomeRotatePicture {
    @GET("/JsonTest/homeServlet?")
    Call<HomeRotatePicture> getResult();
}
