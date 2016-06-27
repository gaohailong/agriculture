package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 消息页面的item数据bean
 *
 * @author 高海龙
 */
public class MessageInfo implements Serializable{
    /**
     * id : 3
     * whenCreated : 1466391653000
     * whenUpdated : 1466391653000
     * relationId : 49
     * messageType : TRADE
     * user : {"id":1,"whenCreated":1465196803000,"whenUpdated":1466437203000,"email":"5-10人2","userType":"EXPERT","address":"山西农业大学牧站2","realName":"郭栋2","phone":"18404968725","name":"guodont","avatar":null,"industry":"软件开发公司23","scale":"5-10人2","lastIp":"60.223.239.8"}
     * title : 您的交易信息已通过审核
     * remark : 12
     * markRead : false
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private int relationId;
    private String messageType;
    /**
     * id : 1
     * whenCreated : 1465196803000
     * whenUpdated : 1466437203000
     * email : 5-10人2
     * userType : EXPERT
     * address : 山西农业大学牧站2
     * realName : 郭栋2
     * phone : 18404968725
     * name : guodont
     * avatar : null
     * industry : 软件开发公司23
     * scale : 5-10人2
     * lastIp : 60.223.239.8
     */

    private User user;
    private String title;
    private String remark;
    private boolean markRead;

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

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isMarkRead() {
        return markRead;
    }

    public void setMarkRead(boolean markRead) {
        this.markRead = markRead;
    }

}