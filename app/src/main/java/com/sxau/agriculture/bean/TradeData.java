package com.sxau.agriculture.bean;

/**
 * Created by Administrator on 2016/4/9.
 */
public class TradeData {

    /**
     * id : 18
     * whenCreated : 1464091024000
     * whenUpdated : 1464091024000
     * title : 测试交供应0001
     * description : 测试交易供应0001
     * user : {"id":1,"whenCreated":1463621389000,"whenUpdated":1464090854000,"userType":"PUBLIC","address":null,"name":"guodont","avatar":null,"industry":null,"scale":null}
     * clickCount : 0
     * likeCount : 0
     * endTime : null
     * tradeType : SUPPLY
     * category : {"id":2,"whenCreated":1463620016000,"whenUpdated":1463620016000,"pid":1,"name":"科技前沿","categoryType":"ARTICLE","image":"images/test.png","sort":255}
     * tradeState : WAIT_AUDITED
     * images : image
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String title;
    private String description;
    /**
     * id : 1
     * whenCreated : 1463621389000
     * whenUpdated : 1464090854000
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
    private Object endTime;
    private String tradeType;
    /**
     * id : 2
     * whenCreated : 1463620016000
     * whenUpdated : 1463620016000
     * pid : 1
     * name : 科技前沿
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

    public static class UserBean {
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String userType;
        private Object address;
        private String name;
        private Object avatar;
        private Object industry;
        private Object scale;

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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
            this.avatar = avatar;
        }

        public Object getIndustry() {
            return industry;
        }

        public void setIndustry(Object industry) {
            this.industry = industry;
        }

        public Object getScale() {
            return scale;
        }

        public void setScale(Object scale) {
            this.scale = scale;
        }
    }

    public static class CategoryBean {
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
