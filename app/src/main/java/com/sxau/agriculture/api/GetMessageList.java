package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MessageList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 消息界面的api
 */
public interface GetMessageList {
    @GET("/JsonTest/messageServlet?")
    Call<MessageList> getResult();

}
