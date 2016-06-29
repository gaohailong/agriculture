package com.sxau.agriculture.api;

import com.google.gson.JsonObject;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by gaohailong on 2016/6/29.
 */
public interface IVersionUpdate {
    @GET("app/checkUpdate")
    Call<JsonObject> getUpdateJson();
}
