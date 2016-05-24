package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.InfoData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/5/24.
 */
public interface IInfoTrade {
    @GET("trades")
    Call<ArrayList<InfoData>> getInfoTrades(@Query("page") String page,@Query("pageSize") String pageSize);
}
