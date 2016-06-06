package com.sxau.agriculture.utils;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.bean.User;

import java.io.File;

/**
 * 返回用户存储在本地的AuthToken
 *
 * @author Yawen_Li on 2016/6/6.
 */
public class AuthTokenUtil {
    private static String authToken;
    private static ACache mCache = ACache.get(AgricultureApplication.getContext());

    public static String findAuthToken() {
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (user != null) {
            authToken = user.getAuthToken();
        }
        return authToken;
    }
}
