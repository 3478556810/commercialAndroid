package com.example.carouselfigure.Bean;

import com.example.carouselfigure.R;

public class Community {
    private int community_id;
    private int imageId;
    private String name;
    private String comments_count;
private Boolean isClicked=false;

    public Community(int community_id,int imageId,String name, String comments_count) {

        this.community_id = community_id;
        this.imageId= imageId;
        this.name = name;
        this.comments_count = comments_count;

    }
    public int strToInt(String name)
    {
        //默认的id
        int resId = 2131165336;
        try {
            //根据字符串字段名，取字段//根据资源的ID的变量名获得Field的对象,使用反射机制来实现的
            java.lang.reflect.Field field = R.drawable.class.getField(name);
            //取值
            resId = (Integer) field.get(R.drawable.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resId;



    }

    public int getCommunity_id() {
        return community_id;
    }

    public String getName() {
        return name;
    }

    public String getComments_count() {
        return comments_count;
    }


    public Boolean getClicked() {
        return isClicked;
    }

    public void setClicked(Boolean clicked) {
        isClicked = clicked;
    }


    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
