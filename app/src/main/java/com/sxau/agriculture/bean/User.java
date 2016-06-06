package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 用户实体
 * @author  Yawen_Li on 2016/5/17.
 */
public class User implements Serializable{
<<<<<<< HEAD
=======

>>>>>>> 3289dd45af5b3d9150528dc90cbe8ea6d0e83cb1
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
        return avatar;
    }

    public String getIndustry() {
        return industry;
    }

    public String getScale() {
        return scale;
    }
}
