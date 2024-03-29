package com.sxau.agriculture.bean;

import android.util.Log;

import com.sxau.agriculture.utils.ConstantUtil;

import java.io.Serializable;

/**
 * 用户实体
 * @author  Yawen_Li on 2016/5/17.
 */
public class User implements Serializable{

    /**
     * whenCreated : 1465196803000
     * whenUpdated : 1465212787000
     * email : null
     * userType : EXPERT
     * address : null
     * realName : null
     * phone : 18404968725
     * name : guodont
     * avatar : null
     * industry : null
     * scale : null
     */

    private long whenCreated;
    private long whenUpdated;
    private String id;
    private String email;
    private String userType;
    private String address;
    private String realName;
    private String phone;
    private String name;
    private String avatar;
    private String industry;
    private String scale;
    private String authToken;
    private String lastIp;

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public void setWhenUpdated(long whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvatar(String avatar) {
        avatar = ConstantUtil.DOMAIN + avatar +ConstantUtil.UPLOAD_PIC_SUFFIX;
        Log.e("UserSet","avatar:"+avatar);
        this.avatar = avatar;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public long getWhenCreated() {
        return whenCreated;
    }

    public long getWhenUpdated() {
        return whenUpdated;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public String getAddress() {
        return address;
    }

    public String getRealName() {
        return realName;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        Log.e("UserGet","avatar:"+ConstantUtil.DOMAIN + avatar + ConstantUtil.UPLOAD_PIC_SUFFIX);
        return (ConstantUtil.DOMAIN + avatar + ConstantUtil.UPLOAD_PIC_SUFFIX);
    }

    public String getIndustry() {
        return industry;
    }

    public String getScale() {
        return scale;
    }
}
