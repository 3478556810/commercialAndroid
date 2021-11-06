package com.example.carouselfigure.entity;

import android.graphics.Bitmap;
import android.media.Image;

public class Comments {
    private int poster_id;
    private int comments_id;
    private String post_time;
    private int qppreciate_count;
    private String belong;


    private Bitmap img;
    private String past_time;
    private String title;
    private String introduction;
    private Commodity Source;


    public Comments(int comments_id, String post_time, String introduction, Bitmap img) {
        this.poster_id = poster_id;
        this.post_time = post_time;
        this.comments_id = comments_id;
        this.img = img;
        this.title = title;
        this.introduction = introduction;
        this.Source = Source;

    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public Bitmap getImg() {
        return img;
    }

    public Commodity getSource() {
        return Source;
    }

    public String getTitle() {
        return title;
    }


    public int getPoster_id() {
        return poster_id;
    }

    public int getComments_id() {
        return this.comments_id;
    }


    public void setComments_id(int comments_id) {
        this.comments_id = comments_id;
    }

    public String getIntroduction() {
        return this.introduction;
    }

    public String getPass_time() {
        return this.past_time;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setPast_time(String past_time) {
        this.past_time = past_time;
    }


}
