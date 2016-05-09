package com.sxau.agriculture.api;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;

/**
 * 身份验证接口，包含登录验证、注册
 * @author  Yawen_Li on 2016/5/9.
 */
public interface IAuthentication {
    //接口还没弄好了，之后还要改这里
    @GET("/JsonTest/messageServlet?")
    Call<ResponseBody> getResult();
}
