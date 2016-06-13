package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.TradeData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by czz on 2016/6/10.
 */
public interface IPersonTradeContent {
    @GET("trade/{id}")
    Call<TradeData> getTradeContent(@Path("id") int id);
}
