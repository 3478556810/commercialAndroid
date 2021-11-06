package com.example.carouselfigure;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.FragmentManager;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.carouselfigure.adapter.RecyclerAdapterForComments;
import com.example.carouselfigure.broadcastReceiver.internetBroadcast;
import com.example.carouselfigure.entity.Comments;
import com.example.carouselfigure.fragment.community;
import com.example.carouselfigure.fragment.home;
import com.example.carouselfigure.fragment.mine;
import com.example.carouselfigure.fragment.shopping;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.util.BSUtil;
import com.example.carouselfigure.util.SwitchFragmentUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

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

        //组件获取

        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);
        //action
        internetBroadcast = new internetBroadcast();
        IntentFilter itFilter = new IntentFilter();
        itFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(internetBroadcast, itFilter);

        //初始化
        initBottomNav();

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
        shopping = new shopping("购物车");
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






