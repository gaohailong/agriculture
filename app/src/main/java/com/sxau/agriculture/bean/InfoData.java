package com.sxau.agriculture.bean;

/**
 * Created by Administrator on 2016/4/9.
 */
public class InfoData {
    private  int lvCollection;
    private int ivHead;
    private String name;
    private String date;
    private String distance;
    private String title;
    private String content;
    private int ivLocation;

    public InfoData(int lvCollection, int ivHead, String name, String date, String distance, String title, String content, int ivLocation) {
        this.lvCollection = lvCollection;
        this.ivHead = ivHead;
        this.name = name;
        this.date = date;
        this.distance = distance;
        this.title = title;
        this.content = content;
        this.ivLocation = ivLocation;
    }

    public int getLvCollection() {
        return lvCollection;
    }

    public void setLvCollection(int lvCollection) {
        this.lvCollection = lvCollection;
    }

    public int getIvLocation() {
        return ivLocation;
    }

    public void setIvLocation(int ivLocation) {
        this.ivLocation = ivLocation;
    }

    public int getIvHead() {
        return ivHead;
    }

    public void setIvHead(int ivHead) {
        this.ivHead = ivHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
