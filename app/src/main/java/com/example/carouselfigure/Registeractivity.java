package com.example.carouselfigure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.sqlite.DBHelper;

import java.util.Timer;
import java.util.TimerTask;

public class Registeractivity extends AppCompatActivity {

    private TextView account;
    private TextView password;
    private Button register_btn;
    private Button finishBtn;
    private TextView qLogin;
    private Spinner spinner1;
    private TimerTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registeractivity);

        //数据库部分
        DBHelper dBHelper = new DBHelper(this, "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getWritableDatabase();

        /*获取组件*/
        account = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPassword);
        register_btn = findViewById(R.id.register_button);
        spinner1 = findViewById(R.id.spinner1);
        finishBtn=findViewById(R.id.finishButton);
        qLogin=findViewById(R.id.qLogin);

        //学院下滑选择栏
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.college, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource
                (android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);

        //点击事件
        //finishBtn
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//qLogin
//        qLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Intent intent_r0 = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent_r0);
//            }
//        });

        //registerBtn
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (account.getText().length() == 0 || password.getText().length() == 0) {
                    {       Toast t=Toast.makeText(Registeractivity.this,"账号或密码不合法！",Toast.LENGTH_SHORT);
                        t.setGravity(Gravity.TOP,0,0);
                        t.show();}
                    return;
                }
                if (password.getText().length() < 6||password.getText().length() >16) {
                    password.setHint("请输入合法的密码！");
                    password.setText("");
                    Toast t = Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_LONG);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();

                    return;
                }
                if (IsUnique_acc(account.getText().toString())) {
                    ContentValues values = new ContentValues();
                    String account_info = account.getText().toString();
                    String password_info = password.getText().toString();
                    values.put("account", account_info);
                    values.put("password", password_info);
                    db.insert("users", null, values);
                    Toast t=Toast.makeText(Registeractivity.this,"注册成功,等待三秒后跳转！",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP,0,0);
                    t.show();


                } else {

                    Toast t=Toast.makeText(Registeractivity.this,"该账号已存在，等待三秒后跳转登录页面。。。！",Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.TOP,0,0);
                    t.show();
//                    final Intent intent_rl = new Intent(Registeractivity.this, LoginActivity.class);
//                    Timer timer = new Timer();
//                    task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            startActivity(intent_rl);
//                        }
//                    };
//                    timer.schedule(task, 3000);
               }


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

    private boolean IsUnique_acc(String str) {

        DBHelper dBHelper = new DBHelper(this, "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "select * from   users  where   account=? ",
                new String[]{str});
        while (cursor.getCount() == 0) {
            db.close();
            return true;
        }
        db.close();
        return false;

    }
}