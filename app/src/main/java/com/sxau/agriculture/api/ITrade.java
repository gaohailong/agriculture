package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.TradeData;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * @author 田帅
 * 请求交易数据
 */
public interface ITrade {
    @GET("trades")
    Call<ArrayList<TradeData>> getInfoTrade(@Header("X-AUTH-TOKEN") String authToken,@Query("page") String page,@Query("pageSize") String pageSize);
}
