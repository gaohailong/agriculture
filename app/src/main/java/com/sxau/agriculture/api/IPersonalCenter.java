package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalCenter;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Administrator on 2016/6/4.
 */
public interface IPersonalCenter {
    @GET("user")
    Call<ArrayList<MyPersonalCenter>> getMessage(@Header("X-AUTH-TOKEN") String authToken);
}
