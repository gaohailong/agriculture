package com.sxau.agriculture.bean;

import java.util.List;

/**
 * 问题数据的实体类
 * @author 崔志泽
 */
public class QuestionData {

    /**
     * id : 10
     * whenCreated : 1464264153000
     * whenUpdated : 1464264153000
     * category : {"id":11,"whenCreated":1463620170000,"whenUpdated":1463620170000,"pid":10,"name":"农作物","categoryType":"ARTICLE","image":"images/test.png","sort":255}
     * title : 水稻叶子发黄怎么处理？
     * content : 水稻叶子发黄怎么处理？ 图片是我家水稻的情况，请看看。
     * clickCount : 0
     * likeCount : 0
     * expert : null
     * user : {"id":1,"whenCreated":1463621389000,"whenUpdated":1464861953000,"userType":"PUBLIC","address":null,"name":"guodont","avatar":null,"industry":null,"scale":null}
     * questionAuditState : WAIT_AUDITED
     * questionResolveState : WAIT_RESOLVE
     * images : o_1ajmf2vo97ldoaqnb71mjhqs47.jpg,o_1ajmf38l017u91s1kf9b1afm1ds4c.jpg,
     * answers : []
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 11
     * whenCreated : 1463620170000
     * whenUpdated : 1463620170000
     * pid : 10
     * name : 农作物
     * categoryType : ARTICLE
     * image : images/test.png
     * sort : 255
     */

    private CategoryBean category;
    private String title;
    private String content;
    private int clickCount;
    private int likeCount;
    private Object expert;
    /**
     * id : 1
     * whenCreated : 1463621389000
     * whenUpdated : 1464861953000
     * userType : PUBLIC
     * address : null
     * name : guodont
     * avatar : null
     * industry : null
     * scale : null
     */

    private UserBean user;
    private String questionAuditState;
    private String questionResolveState;
    private String images;
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

    public List<?> getAnswers() {
        return answers;
    }

    public void setAnswers(List<?> answers) {
        this.answers = answers;
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
}
