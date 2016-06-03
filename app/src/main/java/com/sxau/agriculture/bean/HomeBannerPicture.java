package com.sxau.agriculture.bean;

/**
 * Created by czz on 2016/6/1.
 */
public class HomeBannerPicture {

    /**
     * id : 4
     * whenCreated : 1464777121000
     * whenUpdated : 1464777121000
     * name : advertisements002
     * description : advertisements002
     * url : http://sxnk110.workerhub.cn/#/article/11
     * image : o_1ak5oa53s1qohq110i4i381n53l.png
     * clickCount : null
     * position : AMONG
     */

    private int id;
    private long whenCreated;
    private long whenUpdated;
    private String name;
    private String description;
    private String url;
    private String image;
    private Object clickCount;
    private String position;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getClickCount() {
        return clickCount;
    }

    public void setClickCount(Object clickCount) {
        this.clickCount = clickCount;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
