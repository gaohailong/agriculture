package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 问题数据的实体类
 * @author 崔志泽
 */
public class QuestionData2 implements Serializable {


    /**
     * id : 61
     * whenCreated : 1466702271000
     * whenUpdated : 1466702343000
     * category : {"id":73,"whenCreated":1465198398000,"whenUpdated":1465198398000,"pid":0,"name":"信息","categoryType":"QUESTION","image":null,"sort":3}
     * title : 语音问题
     * content : 语音内容
     * clickCount : 1
     * likeCount : 0
     * expert : null
     * user : {"id":8,"whenCreated":1466656263000,"whenUpdated":1466698602000,"email":"****","userType":"EXPERT","address":"山西省太原市小店区","realName":"雅文","phone":"****","name":"yawen999","avatar":null,"industry":"软件工程","scale":"100人","lastIp":"****","weChatOpenId":null}
     * questionAuditState : AUDITED
     * questionResolveState : WAIT_RESOLVE
     * images :
     * mediaId : null
     * answer : null
     * fav : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 73
     * whenCreated : 1465198398000
     * whenUpdated : 1465198398000
     * pid : 0
     * name : 信息
     * categoryType : QUESTION
     * image : null
     * sort : 3
     */

    private CategoryEntity category;
    private String title;
    private String content;
    private int clickCount;
    private int likeCount;
    private String expert;
    /**
     * id : 8
     * whenCreated : 1466656263000
     * whenUpdated : 1466698602000
     * email : ****
     * userType : EXPERT
     * address : 山西省太原市小店区
     * realName : 雅文
     * phone : ****
     * name : yawen999
     * avatar : null
     * industry : 软件工程
     * scale : 100人
     * lastIp : ****
     * weChatOpenId : null
     */

    private UserEntity user;
    private String questionAuditState;
    private String questionResolveState;
    private String images;
    private String mediaId;
    private String answer;
    private boolean fav;

    public void setId(int id) {
        this.id = id;
    }

    public void setWhenCreated(long whenCreated) {
        this.whenCreated = whenCreated;
    }

    public void setWhenUpdated(long whenUpdated) {
        this.whenUpdated = whenUpdated;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setQuestionAuditState(String questionAuditState) {
        this.questionAuditState = questionAuditState;
    }

    public void setQuestionResolveState(String questionResolveState) {
        this.questionResolveState = questionResolveState;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
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

    public CategoryEntity getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getClickCount() {
        return clickCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public String getExpert() {
        return expert;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getQuestionAuditState() {
        return questionAuditState;
    }

    public String getQuestionResolveState() {
        return questionResolveState;
    }

    public String getImages() {
        return images;
    }

    public String getMediaId() {
        return mediaId;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isFav() {
        return fav;
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

    public static class UserEntity implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private String email;
        private String userType;
        private String address;
        private String realName;
        private String phone;
        private String name;
        private String avatar;
        private String industry;
        private String scale;
        private String lastIp;
        private String weChatOpenId;

        public void setId(int id) {
            this.id = id;
        }

        public void setWhenCreated(long whenCreated) {
            this.whenCreated = whenCreated;
        }

        public void setWhenUpdated(long whenUpdated) {
            this.whenUpdated = whenUpdated;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public void setScale(String scale) {
            this.scale = scale;
        }

        public void setLastIp(String lastIp) {
            this.lastIp = lastIp;
        }

        public void setWeChatOpenId(String weChatOpenId) {
            this.weChatOpenId = weChatOpenId;
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

        public String getEmail() {
            return email;
        }

        public String getUserType() {
            return userType;
        }

        public String getAddress() {
            return address;
        }

        public String getRealName() {
            return realName;
        }

        public String getPhone() {
            return phone;
        }

        public String getName() {
            return name;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getIndustry() {
            return industry;
        }

        public String getScale() {
            return scale;
        }

        public String getLastIp() {
            return lastIp;
        }

        public String getWeChatOpenId() {
            return weChatOpenId;
        }
    }
}
