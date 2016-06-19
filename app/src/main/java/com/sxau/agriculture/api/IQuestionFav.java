package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * 问题收藏/取消收藏接口
 * Created by Yawen_Li on 2016/6/19.
 */
public interface IQuestionFav {

    @PUT("question/{id}/fav")
    Call<JsonObject> doCollectQuestion(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") int id);

    @PUT("question/{id}/unFav")
    Call<JsonObject> doUnCollectQuestion(@Header("X-AUTH-TOKEN") String authToken ,@Path("id") int id);
}
