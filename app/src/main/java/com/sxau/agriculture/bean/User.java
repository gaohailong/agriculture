package com.sxau.agriculture.bean;

/**
 * 用户实体
 * @author  Yawen_Li on 2016/5/17.
 */
public class User {
    /**
     * password : string
     * userName : string
     * phone : long
     *
     * authToken : string
     *
     * address : string
     * realName : string
     * industry : string
     * scale : string
     */

    private String password;
    private String userName;
    private long phone;

    private String authToken;

    private String address;
    private String realName;
    private String industry;
    private String scale;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public long getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getAddress() {
        return address;
    }

    public String getRealName() {
        return realName;
    }

    public String getIndustry() {
        return industry;
    }

    public String getScale() {
        return scale;
    }
}
