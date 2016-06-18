package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 个人收藏交易bean
 * @author 李秉龙
 */
public class MyPersonalCollectTrades implements Serializable{

    /**
     * id : 6
     * whenCreated : 1465214194000
     * whenUpdated : 1465214194000
     * trade : {"id":1,"whenCreated":1465197226000,"whenUpdated":1465214194000,"title":"山东西瓜大量上市产地直销质优价廉","description":"山东省龙腾水果批发基地位于沂水县东南部，距县城14公里，地处三县(沂水、莒县、沂南)两市(临沂、日照)交界。京沪高速，京福高速，济青南线，日东高速，到本地均不到四十分钟，交通便利。水果产地自2002年始被誉为\u201c山东水果之乡\u201d之称。果园面积达30万亩，水果年产量600万吨，我处地理位置优越，货源充足，质优价廉，保证 让广大新老客户高兴而来，满意而归！！！【大棚西瓜 】大棚西瓜5-7月上市品种有：京欣、鲁青、风光、双星、小兰、黑美人、冠龙，郑抗系列等","user":{"id":2,"whenCreated":1465196978000,"whenUpdated":1465205309000,"email":null,"userType":"PUBLIC","address":"山西太谷","realName":"田帅","phone":"18404982762","name":"大ttttttt","avatar":null,"industry":"","scale":"","lastIp":"60.223.239.7"},"clickCount":1,"likeCount":2,"endTime":null,"tradeType":"SUPPLY","category":{"id":17,"whenCreated":1465195541000,"whenUpdated":1465195541000,"pid":5,"name":"农作物","categoryType":"ARTICLE","image":"null","sort":1},"tradeState":"WAIT_AUDITED","images":"","fav":false}
     * user : {"id":6,"whenCreated":1465212843000,"whenUpdated":1465212881000,"email":null,"userType":"PUBLIC","address":"shanxiyuci","realName":"libinglong","phone":"13133443006","name":"yuzestar","avatar":null,"industry":"huahui","scale":"dachangye","lastIp":"60.223.239.6"}
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 1
     * whenCreated : 1465197226000
     * whenUpdated : 1465214194000
     * title : 山东西瓜大量上市产地直销质优价廉
     * description : 山东省龙腾水果批发基地位于沂水县东南部，距县城14公里，地处三县(沂水、莒县、沂南)两市(临沂、日照)交界。京沪高速，京福高速，济青南线，日东高速，到本地均不到四十分钟，交通便利。水果产地自2002年始被誉为“山东水果之乡”之称。果园面积达30万亩，水果年产量600万吨，我处地理位置优越，货源充足，质优价廉，保证 让广大新老客户高兴而来，满意而归！！！【大棚西瓜 】大棚西瓜5-7月上市品种有：京欣、鲁青、风光、双星、小兰、黑美人、冠龙，郑抗系列等
     * user : {"id":2,"whenCreated":1465196978000,"whenUpdated":1465205309000,"email":null,"userType":"PUBLIC","address":"山西太谷","realName":"田帅","phone":"18404982762","name":"大ttttttt","avatar":null,"industry":"","scale":"","lastIp":"60.223.239.7"}
     * clickCount : 1
     * likeCount : 2
     * endTime : null
     * tradeType : SUPPLY
     * category : {"id":17,"whenCreated":1465195541000,"whenUpdated":1465195541000,"pid":5,"name":"农作物","categoryType":"ARTICLE","image":"null","sort":1}
     * tradeState : WAIT_AUDITED
     * images :
     * fav : false
     */

    private TradeBean trade;
    /**
     * id : 6
     * whenCreated : 1465212843000
     * whenUpdated : 1465212881000
     * email : null
     * userType : PUBLIC
     * address : shanxiyuci
     * realName : libinglong
     * phone : 13133443006
     * name : yuzestar
     * avatar : null
     * industry : huahui
     * scale : dachangye
     * lastIp : 60.223.239.6
     */

    private UserBean user;

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

    public TradeBean getTrade() {
        return trade;
    }

    public void setTrade(TradeBean trade) {
        this.trade = trade;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class TradeBean implements Serializable {
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String title;
        private String description;
        /**
         * id : 2
         * whenCreated : 1465196978000
         * whenUpdated : 1465205309000
         * email : null
         * userType : PUBLIC
         * address : 山西太谷
         * realName : 田帅
         * phone : 18404982762
         * name : 大ttttttt
         * avatar : null
         * industry :
         * scale :
         * lastIp : 60.223.239.7
         */

        private UserBean user;
        private int clickCount;
        private int likeCount;
        private Object endTime;
        private String tradeType;
        /**
         * id : 17
         * whenCreated : 1465195541000
         * whenUpdated : 1465195541000
         * pid : 5
         * name : 农作物
         * categoryType : ARTICLE
         * image : null
         * sort : 1
         */

        private CategoryBean category;
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

        public boolean isFav() {
            return fav;
        }

        public void setFav(boolean fav) {
            this.fav = fav;
        }

        public static class UserBean implements Serializable{
            private int id;
            private long whenCreated;
            private long whenUpdated;
            private Object email;
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

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
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

        public static class CategoryBean implements Serializable {
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

    public static class UserBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private Object email;
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

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
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

