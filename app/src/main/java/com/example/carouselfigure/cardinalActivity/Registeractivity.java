package com.example.carouselfigure.cardinalActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.R;
import com.example.carouselfigure.entity.Bmobentity.User;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.widget.CustomaryVideoView;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.SaveListener;

public class Registeractivity extends AppCompatActivity {

    private TextView account;
    private TextView password;
    private Button register_btn;
    private Button finishBtn;
    private TextView qLogin;
    private Spinner spinner1;
    private TimerTask task;
    private CustomaryVideoView videoview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeractivity);
        //Bmob初始化
        Bmob.initialize(this, "f4a201ea7d372e1fe6afa22d17359610");
        //数据库部分
        DBHelper dBHelper = new DBHelper(this, "Data.db", null, 2);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getWritableDatabase();

        /*获取组件*/
        account = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        register_btn = findViewById(R.id.register_button);
        spinner1 = findViewById(R.id.spinner1);
        qLogin = findViewById(R.id.qLogin);

        //学院下滑选择栏
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        videoview = (CustomaryVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.v2));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        });


        //点击事件


        qLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent_r0 = new Intent(Registeractivity.this, LoginActivity.class);
                startActivity(intent_r0);
            }
        });

        //registerBtn
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = account.getText().toString();
                if (s.equals("1")) {
                    startActivity(new Intent(Registeractivity.this, MainActivity.class));

                }
                if (account.getText().length() == 0 || password.getText().length() == 0) {
                    {
                        Toast t = Toast.makeText(Registeractivity.this, "账号或密码不合法！", Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP, 0, 0);
                        t.show();
                    }
                    return;
                }
                if (password.getText().length() < 6 || password.getText().length() > 16) {
                    password.setHint("请输入合法的密码！");
                    password.setText("");
                    Toast t = Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER, 0, 0);
                    t.show();

                    return;
                }

                User p2 = new User();
                //判断新增账号是否是唯一的，账号唯一则进行插入，否则自动跳转登录页面
                if (isAccountUnique_acc(account.getText().toString())) {
                    String account_info = account.getText().toString();
                    String password_info = password.getText().toString();
                    p2.setAccount(account_info);
                    p2.setPassword(password_info);
                    p2.save(new SaveListener<String>() {
                        @Override
                        public void done(String objectId, BmobException e) {
                        }
                    });
                    toast( "注册成功,等待三秒后跳转！");
                } else {
                    toast("该账号已存在，等待三秒后跳转登录页面。。。！");
                }
                    //定时器
                    Timer timer = new Timer();
                    task = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(new Intent(Registeractivity.this, LoginActivity.class));
                        }
                    };
                    timer.schedule(task, 3000);



//                if (IsUnique_acc(account.getText().toString())) {
//                    ContentValues values = new ContentValues();
//                    String account_info = account.getText().toString();
//                    String password_info = password.getText().toString();
//                    values.put("account", account_info);
//                    values.put("password", password_info);
//                    db.insert("users", null, values);
//                    Toast t=Toast.makeText(Registeractivity.this,"注册成功,等待三秒后跳转！",Toast.LENGTH_SHORT);
//                    t.setGravity(Gravity.TOP,0,0);
//                    t.show();
//
//
//                } else {
//
//                    Toast t=Toast.makeText(Registeractivity.this,"该账号已存在，等待三秒后跳转登录页面。。。！",Toast.LENGTH_SHORT);
//                    t.setGravity(Gravity.TOP,0,0);
//                    t.show();
//                    final Intent intent_rl = new Intent(Registeractivity.this, LoginActivity.class);
//                    Timer timer = new Timer();
//                    task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            startActivity(intent_rl);
//                        }
//                    };
//                    timer.schedule(task, 3000);
//               }


//                final Intent intent_rl = new Intent(MainActivity.this, LoginActivity.class);
//                Timer timer = new Timer();
//                task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        startActivity(intent_rl);
//                    }
//                };
//                timer.schedule(task, 3000);
            }
        });

    }

//用来判断新增行数据与Bmob后端User表行数据是否账号重复
    private int flag = 1;
    private boolean isAccountUnique_acc(String str) {
        BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    //Bmob后端表为空直接退出done()函数
                    if (object.size() == 0)
                        return;
                   // toast(String.valueOf(object));
                    for (int i = 0; i < object.size(); i++) {
                        User p = object.get(i);
                        String s = p.getAccount();
                        //找到于新增行数据账号相同的行数据，flag置0，直接退出done()函数
                        if (s.equals(str)) {
                            flag = 0;
                            return;
                        }
                    }
                }
            }

        });
        return flag != 0;
    }

    private void toast(String s) {
        Toast.makeText(Registeractivity.this, s, Toast.LENGTH_LONG).show();

    }
//    private boolean IsUnique_acc(String str) {
//
//        DBHelper dBHelper = new DBHelper(this, "Data.db", null, 2);
//        dBHelper.getWritableDatabase();
//        SQLiteDatabase db = dBHelper.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(
//                "select * from   users  where   account=? ",
//                new String[]{str});
//        while (cursor.getCount() == 0) {
//            db.close();
//            return true;
//        }
//        db.close();
//        return false;
//
//    }

    @Override
    protected void onStop() {
        super.onStop();
        videoview.stopPlayback();
    }
}