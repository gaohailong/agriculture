package com.sxau.agriculture.bean;

/**
 * 首页文章的实体类
 *
 * @author 高海龙
 */
public class HomeArticle {

    /**
     * id : 1
     * whenCreated : 1463157269000
     * whenUpdated : 1463402888000
     * title : testArticle01
     * content : testArticle01
     * tag : testArticle01
     * category : {"id":1,"whenCreated":1463156226000,"whenUpdated":1463156226000,"pid":0,"name":"test01","categoryType":"ARTICLE","image":"images/test.png","sort":255}
     * admin : {"id":1,"whenCreated":1463152818000,"whenUpdated":1463157214000,"name":"admin","phone":"18404968725","lastIp":"117.136.4.188"}
     * sort : 255
     * user : null
     * clickCount : 2
     * commentCount : 0
     * image : images/test.png
     * articleType : WEB
     * articleState : AUDITED
     * articlePushState : NO_PUSH
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String title;
    private String content;
    private String tag;
    /**
     * id : 1
     * whenCreated : 1463156226000
     * whenUpdated : 1463156226000
     * pid : 0
     * name : test01
     * categoryType : ARTICLE
     * image : images/test.png
     * sort : 255
     */

    private CategoryBean category;
    /**
     * id : 1
     * whenCreated : 1463152818000
     * whenUpdated : 1463157214000
     * name : admin
     * phone : 18404968725
     * lastIp : 117.136.4.188
     */

    private AdminBean admin;
    private int sort;
    private Object user;
    private int clickCount;
    private int commentCount;
    private String image;
    private String articleType;
    private String articleState;
    private String articlePushState;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CategoryBean getCategory() {
        return category;
    }

    public void setCategory(CategoryBean category) {
        this.category = category;
    }

    public AdminBean getAdmin() {
        return admin;
    }

    public void setAdmin(AdminBean admin) {
        this.admin = admin;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleState() {
        return articleState;
    }

    public void setArticleState(String articleState) {
        this.articleState = articleState;
    }

    public String getArticlePushState() {
        return articlePushState;
    }

    public void setArticlePushState(String articlePushState) {
        this.articlePushState = articlePushState;
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

    public static class AdminBean {
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String name;
        private String phone;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getLastIp() {
            return lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }
    }
}
