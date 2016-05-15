package com.sxau.agriculture.api;

import com.sxau.agriculture.bean.MessageList;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 消息界面的api
 * @author 高海龙
 */
public interface IGetMessageList {
    @GET("/JsonTest/messageServlet?")
    Call<MessageList> getResult();
}
