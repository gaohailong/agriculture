package com.sxau.agriculture.bean;

import java.util.List;

/**
 * 文章详情的bean
 *
 * @author 高海龙
 */
public class DetailQuestionData {

    /**
     * id : 1
     * whenCreated : 1465030811000
     * whenUpdated : 1465172548000
     * category : {"id":17,"whenCreated":1465023858000,"whenUpdated":1465023858000,"pid":15,"name":"果树","categoryType":"ARTICLE","image":"null","sort":255}
     * title : 测试问题001
     * content : 测试问题001测试问题001
     * clickCount : 10
     * likeCount : 0
     * expert : {"id":3,"whenCreated":1465030758000,"whenUpdated":1465128255000,"email":null,"userType":"EXPERT","address":null,"realName":null,"phone":"18404968725","name":"guodong","avatar":null,"industry":null,"scale":null,"lastIp":"60.223.239.9"}
     * user : {"id":3,"whenCreated":1465030758000,"whenUpdated":1465128255000,"email":null,"userType":"EXPERT","address":null,"realName":null,"phone":"18404968725","name":"guodong","avatar":null,"industry":null,"scale":null,"lastIp":"60.223.239.9"}
     * questionAuditState : WAIT_AUDITED
     * questionResolveState : RESOLVED
     * images :
     * answer :
     * fav : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 17
     * whenCreated : 1465023858000
     * whenUpdated : 1465023858000
     * pid : 15
     * name : 果树
     * categoryType : ARTICLE
     * image : null
     * sort : 255
     */

    private CategoryBean category;
    private String title;
    private String content;
    private int clickCount;
    private int likeCount;
    /**
     * id : 3
     * whenCreated : 1465030758000
     * whenUpdated : 1465128255000
     * email : null
     * userType : EXPERT
     * address : null
     * realName : null
     * phone : 18404968725
     * name : guodong
     * avatar : null
     * industry : null
     * scale : null
     * lastIp : 60.223.239.9
     */

    private ExpertBean expert;
    /**
     * id : 3
     * whenCreated : 1465030758000
     * whenUpdated : 1465128255000
     * email : null
     * userType : EXPERT
     * address : null
     * realName : null
     * phone : 18404968725
     * name : guodong
     * avatar : null
     * industry : null
     * scale : null
     * lastIp : 60.223.239.9
     */

    private UserBean user;
    private String questionAuditState;
    private String questionResolveState;
    private String images;
    private boolean fav;
    private String answer;

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

    public ExpertBean getExpert() {
        return expert;
    }

    public void setExpert(ExpertBean expert) {
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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

    public static class ExpertBean {
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private Object email;
        private String userType;
        private Object address;
        private Object realName;
        private String phone;
        private String name;
        private Object avatar;
        private Object industry;
        private Object scale;
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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
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

        public String getLastIp() {
            return lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }
    }

    public static class UserBean {
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private Object email;
        private String userType;
        private Object address;
        private Object realName;
        private String phone;
        private String name;
        private String avatar;
        private Object industry;
        private Object scale;
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

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getRealName() {
            return realName;
        }

        public void setRealName(Object realName) {
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

        public String getLastIp() {
            return lastIp;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }
    }
}
