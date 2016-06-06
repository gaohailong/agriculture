package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MyPersonalCollectionQuestion;
import com.sxau.agriculture.bean.MyPersonalQuestion;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;

/**
 * Created by Administrator on 2016/5/18.
 */
public interface IPersonalCollectQuestion {
    @GET("my/favorite/questions")
    Call<ArrayList<MyPersonalCollectionQuestion>> getMessage(@Header("X-AUTH-TOKEN")String aunhTokn);
}
