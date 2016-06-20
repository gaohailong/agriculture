package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class TradeData implements Serializable{

    /**
     * id : 21
     * whenCreated : 1465389929000
     * whenUpdated : 1465389929000
     * title : hhy
     * description : gghg
     * user : {"id":7,"whenCreated":1465224391000,"whenUpdated":1465443137000,"email":"****","userType":"PUBLIC","address":"太谷","realName":"****","phone":"****","name":"Ghelen","avatar":null,"industry":"铲屎大军","scale":"老大了","lastIp":"****"}
     * clickCount : 0
     * likeCount : 0
     * endTime : null
     * tradeType : SUPPLY
     * category : null
     * tradeState : WAIT_AUDITED
     * images : string
     * fav : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String title;
    private String description;
    /**
     * id : 7
     * whenCreated : 1465224391000
     * whenUpdated : 1465443137000
     * email : ****
     * userType : PUBLIC
     * address : 太谷
     * realName : ****
     * phone : ****
     * name : Ghelen
     * avatar : null
     * industry : 铲屎大军
     * scale : 老大了
     * lastIp : ****
     */

    private UserBean user;
    private int clickCount;
    private int likeCount;
    private Object endTime;
    private String tradeType;
    private Object category;
    private String tradeState;
    private String images;
    private boolean fav;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public long getWhenUpdated() {
        return whenUpdated;
    }

    public void setWhenUpdated(long whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    public static class UserBean implements Serializable {
        private int id;
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
        private String lastIp;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getWhenCreated() {
            return whenCreated;
        }

        public void setWhenCreated(long whenCreated) {
            this.whenCreated = whenCreated;
        }

        public long getWhenUpdated() {
            return whenUpdated;
        }

        public void setWhenUpdated(long whenUpdated) {
            this.whenUpdated = whenUpdated;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getLastIp() {
            return lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }
    }
}
