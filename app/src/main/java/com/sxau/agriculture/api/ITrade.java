package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.TradeData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Administrator on 2016/5/25.
 */
public interface ITrade {
    @GET("trades")
    Call<ArrayList<TradeData>> getInfoTrade(@Query("page") String page,@Query("pageSize") String pageSize);
}
