package com.example.carouselfigure.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

//1.继承抽象类SQLiteOpenHelper
//2.实现其抽象方法

public class DBHelper extends SQLiteOpenHelper {
    //建商品表
    public static final String Commodity_table="create table commodity("+"id integer primary key,"
            +"name text,"
            +"intro blob,"
            +"belong text,"
            +"src text,"
            +"tag text,"
            +"prize text)";

     //建评论表
    public static final String Comments_table="create table comments("+"commentsId integer primary key,"
            +"posterId integer,"
            +"intro blob,"
            +"postTime blob,"
            +"belong text,"
            +"img blob,"
            +"appreciatedCount integer,"
             + "foreign key (posterId) references users(userId))";
    //建用户表
    public static final String User_table="create table users("+"userId integer primary key,"
            +"account text,"
            +"password text,"
            +"signature blob,"
            +"gender text,"
            +"iconId integer,"
            +"money integer)";

    //建订单表
    public static final String Order_table="create table orderT("+"orderId integer primary key,"
            +"userId integer,"
            +"intro blob,"
            +"store text,"
            +"imgId text,"
            +"prize text)";

    //建历史记录表
    public static final String History_table="create table history("+"historyId integer primary key,"
            +"userId integer,"
            +"intro blob,"
            +"src text,"
            +"prize text," +
            "foreign key (userId) references users(userId))";





    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //第一次创建数据库时被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Commodity_table);
        //数据库插入初始数据
        ContentValues values =new ContentValues();
        values.put("id",0);
        values.put("name","善擦玩偶");
        values.put("belong","自营");
        values.put("intro","仁王2 以善擦为原型的原比例大小玩偶登场 2021新款 快乐吸猫时间！");
        values.put("src","commodity_shancadoll");
        values.put("tag","玩偶");
        values.put("prize","249");
        db.insert("commodity",null,values);
        values.put("id",1);
        values.put("name","谷子帮榴莲饼");
        values.put("belong","津冬超市");
        values.put("intro","谷子帮 猫山王榴莲饼酥一整箱糕点点心馅饼充饥夜宵爆浆休闲零食品20枚");
        values.put("src","commodity_liuliancake");
        values.put("tag","榴莲饼");
        values.put("prize","16.8");
        db.insert("commodity",null,values);
        values.put("id",2);
        values.put("name","啄木鸟短袖");
        values.put("belong","津冬旗舰");
        values.put("intro","啄木鸟（TUCANO）短袖t恤男 2021夏季圆领印花纯棉T恤百搭打底衫休闲衣服男装 白色 XL");
        values.put("src","commodity_caticon_tshirt");
        values.put("tag","T-Shirt衣服");
        values.put("prize","68");
        db.insert("commodity",null,values);
        values.put("id",3);
        values.put("name","RTX3090显卡");
        values.put("belong","津冬旗舰");
        values.put("intro","微星（MSI）超龙 GeForce RTX 3090 SUPRIM X 24G 超旗舰 电竞游戏设计智能学习电脑独立显卡");
        values.put("src","commodity_3090xianka");
        values.put("tag","显卡");
        values.put("prize","22999");
        db.insert("commodity",null,values);
        values.put("id",4);
        values.put("name","商茵连衣裙");
        values.put("belong","自营");
        values.put("intro","商茵 连衣裙女大码女装2021年秋季新款法式轻熟风连衣裙女胖妹妹收腰显瘦裙子气质女神范 卡其色 M");
        values.put("src","commodity_reddress");
        values.put("tag","连衣裙衣服");
        values.put("prize","129");
        db.insert("commodity",null,values);

        db.execSQL(Comments_table);
        db.execSQL(User_table);
        ContentValues values1 =new ContentValues();
        values1.put("userId","0");
        values1.put("money","2200");
        db.insert("users",null,values1);
        db.execSQL(Order_table);
        db.execSQL(History_table);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // 启动外键
            db.execSQL("PRAGMA foreign_keys = 1;");

        }
    }


}
