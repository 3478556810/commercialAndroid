package com.example.carouselfigure.cardinalActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carouselfigure.R;
import com.example.carouselfigure.entity.Bmobentity.User;
import com.example.carouselfigure.widget.CustomaryVideoView;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LoginActivity extends AppCompatActivity {
private CustomaryVideoView videoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView account=findViewById(R.id.loginName);
        TextView password=findViewById(R.id.loginPassword);
        videoview = (CustomaryVideoView) findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.v2));
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
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (isAccountExist(account.getText().toString(),password.getText().toString()))
{
    toast("登录成功！！！");
    startActivity(new Intent(LoginActivity.this, MainActivity.class));

}
else toast("账号或密码有误。。。");

            }
        });

    }
private int flag=0;
    private boolean isAccountExist(String str,String str1) {
        BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
        categoryBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> object, BmobException e) {
                if (e == null) {
                    //Bmob后端表为空直接退出done()函数,显然无法登录，直接退出done()函数
                    if (object.size() == 0)
                    {
                        return;
                    }
                    // toast(String.valueOf(object));
                    for (int i = 0; i < object.size(); i++) {

                        User p = object.get(i);
                        String s = p.getAccount();
                        String s1= p.getPassword();
//                        toast(String.valueOf(s));
                        //找到与新增行数据账号相同的行数据，flag置1，直接退出done()函数
                        if (s.equals(str)&&s1.equals(str1)) {
                            flag = 1;
                            return;
                        }
                    }
                }
            }

        });
        return flag != 0;
    }

    private void toast(String s) {
        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_LONG).show();

    }
}