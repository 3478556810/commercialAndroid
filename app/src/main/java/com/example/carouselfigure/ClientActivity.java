package com.example.carouselfigure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.carouselfigure.broadcastReceiver.internetBroadcast;
import com.example.carouselfigure.entity.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;

public class ClientActivity extends Activity {
    private TextView display;
    private Handler handler;
    private String host;
    private Button btn;
    private Socket socket;
    private String line;
    private EditText et;
    private DBHelper dBHelper;
    ArrayList <String>  idList=new ArrayList<>();
    ArrayList <String> nameList=new ArrayList<>();
    ArrayList <String> prizeList=new ArrayList<>();
    public static com.example.carouselfigure.broadcastReceiver.internetBroadcast internetBroadcast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        handler = new Handler();
        internetBroadcast = new internetBroadcast();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetBroadcast, itFilter);
        ListView lv1 = (ListView) findViewById(R.id.lv1);//得到ListView对象的引用
        ListView lv2 = (ListView) findViewById(R.id.lv2);//得到ListView对象的引用
        ListView lv3 = (ListView) findViewById(R.id.lv3);//得到ListView对象的引用
        //list
        String s ="";
        dBHelper = new DBHelper(this, "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();

        Cursor cursor = db.query(false, "commodity", null, null, null ,null, null, null, null);
        if (cursor.moveToFirst()){//游标指向第一行遍历对象
            do {
                //向适配器中添加数据
                idList.add(cursor.getString(cursor.getColumnIndex("id")));
                nameList.add(cursor.getString(cursor.getColumnIndex("name")));
                prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));

            }while (cursor.moveToNext());
        }
        cursor.close();




        lv1.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,idList ));

        lv2.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nameList));

        lv3.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, prizeList));

        btn = (Button) findViewById(R.id.send);
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
                            String[] sArray=line.split(" ");
                            ContentValues values =new ContentValues();
                            values.put("id",sArray[0]);
                            values.put("name",sArray[1]);
                            values.put("prize",sArray[2]);
                            db.insert("commodity",null,values);
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
                        handler.post(new Runnable(){

                            @Override
                            public void run() {
// TODO Auto-generated method stub
                                idList.clear();
                                nameList.clear();
                                prizeList.clear();
                                Cursor cursor = db.query(false, "commodity", null, null, null ,null, null, null, null);
                                if (cursor.moveToFirst()){//游标指向第一行遍历对象
                                    do {
                                        //向适配器中添加数据
                                        idList.add(cursor.getString(cursor.getColumnIndex("id")));
                                        nameList.add(cursor.getString(cursor.getColumnIndex("name")));
                                        prizeList.add(cursor.getString(cursor.getColumnIndex("prize")));

                                    }while (cursor.moveToNext());
                                }
                                cursor.close();


                                lv1.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1,idList ));

                                lv2.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1, nameList));

                                lv3.setAdapter(new ArrayAdapter<String>(ClientActivity.this,
                                        android.R.layout.simple_list_item_1, prizeList));
                            }});
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
