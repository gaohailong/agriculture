package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 个人用户信息bean
 * @author 李秉龙
 */
public class MyPersonalCenter implements Serializable {

    /**
     * id : 0
     * name : string
     * address : string
     * avatar : string
     * industry : string
     * scale : string
     * userType : string
     * whenCreated : 0
     * whenUpdated : 0
     */

    private int id;
    private String name;
    private String address;
    private String avatar;
    private String industry;
    private String scale;
    private String userType;
    private int whenCreated;
    private int whenUpdated;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(int whenCreated) {
        this.whenCreated = whenCreated;
    }

    public int getWhenUpdated() {
        return whenUpdated;
    }

    public void setWhenUpdated(int whenUpdated) {
        this.whenUpdated = whenUpdated;
    }
}
