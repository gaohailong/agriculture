package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface IPersonalCollectQuestion {
    @GET("questions")
    Call<ArrayList<MyPersonalQuestion>> getMessage();
}
