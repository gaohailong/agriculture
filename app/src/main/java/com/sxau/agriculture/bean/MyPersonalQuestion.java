package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 个人中心问题bean
 * @author 李秉龙
 */
public class MyPersonalQuestion implements Serializable {

    /**
     * id : 24
     * whenCreated : 1465214682000
     * whenUpdated : 1465217176000
     * category : {"id":24,"whenCreated":1465196118000,"whenUpdated":1465196118000,"pid":0,"name":"农产加工","categoryType":"ARTICLE","image":"null","sort":8}
     * title : yuze个人中心测试发布的问题0001
     * content : yuze个人中心测试发布的问题0001
     * clickCount : 22
     * likeCount : 1
     * expert : null
     * user : {"id":6,"whenCreated":1465212843000,"whenUpdated":1465219196000,"email":null,"userType":"PUBLIC","address":"shanxiyuci","realName":"libinglong","phone":"13133443006","name":"yuzestar","avatar":null,"industry":"huahui","scale":"dachangye","lastIp":"60.223.239.6"}
     * questionAuditState : WAIT_AUDITED
     * questionResolveState : WAIT_RESOLVE
     * images :
     * answer : null
     * fav : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 24
     * whenCreated : 1465196118000
     * whenUpdated : 1465196118000
     * pid : 0
     * name : 农产加工
     * categoryType : ARTICLE
     * image : null
     * sort : 8
     */

    private CategoryBean category;
    private String title;
    private String content;
    private int clickCount;
    private int likeCount;
    private Object expert;
    /**
     * id : 6
     * whenCreated : 1465212843000
     * whenUpdated : 1465219196000
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

    private User user;
    private String questionAuditState;
    private String questionResolveState;
    private String images;
    private String answer;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
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
