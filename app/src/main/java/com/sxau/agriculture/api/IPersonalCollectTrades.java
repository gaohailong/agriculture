package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalCollectTrades;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Administrator on 2016/6/3.
 */
public interface IPersonalCollectTrades {
    @GET("user/favorite/trades")
    Call<ArrayList<MyPersonalCollectTrades>> getMessage(@Header("X-AUTH-TOKEN")String aunhTokn);

}
