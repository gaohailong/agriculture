package com.sxau.agriculture.utils;

import android.os.Handler;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * 网络请求的Util
 * @author 高海龙
 */
public class RetrofitUtil {

    public static Retrofit test() {
        //1.创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())//解析方法
                .baseUrl(ConstantUtil.BASE_URL)//主机地址
                .build();
        return retrofit;
        /*//2.创建访问API的请求
        serviceClass = retrofit.create(serviceClass.class);
        Call<beanClass> call = serviceClass.getResult();
        //3.发送请求
        call.enqueue(new Callback<beanClass>() {
            @Override
            public void onResponse(Response<beanClass> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    beanClass = response.body();
                    if (beanClass != null) {
                        beanClass
                        //  messageInfos = messageList.getMessageInfo();
                        handler.sendEmptyMessage(1);
                    }
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
            }
        });*/
    }


}
