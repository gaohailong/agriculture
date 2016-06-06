package com.sxau.agriculture.utils;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.bean.User;

/**
 * 返回用户存储在本地的AuthToken
 * @author  Yawen_Li on 2016/6/6.
 */
public class AuthTokenUtil {
    public static String authToken;
    private static ACache mCache = ACache.get(AgricultureApplication.getContext());

    private void findAuthToken(){
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        authToken = user.getAuthToken();
    }

    static String getAuthToken(){
        return authToken;
    }
}
