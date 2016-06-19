package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.bean.TradeData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * 请求交易详情
 * Created by Administrator on 2016/5/27.
 */
public interface ITradeContent {
    @GET("trade/{id}")
    Call<TradeData> getTrade(@Header("X-AUTH-TOKEN") String authToken, @Path("id") int id);
}
