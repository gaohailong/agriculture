package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
import com.sxau.agriculture.bean.User;

import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * 获取上传token
 */
public interface IUploadToken {
    @GET("user/uploadToken")
    Call<JsonObject> getUploadToken(@Header("X-AUTH-TOKEN") String authToken);

    @GET("user/uploadTokenForAudio")
    Call<JsonObject> getUploadTokenForAudio(@Header("X-AUTH-TOKEN") String authToken);

}
