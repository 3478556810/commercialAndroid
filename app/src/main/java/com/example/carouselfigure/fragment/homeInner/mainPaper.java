package com.example.carouselfigure.fragment.homeInner;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.carouselfigure.cardinalActivity.MainActivity;
import com.example.carouselfigure.R;
import com.example.carouselfigure.adapter.RecyclerAdapterForCommodity;
import com.example.carouselfigure.Bean.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainPaper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainPaper extends Fragment {
    //constants
    private static final int RESULT_OK = 1;
    private static final int REQUEST_CODE = 1;
    static int TOPMARGIN = 0;
    static int HEIGH = 0;
    static int BOTTOMMARGIN = 0;
    public static double downdis = 0.0;
    //ViewFlipper
    private ViewFlipper flipper;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Handler handler = new Handler();
    private final static int MIN_MOVE = 200;   //手势移动最小距离
    private GestureDetector mDetector;
    public Context mContext;
    //RecyclerView
    private ClassicsHeader classicHeader;
    public RecyclerView recyclerView;
    private RecyclerAdapterForCommodity rcAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private List<Commodity> mList = new ArrayList();


    //??
    private DBHelper dBHelper;
    private Fragment mine;
    private long commodityLines;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainPaper() {
        // Required empty public constructor

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainPaper.
     */
    // TODO: Rename and change types and number of parameters
    public static mainPaper newInstance(String param1, String param2) {
        mainPaper fragment = new mainPaper();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main_paper, container, false);
        view.setTag("mainPaper");
        recyclerView = view.findViewById(R.id.commodity_view);
        rcAdapter = new RecyclerAdapterForCommodity(mList);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //每行两个瀑布流排列
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        flipper = view.findViewById(R.id.vf);
        flipper.startFlipping();
        rb1 = view.findViewById(R.id.rb1);
        rb2 = view.findViewById(R.id.rb2);
        rb3 = view.findViewById(R.id.rb3);
        rb4 = view.findViewById(R.id.rb4);
        RefreshLayout refreshLayout = view.findViewById(R.id.refreshLayout);
        classicHeader = view.findViewById(R.id.classicHeader);

        //action
        addMlist();
        handler.post(task);


        //RefreshLayout
        refreshLayout.setHeaderTriggerRate((float) 0.5);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                refreshlayout.finishRefresh(2000);
                //擦除再重构
                mList.clear();
                dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
                dBHelper.getWritableDatabase();
                SQLiteDatabase db = dBHelper.getReadableDatabase();
                Set<Integer> set = new HashSet<Integer>();
                if (db.query(false, "commodity", null, null, null, null, null, null, null).getCount() != 0) {
                    for (int i = 0; i < commodityLines; i++) {
                        int r;
                        do {
                            r = (int) (Math.random() * commodityLines);
                        } while (set.contains(r));
                        set.add(r);
                        mList.add(new Commodity(r, db));
                    }
                    rcAdapter = new RecyclerAdapterForCommodity(mList);
                    layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //每行两个瀑布流排列
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(rcAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                }
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);
                SmartRefreshLayout.LayoutParams linearParams = (SmartRefreshLayout.LayoutParams) classicHeader.getLayoutParams();
                linearParams.topMargin = TOPMARGIN + 40;
                linearParams.bottomMargin = BOTTOMMARGIN + 40;
                linearParams.height = HEIGH + 100;
                classicHeader.setLayoutParams(linearParams);
                //添加再重构
                dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
                dBHelper.getWritableDatabase();
                SQLiteDatabase db = dBHelper.getReadableDatabase();
                Set<Integer> set = new HashSet<Integer>();
                for (int i = 0; i < commodityLines; i++) {
                    int r;
                    do {
                        r = (int) (Math.random() * commodityLines);
                    } while (set.contains(r));
                    set.add(r);
                    mList.add(new Commodity(r, db));
                }
                layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //每行两个瀑布流排列
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(rcAdapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());

            }
        });

        //gestureListener

        //实例化SimpleOnGestureListener与GestureDetector对象
        GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override

            public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
                if (e1.getX() - e2.getX() > MIN_MOVE) {
                    flipper.showNext();
                } else if (e2.getX() - e1.getX() > MIN_MOVE) {
                    flipper.showPrevious();
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


    private void addMlist() {
        //数据库初始化
        dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Set<Integer> set = new HashSet<Integer>();
        commodityLines = DatabaseUtils.queryNumEntries(db, "commodity");
        for (int i = 0; i < commodityLines; i++) {
            int r;
            do {
                r = (int) (Math.random() * commodityLines);
            } while (set.contains(r));
            set.add(r);
            mList.add(new Commodity(0, db));

        }
    }
    //ViewFlipper AutoFlipper
    private Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this, 400);//设置延迟时间
//            float dy = recyclerView.computeVerticalScrollOffset();
//            Log.v("distance", String.valueOf(dy));
            //需要执行的代码
            //data upgrade
            mList.clear();
            dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
            dBHelper.getWritableDatabase();
            SQLiteDatabase db = dBHelper.getReadableDatabase();

            //商品删减/购买
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                ConstraintLayout layout = (ConstraintLayout) recyclerView.getChildAt(i);
                Button del_btn = layout.findViewById(R.id.del_button);
                Button purchase_btn = layout.findViewById(R.id.purchase_button);
                final int finalI = i;
                del_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mList.size() > 1)
                            mList.remove(finalI);
                        else
                            Toast.makeText(getActivity(), "只剩这一个商品了哦", Toast.LENGTH_LONG).show();
                        rcAdapter.notifyItemMoved(finalI, finalI + 1);
                        rcAdapter = new RecyclerAdapterForCommodity(mList);
                        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //每行两个瀑布流排列
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(rcAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }

                });
                purchase_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"您已购买"+mList.get(finalI).getName(),Toast.LENGTH_LONG).show();
                        ContentValues values =new ContentValues();
                        values.put("orderId",mList.get(finalI).getCommodity_id());
                        values.put("userId","null");
                        values.put("intro",mList.get(finalI).getIntroduction());
                        values.put("store",mList.get(finalI).getBelong());
                        values.put("imgId",mList.get(finalI).getImageId());
                        values.put("prize",mList.get(finalI).getPrize());
                        Toast.makeText(getActivity(),String.valueOf(values),Toast.LENGTH_LONG).show();
                        dBHelper = new DBHelper(getActivity(), "Data.db", null, 2);
                        dBHelper.getWritableDatabase();
                        SQLiteDatabase db = dBHelper.getReadableDatabase();
                        db.insert("orderT",null,values);
                    }
                });
            }

            //轮播图按钮同步
            switch (flipper.getDisplayedChild()) {
                case 0:
                    rb1.setChecked(true);
                    break;
                case 1:
                    rb2.setChecked(true);
                    break;
                case 2:
                    rb3.setChecked(true);
                    break;
                case 3:
                    rb4.setChecked(true);
                    break;
            }

        }
    };
    private void toast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();

    }
}