package com.sxau.agriculture.api;


import com.sxau.agriculture.bean.MyPersonalTrade;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Administrator on 2016/6/2.
 */
public interface IPersonalTrades {
    @GET("my/trades")
    Call<ArrayList<MyPersonalTrade>> getMessage(@Header("X-AUTH-TOKEN") String authToken);

}
