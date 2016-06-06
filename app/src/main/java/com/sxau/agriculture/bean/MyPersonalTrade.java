package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 个人中心交易信息bean
 * @author 李秉龙
 */
public class MyPersonalTrade implements Serializable{

    /**
     * id : 21
     * whenCreated : 1464943374000
     * whenUpdated : 1464964730000
     * title : 测试供应信息0008
     * description : 测试供应信息0008测试供应信息0008测试供应信息0008
     * user : {"id":1,"whenCreated":1463621389000,"whenUpdated":1464943203000,"userType":"PUBLIC","address":null,"name":"guodont","avatar":null,"industry":null,"scale":null}
     * clickCount : 9
     * likeCount : 0
     * endTime : null
     * tradeType : SUPPLY
     * category : {"id":14,"whenCreated":1463620231000,"whenUpdated":1463620231000,"pid":10,"name":"瓜类","categoryType":"ARTICLE","image":"images/test.png","sort":255}
     * tradeState : WAIT_AUDITED
     * images : o_1akamrdhsi7s1m7n65912i91f1i7.jpg,o_1akamro53u9mqbj1nqdm2t31ic.png,
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String title;
    private String description;
    /**
     * id : 1
     * whenCreated : 1463621389000
     * whenUpdated : 1464943203000
     * userType : PUBLIC
     * address : null
     * name : guodont
     * avatar : null
     * industry : null
     * scale : null
     */

    private UserBean user;
    private int clickCount;
    private int likeCount;
    private String endTime;
    private String tradeType;
    /**
     * id : 14
     * whenCreated : 1463620231000
     * whenUpdated : 1463620231000
     * pid : 10
     * name : 瓜类
     * categoryType : ARTICLE
     * image : images/test.png
     * sort : 255
     */

    private CategoryBean category;
    private String tradeState;
    private String images;

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
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

    public static class UserBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String userType;
        private String address;
        private String name;
        private String avatar;
        private String industry;
        private String scale;

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
    }

    public static class CategoryBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private int pid;
        private String name;
        private String categoryType;
        private String image;
        private int sort;

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
    }
}
