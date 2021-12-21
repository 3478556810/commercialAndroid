package com.example.carouselfigure.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.carouselfigure.EntertainmentActivity.EntertainmentActivityForCombat;
import com.example.carouselfigure.cardinalActivity.SearchExportActivity;
import com.example.carouselfigure.adapter.HomeInnerPageAdapter;
import com.example.carouselfigure.fragment.homeInner.classificationPaper;
import com.example.carouselfigure.fragment.homeInner.finePaper;
import com.example.carouselfigure.fragment.homeInner.fleaMarketPaper;
import com.example.carouselfigure.fragment.homeInner.mainPaper;
import com.example.carouselfigure.widget.AnimationNestedScrollView;
import com.example.carouselfigure.Bean.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.cardinalActivity.MainActivity;
import com.example.carouselfigure.R;
import com.example.carouselfigure.adapter.RecyclerAdapterForCommodity;

import com.example.carouselfigure.util.SwitchFragmentUtil;
import com.example.carouselfigure.widget.NoScrollViewPaper;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.carouselfigure.fragment.homeInner.mainPaper.downdis;

//Contents
//0 component define
//...onCreateView...
//1 component id
//2 commonComponent event
//3 gestureListener
//...onCreateView...
//4 onActivityResult
//5  handle
//6  adapter

public class home extends Fragment {
    //constants
    static double updis=0.0;
    Boolean flag = true;
    private static final int RESULT_OK = 1;
    private static final int REQUEST_CODE = 1;
    static int TOPMARGIN = 0;
    static int HEIGH = 0;
    static int BOTTOMMARGIN = 0;
    static Float distance = 0.0f;
    //gesture
    private final static int MIN_MOVE = 50;   //手势移动最小距离
    private GestureDetector mDetector;
    public Context mContext;
    //innerHomeFragmentSwitch
    FragmentManager childFM;
    List<Fragment> fragmentList = new ArrayList<>();
    List<String> tabTitles = new ArrayList<>();
    private TabLayout tabLayout;
    private NoScrollViewPaper viewPager;
    private HomeInnerPageAdapter homeInnerPageAdapter;
    //ViewFlipper
    private ViewFlipper flipper;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Handler handler = new Handler();

    //RecyclerView
    private ClassicsHeader classicHeader;
    private RecyclerView recyclerView;
    private RecyclerAdapterForCommodity rcAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private List<Commodity> mList = new ArrayList();
    private List<Commodity> oList = new ArrayList();
    //SearchBanner
    private RelativeLayout loomedLayout;
    private AnimationNestedScrollView sv_view;
    private LinearLayout ll_search;
    private CircleImageView tv_icon;
    private CardView tv_discourse;
    private float LL_SEARCH_MIN_TOP_MARGIN, LL_SEARCH_MAX_TOP_MARGIN, LL_SEARCH_MAX_WIDTH, LL_SEARCH_MIN_WIDTH, TV_ICON_MAX_TOP_MARGIN;
    private RelativeLayout.LayoutParams loomedLayoutParams;
    private AutoCompleteTextView acView;
    private static final String[] data = new String[]{
            "玩偶", "连衣裙", "显卡", "榴莲饼", "原道耳机", "耳机", "T-恤", "奥特曼", "3090","衣服"
    };
    private ImageButton photoTaker;
    private ImageButton qrTaker;
    private ImageButton calenderBtn;
    private ImageButton messengeBtn;
    private ImageView divider;
    private TextView signature;
    //??
    private DBHelper dBHelper;
    public static mainPaper mainPaper= new mainPaper();
    public static finePaper finePaper= new finePaper();
    public static classificationPaper classificationPaper=new classificationPaper();
    public static fleaMarketPaper fleaMarketPaper=new fleaMarketPaper();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String text;


    public home(String text) {
        // Required empty public constructor
        this.text = text;
    }

    public home() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        acView = view.findViewById(R.id.search_tv_search);
        ll_search = view.findViewById(R.id.searchView);
        tv_icon = view.findViewById(R.id.search_tv_icon);
        tv_discourse = view.findViewById(R.id.search_tv_discourse);
        photoTaker = view.findViewById(R.id.photoTaker);
        qrTaker = view.findViewById(R.id.qrCodeTaker);
        divider = view.findViewById(R.id.divider_homeBtn);
        messengeBtn = view.findViewById(R.id.messenge_homeBtn);
        signature = view.findViewById(R.id.signature);
        tabLayout = view.findViewById(R.id.hNavigation);
        viewPager = view.findViewById(R.id.homeInnerContainer);
        loomedLayout = view.findViewById(R.id.loomedLayout);
        //动态伸缩
        sv_view = view.findViewById(R.id.dynamicLayout);
        //水平导航碎片切换

        fragmentList.add(0, mainPaper);
        fragmentList.add(1, classificationPaper);
        fragmentList.add(2, finePaper);
        fragmentList.add(3, fleaMarketPaper);
        tabTitles.add("首页");
        tabTitles.add("分类");
        tabTitles.add("精品");
        tabTitles.add("淘宝");
        homeInnerPageAdapter = new HomeInnerPageAdapter(getChildFragmentManager(), fragmentList, tabTitles);
        viewPager.setAdapter(homeInnerPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        //初始化
        setAdapter();
      //  handler.post(task);

       // Log.v("dis", String.valueOf(mainPaper));

        //组件点击事件
        //头像快捷跳转个人中心
        tv_icon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SwitchFragmentUtil.switchFragment(MainActivity.mine, getActivity());
                MainActivity.bnv_menu.setSelectedItemId(R.id.nav_my_item);
            }
        });
        //照相机
        photoTaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        //二维码
        qrTaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建IntentIntegrator对象
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                // 开始扫描
                intentIntegrator.initiateScan();
            }
        });

        messengeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EntertainmentActivityForCombat.class));
            }
        });
        //输入框
        acView.setDropDownWidth(600);
        acView.setDropDownHorizontalOffset(80);
        acView.setDropDownVerticalOffset(20);
        acView.setDropDownBackgroundResource(R.drawable.shapeforsearch);
        RelativeLayout toolBar = view.findViewById(R.id.toolbarHome);
        RelativeLayout changedImg = view.findViewById(R.id.changedBg);
        //gestureListener 初态
        RelativeLayout.LayoutParams toolBarParams = (RelativeLayout.LayoutParams) toolBar.getLayoutParams();
        final int mHeightToolBar = toolBarParams.height;
        RelativeLayout.LayoutParams searchLayoutParams = (RelativeLayout.LayoutParams) ll_search.getLayoutParams();
        final int mHeightSearch = searchLayoutParams.height;
        final int mMaginSearch = searchLayoutParams.topMargin;
        final int mWidthSearch = searchLayoutParams.width;
        RelativeLayout.LayoutParams loomedLayoutParams = (RelativeLayout.LayoutParams) loomedLayout.getLayoutParams();
        final int mHeightLoomed = loomedLayoutParams.height;
        RelativeLayout.LayoutParams bgLayoutParams = (RelativeLayout.LayoutParams) changedImg.getLayoutParams();
        final int mHeightBG = bgLayoutParams.height;
        //animator
        //设置初始值和结束值
        final ValueAnimator animatorADD = ValueAnimator.ofFloat(10f, 20f);
        animatorADD.setDuration(200);
        animatorADD.setInterpolator(new BounceInterpolator());
        final ValueAnimator animatorSUB = ValueAnimator.ofFloat(80f, 35f);
        animatorSUB.setDuration(200);
        animatorSUB.setInterpolator(new LinearInterpolator());

        //实例化SimpleOnGestureListener与GestureDetector对象
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override

            public boolean onFling(MotionEvent e1, MotionEvent e2, float vx, float vy) {
                if (e1.getY() - e2.getY() > MIN_MOVE) {
                    //下滑实现动画伸缩
                    animatorADD.addUpdateListener(animation1 -> {
                        Float x = (Float) animatorADD.getAnimatedValue();
                        int value = x.intValue();
                        tabLayout.setVisibility(View.GONE);
                        photoTaker.setVisibility(View.INVISIBLE);
                        divider.setVisibility(View.INVISIBLE);
                        qrTaker.setVisibility(View.INVISIBLE);
                        //toolbar
                        tv_discourse.setVisibility(View.GONE);
                        //search
                        searchLayoutParams.width = (int) (80 - (value - 600));
                        ll_search.setLayoutParams(searchLayoutParams);
                        // loomed
                        loomedLayoutParams.height -= mHeightToolBar;
                        loomedLayout.setLayoutParams(loomedLayoutParams);
                        //bg
                        bgLayoutParams.height = searchLayoutParams.height + 46;
                        changedImg.setLayoutParams(bgLayoutParams);
                    });
                    if (flag) {
                        animatorADD.start();
                        animatorSUB.addUpdateListener(animation1 -> {
                            Float x = (Float) animatorSUB.getAnimatedValue();
                            int value = x.intValue();
                            //search
                            searchLayoutParams.topMargin = value - 15;
                            ll_search.setLayoutParams(searchLayoutParams);
                        });
                        animatorSUB.start();
                    }
                    flag = false;

                } else if (e2.getY() - e1.getY() > MIN_MOVE) {

                    //上滑实现动画复原
                    flag = true;
                    animatorADD.addUpdateListener(animation1 -> {
                        tabLayout.setVisibility(View.VISIBLE);
                        photoTaker.setVisibility(View.VISIBLE);
                        divider.setVisibility(View.VISIBLE);
                        qrTaker.setVisibility(View.VISIBLE);
                        //toolbar
                        tv_discourse.setVisibility(View.VISIBLE);
                        //search
                        searchLayoutParams.height = mHeightSearch;
                        searchLayoutParams.topMargin = mMaginSearch;
                        searchLayoutParams.width = mWidthSearch;
                        ll_search.setLayoutParams(searchLayoutParams);
                        //loomed
                        loomedLayoutParams.height = mHeightLoomed;
                        loomedLayout.setLayoutParams(loomedLayoutParams);
                        //bg
                        bgLayoutParams.height = mHeightBG;
                        changedImg.setLayoutParams(bgLayoutParams);
                    });
                        if (downdis==0)
                            animatorADD.start();
                }

                return true;
            }
        };

        GestureDetector gestureDetector = new GestureDetector(simpleOnGestureListener);

        MainActivity.MyOnTouchListener myOnTouchListener = new MainActivity.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                gestureDetector.onTouchEvent(ev);
                return false;
            }
        };

        ((MainActivity) getActivity()).registerMyOnTouchListener(myOnTouchListener);




        return view;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            tv_icon.setImageBitmap(bitmap);

        }
    }


//    //SharedPreferences
//    private Runnable task = new Runnable() {
//        public void run() {
//            // TODOAuto-generated method stub
//            handler.postDelayed(this, 400);//设置延迟时间
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
//            String preferencesString = preferences.getString("signature", "default_value");
//            if (preferencesString != signature.getText())
//                signature.setText(preferencesString);
//
//
//        }
//    };


    private void setAdapter() {
        //ArrayAdapterForSearch
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, data);
        acView.setAdapter(adapter1);
        acView.setOnItemClickListener((parent, view, position, id) -> {
oList.clear();
            dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
            dBHelper.getWritableDatabase();
            SQLiteDatabase db = dBHelper.getReadableDatabase();

            Cursor cursor = db.query(false, "commodity", null, null, null ,null, null, null, null);
            if (cursor.moveToFirst()){//游标指向第一行遍历对象
                do {
                    //向适配器中添加数据
                    if (cursor.getString(cursor.getColumnIndex("tag")).contains((CharSequence) parent.getItemAtPosition(position))) {
                        oList.add(new Commodity(cursor.getInt(cursor.getColumnIndex("id")), db));

                    }

                }while (cursor.moveToNext());
            }
            cursor.close();
            Intent intent=new Intent(getActivity(), SearchExportActivity.class);
            intent.putExtra("oList", (Serializable) oList);

            startActivity(intent);


        });

        acView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获取焦点时
                    acView.showDropDown();
                }
            }
        });


    }
}






