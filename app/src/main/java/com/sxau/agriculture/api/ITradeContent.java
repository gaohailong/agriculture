package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalTrade;
import com.sxau.agriculture.bean.TradeData;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Administrator on 2016/5/27.
 */
public interface ITradeContent {
    @GET("trade/{id}")
    Call<TradeData> getTrade(@Path("id") int id);
}
