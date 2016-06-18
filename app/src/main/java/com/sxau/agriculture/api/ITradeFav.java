package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * @author  Yawen_Li on 2016/6/14.
 * 交易收藏与取消收藏接口
 */
public interface ITradeFav {
    @PUT("trade/{id}/fav")
    Call<JsonObject> doCollectTrade(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") int id);

    @PUT("trade/{id}/unFav")
    Call<JsonObject> doUnCollectTrade(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") int id);
}
