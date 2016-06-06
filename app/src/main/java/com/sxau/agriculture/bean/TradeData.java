package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/4/9.
 */
public class TradeData implements Serializable{


    /**
     * id : 0
     * title : string
     * description : string
     * tradeType : string
     * tradeState : string
     * clickCount : 0
     * likeCount : 0
     * endTime : 0
     * images : string
     * whenCreated : 0
     * whenUpdated : 0
     * user : {"id":0,"name":"string","address":"string","avatar":"string","industry":"string","scale":"string","userType":"string","whenCreated":0,"whenUpdated":0}
     * category : {"id":0,"pid":0,"name":"string","categoryType":"string","image":"string","sort":0,"whenCreated":0,"whenUpdated":0}
     */

    private int id;
    private String title;
    private String description;
    private String tradeType;
    private String tradeState;
    private int clickCount;
    private int likeCount;
    private int endTime;
    private String images;
    private int whenCreated;
    private int whenUpdated;
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

    private UserBean user;
    /**
     * id : 0
     * pid : 0
     * name : string
     * categoryType : string
     * image : string
     * sort : 0
     * whenCreated : 0
     * whenUpdated : 0
     */

    private CategoryBean category;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeState() {
        return tradeState;
    }

    public void setTradeState(String tradeState) {
        this.tradeState = tradeState;
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

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public static class UserBean implements Serializable{
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

    public static class CategoryBean implements Serializable{
        private int id;
        private int pid;
        private String name;
        private String categoryType;
        private String image;
        private int sort;
        private int whenCreated;
        private int whenUpdated;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryType() {
            return categoryType;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
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
}
