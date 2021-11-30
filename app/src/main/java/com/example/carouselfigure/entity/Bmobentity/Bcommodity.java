package com.example.carouselfigure.entity.Bmobentity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.carouselfigure.R;
import com.example.carouselfigure.sqlite.DBHelper;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

public class Bcommodity extends BmobObject {

    private int commodity_id;
    private String name;
    private String introduction;
    private int imageId;

    private double prize;
    public static String TYPE = "¥";



    public Bcommodity(int id, String name, String intro, String belong, Bitmap bitmap, String tag, String prize)
    {
        this.commodity_id=id;
        this.name =name;
        this.introduction=intro;
        this.prize=Double.parseDouble(prize);

    }

    public Bcommodity() {

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
        Log.v("ff", String.valueOf(resId));
        return resId;



    }



    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public double getPrize() {
        return prize;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(int commodity_id) {
        this.commodity_id = commodity_id;
    }
}
