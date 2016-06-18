package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 个人收藏问题的bean
 * @author 李秉龙
 */
public class MyPersonalCollectionQuestion implements Serializable {

    /**
     * id : 1
     * whenCreated : 1465204869000
     * whenUpdated : 1465204869000
     * question : {"id":20,"whenCreated":1465204857000,"whenUpdated":1465204869000,"category":{"id":71,"whenCreated":1465198382000,"whenUpdated":1465198382000,"pid":0,"name":"种植","categoryType":"QUESTION","image":null,"sort":1},"title":"地膜花生扣膜时花生芽是否盖湿土","content":"地膜花生扣膜时花生芽是否盖湿土?","clickCount":0,"likeCount":1,"expert":null,"user":{"id":1,"whenCreated":1465196803000,"whenUpdated":1465212787000,"email":null,"userType":"EXPERT","address":null,"realName":null,"phone":"18404968725","name":"guodont","avatar":null,"industry":null,"scale":null,"lastIp":"60.223.239.6"},"questionAuditState":"WAIT_AUDITED","questionResolveState":"WAIT_RESOLVE","images":"","answer":null,"fav":false}
     * user : {"id":1,"whenCreated":1465196803000,"whenUpdated":1465212787000,"email":null,"userType":"EXPERT","address":null,"realName":null,"phone":"18404968725","name":"guodont","avatar":null,"industry":null,"scale":null,"lastIp":"60.223.239.6"}
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    /**
     * id : 20
     * whenCreated : 1465204857000
     * whenUpdated : 1465204869000
     * category : {"id":71,"whenCreated":1465198382000,"whenUpdated":1465198382000,"pid":0,"name":"种植","categoryType":"QUESTION","image":null,"sort":1}
     * title : 地膜花生扣膜时花生芽是否盖湿土
     * content : 地膜花生扣膜时花生芽是否盖湿土?
     * clickCount : 0
     * likeCount : 1
     * expert : null
     * user : {"id":1,"whenCreated":1465196803000,"whenUpdated":1465212787000,"email":null,"userType":"EXPERT","address":null,"realName":null,"phone":"18404968725","name":"guodont","avatar":null,"industry":null,"scale":null,"lastIp":"60.223.239.6"}
     * questionAuditState : WAIT_AUDITED
     * questionResolveState : WAIT_RESOLVE
     * images :
     * answer : null
     * fav : false
     */

    private QuestionBean question;
    /**
     * id : 1
     * whenCreated : 1465196803000
     * whenUpdated : 1465212787000
     * email : null
     * userType : EXPERT
     * address : null
     * realName : null
     * phone : 18404968725
     * name : guodont
     * avatar : null
     * industry : null
     * scale : null
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

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class QuestionBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        /**
         * id : 71
         * whenCreated : 1465198382000
         * whenUpdated : 1465198382000
         * pid : 0
         * name : 种植
         * categoryType : QUESTION
         * image : null
         * sort : 1
         */

        private CategoryBean category;
        private String title;
        private String content;
        private int clickCount;
        private int likeCount;
        private Object expert;
        /**
         * id : 1
         * whenCreated : 1465196803000
         * whenUpdated : 1465212787000
         * email : null
         * userType : EXPERT
         * address : null
         * realName : null
         * phone : 18404968725
         * name : guodont
         * avatar : null
         * industry : null
         * scale : null
         * lastIp : 60.223.239.6
         */

        private UserBean user;
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
            private Object image;
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

            public Object getImage() {
                return image;
            }

            public void setImage(Object image) {
                this.image = image;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }
        }

        public static class UserBean implements Serializable {
            private int id;
            private long whenCreated;
            private long whenUpdated;
            private Object email;
            private String userType;
            private String address;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
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

    public static class UserBean implements Serializable{
        private int id;
        private long whenCreated;
        private long whenUpdated;
        private Object email;
        private String userType;
        private String address;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
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