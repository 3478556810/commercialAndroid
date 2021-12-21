package com.example.carouselfigure.Bean;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.carouselfigure.R;
import com.example.carouselfigure.sqlite.DBHelper;

import java.io.Serializable;
import java.util.ArrayList;

public class Commodity implements Serializable {
    private int commodity_id;
    private String name;
    private String introduction;
    private int imageId;
    private Bitmap bitmap;
    private double prize;
    public static String TYPE = "¥";
    private String belong;
    private String tag;
    private DBHelper dBHelper;
    ArrayList<String> id_arr=new ArrayList<>();
    ArrayList<String> name_arr=new ArrayList<>();
    ArrayList<String> intro_arr=new ArrayList<>();
    ArrayList<String> img_arr=new ArrayList<>();
    ArrayList<String> prize_arr=new ArrayList<>();
    ArrayList<String> belong_arr=new ArrayList<>();
    public Commodity(int commodity_id, SQLiteDatabase db) {
        this.commodity_id=commodity_id;

        Cursor cursor = db.query(false, "commodity", null, null, null ,null, null, null, null);
        if (cursor.moveToFirst()){//游标指向第一行遍历对象
            do {
                //向适配器中添加数据
//                Log.v("1", String.valueOf(commodity_id));
             id_arr.add(cursor.getString(cursor.getColumnIndex("id")));
                name_arr.add(cursor.getString(cursor.getColumnIndex("name")));
                intro_arr.add(cursor.getString(cursor.getColumnIndex("intro")));
                img_arr.add(cursor.getString(cursor.getColumnIndex("src")));
                prize_arr.add(cursor.getString(cursor.getColumnIndex("prize")));
                belong_arr.add(cursor.getString(cursor.getColumnIndex("belong")));
            }while (cursor.moveToNext());
        }
        cursor.close();
this.commodity_id=commodity_id;
        this.name = name_arr.get(commodity_id);
        this.introduction = intro_arr.get(commodity_id);
        this.imageId = strToInt(img_arr.get(commodity_id));
        this.prize = Double.parseDouble(prize_arr.get(commodity_id));
        this.belong = belong_arr.get(commodity_id);
    }

public Commodity (int id, String name, String intro, String belong, Bitmap bitmap, String tag, String prize)
{
    this.commodity_id=id;
    this.name =name;
    this.introduction=intro;
    this.belong=belong;
    this.bitmap=bitmap;
    this.tag=tag;
    this.prize=Double.parseDouble(prize);

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

    public String getBelong() {
        return belong;
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
