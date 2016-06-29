package com.sxau.agriculture.bean;

/**
 * Created by gaohailong on 2016/6/29.
 */
public class Update {

    /**
     * url : http://storage.workerhub.cn/o_1ame77hob1qcr14idjn914tmct87.apk
     * versionCode : 2
     * updateMessage : 1.更新app
     2.高海龙
     */

    private String url;
    private int versionCode;
    private String updateMessage;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }
}
