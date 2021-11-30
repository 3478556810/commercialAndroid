package com.example.carouselfigure.cardinalActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.R;
import com.example.carouselfigure.broadcastReceiver.internetBroadcast;
import com.example.carouselfigure.entity.Bmobentity.Bcommodity;
import com.example.carouselfigure.entity.Bmobentity.User;
import com.example.carouselfigure.sqlite.DBHelper;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ClientActivity extends Activity {
    private TextView display;
    private Handler handler;
    private String host;
    private Button btn;
    private Button flushBtn;
    private Socket socket;
    private String line;
    private EditText et;
    private DBHelper dBHelper;
    ArrayList<String> idList = new ArrayList<>();
    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> prizeList = new ArrayList<>();
    ListView lv1;
    ListView lv2;
    ListView lv3;
    SQLiteDatabase db;
    public static com.example.carouselfigure.broadcastReceiver.internetBroadcast internetBroadcast;
    public static final int FLUSH_TABLE = 1;
    private Handler handler0 = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        //Bmob初始化
        Bmob.initialize(this, "f4a201ea7d372e1fe6afa22d17359610");
        dBHelper = new DBHelper(this, "Data.db", null, 2);
        dBHelper.getWritableDatabase();
        db = dBHelper.getReadableDatabase();
        handler = new Handler();
        internetBroadcast = new internetBroadcast();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetBroadcast, itFilter);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);
        flushBtn = findViewById(R.id.flushTable);
        OnClickListener listener0 = new OnClickListener() {
            @Override
            public void onClick(View v) {

// TODO Auto-generated method stub
                        //  db.delete("commodity", null, null);

//                        idList.clear();
//                        nameList.clear();
//                        prizeList.clear();
                        final EditText input = new EditText(ClientActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                        builder.setTitle("请输入删除商品的id").setIcon(R.drawable.transformcode).setView(input)
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int bcID = Integer.parseInt(String.valueOf(input.getText()));
                                        BmobQuery<Bcommodity> categoryBmobQuery = new BmobQuery<>();
                                        categoryBmobQuery.findObjects(new FindListener<Bcommodity>() {
                                            @Override
                                            public void done(List<Bcommodity> object, BmobException e) {
                                                if (e == null) {
                                                    // toast(String.valueOf(object));
                                                    for (int i = 0; i < object.size(); i++) {
                                                        Bcommodity p = object.get(i);
                                                        if (p.getCommodity_id() == bcID) {
                                                            p.delete(new UpdateListener() {
                                                                @Override
                                                                public void done(BmobException e) {
                                                                }

                                                            });


                                                        }
                                                    }
                                                    // toast(String.valueOf(idList));
                                                }
                                                toast("删除成功");
                                            }
                                            });
                                }
                                        });

                        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        builder.show();
                                        lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                                android.R.layout.simple_list_item_1, idList));

                                        lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                                android.R.layout.simple_list_item_1, nameList));

                                        lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                                android.R.layout.simple_list_item_1, prizeList));
                                    }







        };
                flushBtn.setOnClickListener(listener0);
                //list
//        Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
//        if (cursor.moveToFirst()) {//游标指向第一行遍历对象
//            do {
//                //向适配器中添加数据
//                idList.add(cursor.getString(cursor.getColumnIndex("id")));
//                nameList.add(cursor.getString(cursor.getColumnIndex("name")));
//                prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));
//
//            } while (cursor.moveToNext());
//        }
        //跳转页面按钮
                OnClickListener listenerz = new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(ClientActivity.this, MainActivity.class));
                    }
                };
                findViewById(R.id.commodityPaper).setOnClickListener(listenerz);
        //更新按钮
        OnClickListener listenerc = new OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText input = new EditText(ClientActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(ClientActivity.this);
                builder.setTitle("请输入需要更新商品的id和新价格").setIcon(R.drawable.transformcode).setView(input)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String[] sArray = input.getText().toString().split(" ");
                                 toast(String.valueOf(sArray));
                                int bcID = Integer.parseInt(sArray[0]);
                                Double newP=Double.parseDouble(sArray[1]);
                                BmobQuery<Bcommodity> categoryBmobQuery = new BmobQuery<>();
                                categoryBmobQuery.findObjects(new FindListener<Bcommodity>() {
                                    @Override
                                    public void done(List<Bcommodity> object, BmobException e) {
                                        if (e == null) {
                                            // toast(String.valueOf(object));
                                            for (int i = 0; i < object.size(); i++) {
                                                Bcommodity p = object.get(i);
                                                if (p.getCommodity_id() == bcID) {
                                                    p.setPrize(newP);
                                                    ContentValues values = new ContentValues();
                                                    values.put("prize", newP);
                                                    db.update("commodity", values, "id = ?", new String[] { String.valueOf(bcID)});
                                                    p.update(p.getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                        }

                                                    });

                                                }
                                            }
                                            // toast(String.valueOf(idList));
                                        }
                                        toast("更新成功");
                                    }
                                });
                            }
                        });

                builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
                lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                        android.R.layout.simple_list_item_1, idList));

                lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                        android.R.layout.simple_list_item_1, nameList));

                lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                        android.R.layout.simple_list_item_1, prizeList));

            }
        };
        findViewById(R.id.upgradeTable).setOnClickListener(listenerc);
                //初始化列表数据
                BmobQuery<Bcommodity> categoryBmobQuery = new BmobQuery<>();
                categoryBmobQuery.findObjects(new FindListener<Bcommodity>() {
                    @Override
                    public void done(List<Bcommodity> object, BmobException e) {
                        if (e == null) {

                            // toast(String.valueOf(object));
                            for (int i = 0; i < object.size(); i++) {
                                Bcommodity p = object.get(i);

                                idList.add(String.valueOf(p.getCommodity_id()));
                                nameList.add(p.getName());
                                prizeList.add(String.valueOf(p.getPrize()));
                                // toast(String.valueOf(idList));
                            }
                            //   toast(String.valueOf(idList+" "+nameList));
                            lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                    android.R.layout.simple_list_item_1, idList));

                            lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                    android.R.layout.simple_list_item_1, nameList));

                            lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                    android.R.layout.simple_list_item_1, prizeList));


                        }
                    }

                });

                btn = findViewById(R.id.send);
                OnClickListener listener = new OnClickListener() {
                    //增加商品
                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
                        new Thread() {
                            public void run() {
                                Bcommodity bcommodity = new Bcommodity();
                                bcommodity.setCommodity_id(0);
                                bcommodity.setName("善擦玩偶");
                                bcommodity.setIntroduction("仁王2 以善擦为原型的原比例大小玩偶登场 2021新款 快乐吸猫时间！");
                                bcommodity.setPrize(249);
                                bcommodity.setImageId(R.drawable.commodity_shancadoll);
                                bcommodity.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                    }
                                });
                                Bcommodity bcommodity1 = new Bcommodity();
                                bcommodity1.setCommodity_id(4);
                                bcommodity1.setName("商茵连衣裙");
                                bcommodity1.setIntroduction("商茵 连衣裙女大码女装2021年秋季新款法式轻熟风连衣裙女胖妹妹收腰显瘦裙子气质女神范 卡其色 M");
                                bcommodity1.setPrize(129);
                                bcommodity1.setImageId(R.drawable.commodity_reddress);
                                bcommodity1.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String objectId, BmobException e) {
                                    }
                                });
//                        try {
//
//                            socket = new Socket("112.124.15.178", 12000);
//                            socket.setSoTimeout(5000);
//                            BufferedReader br = new BufferedReader(
//                                    new InputStreamReader(
//                                            socket.getInputStream()));
//                            line = br.readLine();
//                            String[] sArray = line.split(" ");
//
////                            Log.d("Read:", line);
////                            String[] sArray = line.split(" ");
////                            Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
////                            cursor.moveToLast();
////                            ContentValues values = new ContentValues();
////
////                           // Log.d("ReadID:", line);
////                            if (cursor.getCount() != 0) {
////                                int commodityId = cursor.getInt(cursor.getColumnIndex("id"));
////                                values.put("id", commodityId + 1);
////                            } else values.put("id", 0);
////                            values.put("name", sArray[0]);
////                            values.put("intro", sArray[1]);
////                            values.put("belong", sArray[2]);
////                            values.put("src", sArray[3]);
////                            values.put("tag", sArray[4]);
////                            values.put("prize", sArray[5]);
////                            db.insert("commodity", null, values);
////                            cursor.close();
//                            br.close();
//                            socket.close();
//
//                        } catch (UnknownHostException e) {
//// TODO Auto-generated catch block
//                            Log.e("UnknownHost", "没找到主机");
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            Log.e("IOException", "输入输出出现错误");
//// TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        handler.post(new Runnable() {
//
//                            @Override
////                            public void run() {
////// TODO Auto-generated method stub
////                                idList.clear();
////                                nameList.clear();
////                                prizeList.clear();
//////                                Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
//////                                if (cursor.moveToFirst()) {//游标指向第一行遍历对象
//////                                    do {
//////                                        //向适配器中添加数据
//////                                        idList.add(cursor.getString(cursor.getColumnIndex("id")));
//////                                        nameList.add(cursor.getString(cursor.getColumnIndex("name")));
//////                                        prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));
//////
//////                                    } while (cursor.moveToNext());
//////                                }
//////                                cursor.close();
//////
//////
//////                                lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
//////                                        android.R.layout.simple_list_item_1, idList));
//////
//////                                lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
//////                                        android.R.layout.simple_list_item_1, nameList));
//////
//////                                lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
//////                                        android.R.layout.simple_list_item_1, prizeList));
////
////                                BmobQuery<Bcommodity> categoryBmobQuery = new BmobQuery<>();
////                                categoryBmobQuery.findObjects(new FindListener<Bcommodity>() {
////                                    @Override
////                                    public void done(List<Bcommodity> object, BmobException e) {
////                                        if (e == null) {
////
////                                            toast(String.valueOf(object));
////                                            for (int i = 0; i < object.size(); i++) {
////                                                Bcommodity p = object.get(i);
////                                                idList.add(String.valueOf(p.getCommodity_id()));
////                                                nameList.add(p.getName());
////                                                prizeList.add(String.valueOf(p.getPrize()));
////                                            }
////                                        }
////                                    }
////
////                                });
////
////                                lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
////                                        android.R.layout.simple_list_item_1, idList));
////
////                                lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
////                                        android.R.layout.simple_list_item_1, nameList));
////
////                                lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
////                                        android.R.layout.simple_list_item_1, prizeList));
////                            }
//                        });
                            }
                        }.start();
                    }
                };
                btn.setOnClickListener(listener);


            }


            @Override
            protected void onDestroy() {
                super.onDestroy();
                unregisterReceiver(internetBroadcast);
            }

            private void toast(String s) {
                Toast.makeText(this, s, Toast.LENGTH_LONG).show();

            }

        }
