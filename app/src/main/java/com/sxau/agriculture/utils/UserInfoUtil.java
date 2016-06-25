package com.sxau.agriculture.utils;

import android.util.Log;

import com.sxau.agriculture.AgricultureApplication;
import com.sxau.agriculture.bean.User;

import java.io.File;

/**
 * 返回用户存储在本地的AuthToken
 *
 * 返回用户的类型（专家/普通用户）
 *
 * @author Yawen_Li on 2016/6/6.
 */
public class UserInfoUtil {
    private static String authToken;
    private static String userType;
    private static String userAvatar;
    private static ACache mCache = ACache.get(AgricultureApplication.getContext());

    public static String findAuthToken() {
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (user != null) {
            authToken = user.getAuthToken();
        }
        return authToken;
    }

    public static boolean isUserTypeEXPERT() {
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (user != null) {
            userType = user.getUserType();
            if (userType.equals("EXPERT")){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public static String getUserAvatar(){
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (user != null && user.getAvatar() != null) {
            userAvatar = user.getAvatar();
            Log.e("UserIU","userAvatar:"+userAvatar);
            return userAvatar;
        }else {
            return null;
        }
    }

    public static String getUserType(){
        User user = (User) mCache.getAsObject(ConstantUtil.CACHE_KEY);
        if (user != null) {
            userType = user.getUserType();
        }else {
            userType = "isNull";
        }
        return userType;
    }
}
