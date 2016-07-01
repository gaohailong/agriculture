package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 专家文章实体类
 * 现在没有用到，留着也许以后会用到
 * Created by Yawen_Li on 2016/7/1.
 */
public class ExpertArticles implements Serializable{

    /**
     * id : 177
     * whenCreated : 1467215716000
     * whenUpdated : 1467215888000
     * title : 测试专家文章001
     * content : <p>测试专家文章001测试专家文章001<br></p>
     * tag : 测试专家文章001
     * category : {"id":49,"whenCreated":1465196545000,"whenUpdated":1465196545000,"pid":24,"name":"副食加工","categoryType":"ARTICLE","image":"null","sort":4}
     * admin : null
     * sort : null
     * user : {"id":2,"whenCreated":1466590152000,"whenUpdated":1467255160000,"email":null,"userType":"EXPERT","address":"未设置","realName":"范兆杰","phone":"18404968728","name":"农事易001","avatar":"FoCeRkLAO1KHV2oQ1tgX-O5F31qt","industry":"未设置","scale":"未设置","lastIp":"117.136.4.184","weChatOpenId":"o451ewGpwu4V3JLwzkOkOT9-hD7A"}
     * clickCount : 4
     * commentCount : 0
     * image : null
     * articleType : ARTICLE
     * articleState : WAIT_AUDITED
     * articlePushState : NO_PUSH
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String title;
    private String content;
    private String tag;
    /**
     * id : 49
     * whenCreated : 1465196545000
     * whenUpdated : 1465196545000
     * pid : 24
     * name : 副食加工
     * categoryType : ARTICLE
     * image : null
     * sort : 4
     */

    private CategoryEntity category;
    private Object admin;
    private Object sort;
    /**
     * id : 2
     * whenCreated : 1466590152000
     * whenUpdated : 1467255160000
     * email : null
     * userType : EXPERT
     * address : 未设置
     * realName : 范兆杰
     * phone : 18404968728
     * name : 农事易001
     * avatar : FoCeRkLAO1KHV2oQ1tgX-O5F31qt
     * industry : 未设置
     * scale : 未设置
     * lastIp : 117.136.4.184
     * weChatOpenId : o451ewGpwu4V3JLwzkOkOT9-hD7A
     */

    private User user;
    private int clickCount;
    private int commentCount;
    private Object image;
    private String articleType;
    private String articleState;
    private String articlePushState;

    public void setId(int id) {
        this.id = id;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public void setWhenUpdated(long whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setAdmin(Object admin) {
        this.admin = admin;
    }

    public void setSort(Object sort) {
        this.sort = sort;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public void setArticleState(String articleState) {
        this.articleState = articleState;
    }

    public void setArticlePushState(String articlePushState) {
        this.articlePushState = articlePushState;
    }

    public int getId() {
        return id;
    }

    public long getWhenCreated() {
        return whenCreated;
    }

    public long getWhenUpdated() {
        return whenUpdated;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTag() {
        return tag;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public Object getAdmin() {
        return admin;
    }

    public Object getSort() {
        return sort;
    }

    public User getUser() {
        return user;
    }

    public int getClickCount() {
        return clickCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public Object getImage() {
        return image;
    }

    public String getArticleType() {
        return articleType;
    }

    public String getArticleState() {
        return articleState;
    }

    public String getArticlePushState() {
        return articlePushState;
    }

    public static class CategoryEntity implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private int pid;
        private String name;
        private String categoryType;
        private String image;
        private int sort;

        public void setId(int id) {
            this.id = id;
        }

        public void setWhenCreated(long whenCreated) {
            this.whenCreated = whenCreated;
        }

        public void setWhenUpdated(long whenUpdated) {
            this.whenUpdated = whenUpdated;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setCategoryType(String categoryType) {
            this.categoryType = categoryType;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getId() {
            return id;
        }

        public long getWhenCreated() {
            return whenCreated;
        }

        public long getWhenUpdated() {
            return whenUpdated;
        }

        public int getPid() {
            return pid;
        }

        public String getName() {
            return name;
        }

        public String getCategoryType() {
            return categoryType;
        }

        public String getImage() {
            return image;
        }

        public int getSort() {
            return sort;
        }
    }
}
