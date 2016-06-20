package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 首页文章的实体类
 *
 * @author 高海龙
 */
public class HomeArticle implements Serializable {

    /**
     * id : 10
     * whenCreated : 1465200424000
     * whenUpdated : 1466386938349
     * title : 中国新鲜荔枝首次对韩国出口
     * content : <p>5月30日，记者从漳州检验检疫局获悉，作为唯一允许输韩荔枝的地区，漳州今年计划输韩荔枝300吨，货值达120万美元。首批荔枝试水400千克，经韩国动植物检疫局植物检疫官和漳州检验检疫局植检人员共同预检后，于5月24日从福建漳州发往韩国。</p><p>　　漳州检验检疫局多措并举，全力推进中国新鲜荔枝首次对韩出口。以开拓市场，促进出口为导向，该局积极推动质检总局与韩国进行磋商，在2015年8月，终于达成中国荔枝输韩植物检验检疫协定。中国新鲜荔枝准入谈判终获成功，允许漳州荔枝出口韩国，这是中韩自贸协定实施以来，首个获得韩方开放市场的农产品。</p><p>　　据了解，荔枝是我国南方地区种植的特色水果，年产量超过125万吨，占全世界产量的50%以上，主要出口日本、东南亚、美国和欧盟等国家和地区。漳州在全国荔枝出口占有重要地位，是唯一允许输日荔枝的地区。此次，获准输韩是在对日美出口之后，再为漳州荔枝出口开拓全新的高端市场。</p>
     * tag : null
     * category : {"id":6,"whenCreated":1465195147000,"whenUpdated":1465195147000,"pid":1,"name":"国内农业","categoryType":"ARTICLE","image":"null","sort":1}
     * admin : {"id":2,"whenCreated":1465194560000,"whenUpdated":1465238855000,"name":"fan","email":"357239369@qq.com","phone":"18404968728","lastIp":"60.223.239.6"}
     * sort : 10
     * user : null
     * clickCount : 1
     * commentCount : 0
     * image : null
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
     * id : 6
     * whenCreated : 1465195147000
     * whenUpdated : 1465195147000
     * pid : 1
     * name : 国内农业
     * categoryType : ARTICLE
     * image : null
     * sort : 1
     */

    private CategoryBean category;
    /**
     * id : 2
     * whenCreated : 1465194560000
     * whenUpdated : 1465238855000
     * name : fan
     * email : 357239369@qq.com
     * phone : 18404968728
     * lastIp : 60.223.239.6
     */

    private AdminBean admin;
    private int sort;
    private User user;
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

    public void setUser(User user) {
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

    public static class AdminBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String name;
        private String email;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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
