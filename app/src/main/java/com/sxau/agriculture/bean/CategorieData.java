package com.sxau.agriculture.bean;

import java.io.Serializable;

/**
 * 分类数据的实体类
 * @author 崔志泽
 */
public class CategorieData implements Serializable{

    /**
     * id : 20
     * whenCreated : 1463620393000
     * whenUpdated : 1463620393000
     * pid : 15
     * name : 稀有果蔬
     * categoryType : ARTICLE
     * image : images/test.png
     * sort : 255
     */

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
