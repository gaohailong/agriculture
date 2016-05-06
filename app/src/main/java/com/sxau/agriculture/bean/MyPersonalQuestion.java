package com.sxau.agriculture.bean;

import android.widget.ImageView;

import java.util.Date;

/**
 * 个人中心问题
 * 李秉龙
 */
public class MyPersonalQuestion {
    private Boolean State;
    private String Title;
    private String Context;
    private String NoAnswer;
    private String Date;
    private String Head;
    private ImageView Answer;


    public Boolean getState() {
        return State;
    }

    public void setState(Boolean state) {
        State = state;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }

    public String getNoAnswer() {
        return NoAnswer;
    }

    public void setNoAnswer(String noAnswer) {
        NoAnswer = noAnswer;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHead() {
        return Head;
    }

    public void setHead(String head) {
        Head = head;
    }

    public ImageView getAnswer() {
        return Answer;
    }

    public void setAnswer(ImageView answer) {
        Answer = answer;
    }
}
