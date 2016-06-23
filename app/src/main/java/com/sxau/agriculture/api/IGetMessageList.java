package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MessageInfo;
import com.sxau.agriculture.bean.MessageList;

import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;

/**
 * 消息界面的api
 *
 * @author 高海龙
 */
public interface IGetMessageList {
    @GET("messages")
    Call<ArrayList<MessageInfo>> getMessage(@Header("X-AUTH-TOKEN") String authToken, @Query("page") String page, @Query("pageSize") String pageSize);
}
