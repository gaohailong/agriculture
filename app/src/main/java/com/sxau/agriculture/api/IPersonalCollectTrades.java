package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalCollectTrades;
import com.sxau.agriculture.bean.MyPersonalTrade;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Administrator on 2016/6/3.
 */
public interface IPersonalCollectTrades {
    @GET("trades")
    Call<ArrayList<MyPersonalCollectTrades>> getMessage();

}
