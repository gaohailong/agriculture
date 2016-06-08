package com.sxau.agriculture.api;

import org.json.JSONObject;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.PUT;
import retrofit.http.Part;

/**
 * Created by Administrator on 2016/6/7.
 */
public interface ICollectionSuccess {
    @Multipart
    @PUT("trade/{id}/fav")
    Call<JSONObject> postCollection(@Part("id") int id,@Header("X-AUTH-TOKEN") String authToken);
}
