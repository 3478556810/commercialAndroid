package com.example.carouselfigure.cardinalActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;

import java.util.ArrayList;

import com.example.carouselfigure.R;
import com.example.carouselfigure.broadcastReceiver.internetBroadcast;
import com.example.carouselfigure.fragment.community;
import com.example.carouselfigure.fragment.home;
import com.example.carouselfigure.fragment.mine;
import com.example.carouselfigure.fragment.purchase;
import com.example.carouselfigure.util.SwitchFragmentUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.bmob.v3.Bmob;

public class MainActivity extends AppCompatActivity {

    public static BottomNavigationView bnv_menu;
    public static Fragment home;
    public static Fragment community;
    public static Fragment shopping;
    public static Fragment mine;
    public static internetBroadcast internetBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
//Bmob初始化
        Bmob.initialize(this, "f4a201ea7d372e1fe6afa22d17359610");
        //数据库部分
        //组件获取

        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);
        //action
        internetBroadcast = new internetBroadcast();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetBroadcast, itFilter);

        //初始化
        initBottomNav();
//        Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId, BmobException e) {
//                if(e==null){
//                  Toast.makeText(MainActivity.this,"添加数据成功，返回objectId为："+objectId,Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(MainActivity.this,"创建数据失败：" + e.getMessage(),Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });
//
   }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internetBroadcast);
    }

    private void initBottomNav() {
        home = new home("主页");
        getSupportFragmentManager().beginTransaction().add(R.id.container, home).commitNow();
        SwitchFragmentUtil.mTempFragment = home;
        community = new community("社区");
        shopping = new purchase("购物车");
        mine = new mine("我的");
        bnv_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home_item:
                        SwitchFragmentUtil.switchFragment(home, MainActivity.this);
                        break;
                    case R.id.nav_community_item:
                        SwitchFragmentUtil.switchFragment(community, MainActivity.this);
                        break;
                    case R.id.nav_shopping_item:
                        SwitchFragmentUtil.switchFragment(shopping, MainActivity.this);
                        break;
                    case R.id.nav_my_item:
                        SwitchFragmentUtil.switchFragment(mine, MainActivity.this);
                        break;
                }
                return true;
            }
        });


    }



    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            if (listener != null) {
                listener.onTouch(ev);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }


}






