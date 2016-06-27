package com.sxau.agriculture.api;

import com.google.gson.JsonObject;
import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.bean.MessageList;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * 消息界面的api
 *
 * @author 高海龙
 */
public interface IGetMessageList {
    //获取信息列表
    @GET("messages")
    Call<ArrayList<MessageInfo>> getMessage(@Header("X-AUTH-TOKEN") String authToken, @Query("page") String page, @Query("pageSize") String pageSize);

    //修改已读未读
    @PUT("message/{id}")
    Call<JsonObject> changeReadeStatus(@Header("X-AUTH-TOKEN") String authToken, @Path("id") int id);
}
