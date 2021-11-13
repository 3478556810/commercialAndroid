package com.example.carouselfigure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.broadcastReceiver.internetBroadcast;
import com.example.carouselfigure.entity.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.util.SwitchFragmentUtil;

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
        dBHelper = new DBHelper(this, "Data.db", null, 1);
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
                handler0.post(new Runnable() {

                    @Override
                    public void run() {
// TODO Auto-generated method stub
                        db.delete("commodity",null,null);
                        idList.clear();
                        nameList.clear();
                        prizeList.clear();

                        lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                android.R.layout.simple_list_item_1, idList));

                        lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                android.R.layout.simple_list_item_1, nameList));

                        lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                android.R.layout.simple_list_item_1, prizeList));
                    }
                });

            }


        };
        flushBtn.setOnClickListener(listener0);
        //list
        Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {//游标指向第一行遍历对象
            do {
                //向适配器中添加数据
                idList.add(cursor.getString(cursor.getColumnIndex("id")));
                nameList.add(cursor.getString(cursor.getColumnIndex("name")));
                prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));

            } while (cursor.moveToNext());
        }


        lv1.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, idList));

        lv2.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList));

        lv3.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, prizeList));

        btn = findViewById(R.id.send);
        OnClickListener listener = new OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                new Thread() {
                    public void run() {
                        try {

                            socket = new Socket("10.128.165.153", 12000);
                            socket.setSoTimeout(5000);
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(
                                            socket.getInputStream()));
                            line = br.readLine();
                            Log.d("Read:", line);
                            String[] sArray = line.split(" ");
                            Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
                            cursor.moveToLast();
                            ContentValues values = new ContentValues();

 Log.d("ReadID:",line);
                            if(cursor.getCount()!=0) {
                                int commodityId = cursor.getInt(cursor.getColumnIndex("id"));
                                values.put("id", commodityId + 1);
                            }
                            else  values.put("id", 0);
                            values.put("name", sArray[0]);
                            values.put("intro", sArray[1]);
                            values.put("belong", sArray[2]);
                            values.put("src", sArray[3]);
                            values.put("tag", sArray[4]);
                            values.put("prize", sArray[5]);
                            db.insert("commodity", null, values);
                            cursor.close();
                            br.close();
                            socket.close();

                        } catch (UnknownHostException e) {
// TODO Auto-generated catch block
                            Log.e("UnknownHost", "没找到主机");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.e("IOException", "输入输出出现错误");
// TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
// TODO Auto-generated method stub
                                idList.clear();
                                nameList.clear();
                                prizeList.clear();
                                Cursor cursor = db.query(false, "commodity", null, null, null, null, null, null, null);
                                if (cursor.moveToFirst()) {//游标指向第一行遍历对象
                                    do {
                                        //向适配器中添加数据
                                        idList.add(cursor.getString(cursor.getColumnIndex("id")));
                                        nameList.add(cursor.getString(cursor.getColumnIndex("name")));
                                        prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));

                                    } while (cursor.moveToNext());
                                }
                                cursor.close();


                                lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1, idList));

                                lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1, nameList));

                                lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1, prizeList));
                            }
                        });
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


}
