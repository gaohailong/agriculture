package com.sxau.agriculture.view.fragment;

import android.widget.ImageButton;

/**
 * Created by Administrator on 2016/4/9.
 */
public class InfoData {
    private int ibHead;
    private String name;
    private String date;
    private String distance;
    private String title;
    private String content;
    private int ibDingwei;

    public int getIbDingwei() {
        return ibDingwei;
    }

    public void setIbDingwei(int ibDingwei) {
        this.ibDingwei = ibDingwei;
    }

    public int getIbHead() {
        return ibHead;
    }

    public void setIbHead(int ibHead) {
        this.ibHead = ibHead;
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
