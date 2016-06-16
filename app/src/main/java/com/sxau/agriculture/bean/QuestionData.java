package com.sxau.agriculture.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 问题数据的实体类
 * @author 崔志泽
 */
public class QuestionData implements Serializable {

    /**
     * id : 5
     * whenCreated : 1465123399000
     * whenUpdated : 1465128144000
     * category : {"id":38,"whenCreated":1465024109000,"whenUpdated":1465024109000,"pid":34,"name":"行业动态","categoryType":"ARTICLE","image":"null","sort":255}
     * title : 个人中心我的问题测试数据0002
     * content : 个人中心我的问题测试数据0002个人中心我的问题测试数据0002个人中心我的问题测试数据0002
     * clickCount : 0
     * likeCount : 0
     * expert : null
     * user : {"id":2,"whenCreated":1465029214000,"whenUpdated":1465136188000,"email":"****","userType":"PUBLIC","address":"shanxiyuci","realName":"****","phone":"****","name":"yuzestar\t","avatar":null,"industry":"huahui","scale":"xiaozuofang","lastIp":"****"}
     * questionAuditState : AUDITED
     * questionResolveState : RESOLVED
     * images :
     * answers : []
     * fav : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 38
     * whenCreated : 1465024109000
     * whenUpdated : 1465024109000
     * pid : 34
     * name : 行业动态
     * categoryType : ARTICLE
     * image : null
     * sort : 255
     */

    private CategoryBean category;
    private String title;
    private String content;
    private int clickCount;
    private int likeCount;
    private Object expert;
    /**
     * id : 2
     * whenCreated : 1465029214000
     * whenUpdated : 1465136188000
     * email : ****
     * userType : PUBLIC
     * address : shanxiyuci
     * realName : ****
     * phone : ****
     * name : yuzestar
     * avatar : null
     * industry : huahui
     * scale : xiaozuofang
     * lastIp : ****
     */

    private UserBean user;
    private String questionAuditState;
    private String questionResolveState;
    private String images;
    private boolean fav;
    private List<?> answers;

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

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Object getExpert() {
        return expert;
    }

    public void setExpert(Object expert) {
        this.expert = expert;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getQuestionAuditState() {
        return questionAuditState;
    }

    public void setQuestionAuditState(String questionAuditState) {
        this.questionAuditState = questionAuditState;
    }

    public String getQuestionResolveState() {
        return questionResolveState;
    }

    public void setQuestionResolveState(String questionResolveState) {
        this.questionResolveState = questionResolveState;
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

    public List<?> getAnswers() {
        return answers;
    }

    public void setAnswers(List<?> answers) {
        this.answers = answers;
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

    public static class UserBean  implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String email;
        private String userType;
        private String address;
        private String realName;
        private String phone;
        private String name;
        private Object avatar;
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

        public Object getAvatar() {
            return avatar;
        }

        public void setAvatar(Object avatar) {
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
