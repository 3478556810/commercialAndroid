package com.example.carouselfigure.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.carouselfigure.R;
import com.example.carouselfigure.ReadyPostActivity;
import com.example.carouselfigure.adapter.RecyclerAdapterForComments;
import com.example.carouselfigure.adapter.RecyclerAdapterForCommunity;
import com.example.carouselfigure.entity.Comments;
import com.example.carouselfigure.entity.Community;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.util.TimeUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class community extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = -1;
    private static int communityPosition = 0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //??
    private Date date;
    private Handler handler = new Handler();
    private DBHelper dBHelper;
    //post
    private int REQUEST_CODE = 1;
    //RecyclerView
    private RecyclerView recyclerViewForCommunityType;
    private RecyclerAdapterForCommunity rcAdapterForCommunityType;
    private LinearLayoutManager layoutManagerForCommunityType;
    private List<Community> mList = new ArrayList();
    private RecyclerView recyclerViewForComments;
    private RecyclerAdapterForComments rcAdapterForComments;
    private LinearLayoutManager layoutManagerForComments;
    private List<Comments> cList = new ArrayList();
    String text;
    private FloatingActionButton postBtn;

    public community(String text) {
        // Required empty public constructor
        this.text = text;


    }

    public community() {
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
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_community, container, false);
        date = new Date(System.currentTimeMillis());
        recyclerViewForCommunityType = view.findViewById(R.id.community_view);


//发帖功能
        postBtn = view.findViewById(R.id.postBtn);


        //ForCommunityType
        mList.add(new Community(0, R.drawable.community_life, "生活区", "热度 2200"));
        mList.add(new Community(1, R.drawable.community_device, "数码区", "热度 4396"));
        mList.add(new Community(2, R.drawable.community_clothe, "服装区", "热度 2800"));
        mList.add(new Community(3, R.drawable.community_entertainment, "娱乐区", "热度 443"));

        rcAdapterForCommunityType = new RecyclerAdapterForCommunity(mList);
        layoutManagerForCommunityType = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        recyclerViewForCommunityType.setLayoutManager(layoutManagerForCommunityType);
        recyclerViewForCommunityType.setAdapter(rcAdapterForCommunityType);
        recyclerViewForCommunityType.setItemAnimator(new DefaultItemAnimator());


        //ForComments
        recyclerViewForComments = view.findViewById(R.id.community_commentsView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.commodity_shancadoll);
        DBHelper dBHelper = new DBHelper(getActivity(), "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Cursor cursor = db.query(false, "comments", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {//游标指向第一行遍历对象
            do {

                cList.add(0, new Comments(cursor.getInt(cursor.getColumnIndex("commentsId")), cursor.getString(cursor.getColumnIndex("postTime")), cursor.getString(cursor.getColumnIndex("intro")), BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex("img")), 0, cursor.getBlob(cursor.getColumnIndex("img")).length)));

            } while (cursor.moveToNext());
        }
        cursor.close();
        rcAdapterForComments = new RecyclerAdapterForComments(cList);
        layoutManagerForComments = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerViewForComments.setLayoutManager(layoutManagerForComments);
        recyclerViewForComments.setAdapter(rcAdapterForComments);
        recyclerViewForComments.setItemAnimator(new DefaultItemAnimator());

        handler.post(taskForCommunity);
        handler.post(taskForComments);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String str = data.getExtras().getString("intro");

            cList.add(0, new Comments(1, TimeUtil.getTimeFormatText(date.getTime()), str, BitmapFactory.decodeByteArray(data.getByteArrayExtra("img"), 0, data.getByteArrayExtra("img").length)));
            rcAdapterForComments = new RecyclerAdapterForComments(cList);
            rcAdapterForComments.notifyItemInserted(0);
            layoutManagerForComments = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
            layoutManagerForComments.scrollToPosition(0);
            recyclerViewForComments.setLayoutManager(layoutManagerForComments);
            recyclerViewForComments.setAdapter(rcAdapterForComments);
        }
    }

    static int l = 0;
    static boolean flag = false;
    private Runnable taskForCommunity = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this, 50);
            //需要执行的代码
            //communityType


            for (int i = 0; i < recyclerViewForCommunityType.getChildCount(); i++) {

                //                Log.v("clicked", String.valueOf(mList.get(i).getClicked()));
//                Log.v("position", String.valueOf(i));

                ConstraintLayout layout = (ConstraintLayout) recyclerViewForCommunityType.getChildAt(i);
                CardView cardView = layout.findViewById(R.id.cardView_community);
                //第一次请把第一个item标蓝
                if (!flag) {

                    cardView.setCardBackgroundColor(Color.parseColor("#ABE0F8"));
                    communityPosition = 0;
                    flag = true;
                }
                final int finalI = i;
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mList.get(finalI).getClicked()) {
                            cardView.setCardBackgroundColor(Color.parseColor("#ABE0F8"));
                            mList.get(finalI).setClicked(true);
                            l = finalI;
                            communityPosition = l;
                        }

                    }

                });
            }

            for (int j = 0; j < recyclerViewForCommunityType.getChildCount(); j++) {
                ConstraintLayout layout = (ConstraintLayout) recyclerViewForCommunityType.getChildAt(j);
                CardView cardView = layout.findViewById(R.id.cardView_community);
                if (j != l) {
                    cardView.setCardBackgroundColor(Color.parseColor("#DEEFEF"));
                    mList.get(j).setClicked(false);
                }
            }
            postBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getActivity(), ReadyPostActivity.class);
                    ConstraintLayout layout = (ConstraintLayout) recyclerViewForCommunityType.getChildAt(communityPosition);
                    TextView communityType = layout.findViewById(R.id.community_name);
                    intent.putExtra("communityType", communityType.getText());
                    startActivityForResult(intent, REQUEST_CODE);

                }
            });
        }

    };


    private Runnable taskForComments = new Runnable() {
        public void run() {
            handler.postDelayed(this, 500);
            //comments
            //点赞和时间刷新

            for (int i = 0; i < recyclerViewForComments.getChildCount(); i++) {
                ConstraintLayout layout = (ConstraintLayout) recyclerViewForComments.getChildAt(i);
                ImageButton appreciateView = layout.findViewById(R.id.appreciate_comments);
                TextView apprecaite_count = layout.findViewById(R.id.appreciate_count);
                TextView past_time = layout.findViewById(R.id.comments_passTime);
                //
                DBHelper dBHelper = new DBHelper(getActivity(), "Data.db", null, 1);
                dBHelper.getWritableDatabase();
                SQLiteDatabase db = dBHelper.getReadableDatabase();
                Cursor cursor = db.query(false, "comments", null, null, null, null, null, null, null);
                Long dateTime = null;
                if (cursor.moveToPosition(recyclerViewForComments.getChildCount() - i - 1))
                    dateTime = cursor.getLong(cursor.getColumnIndex("postTime"));
                cursor.close();
                past_time.setText(TimeUtil.getTimeFormatText(dateTime));
                ViewFlipper flipper = layout.findViewById(R.id.commodity_flipper);
                flipper.startFlipping();

                appreciateView.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {

                        appreciateView.setBackgroundResource(R.drawable.appreciated);
                        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) appreciateView.getLayoutParams();
                        linearParams.topMargin = 60;
                        linearParams.width = 60;
                        linearParams.height = 80;
                        appreciateView.setLayoutParams(linearParams);
                        apprecaite_count.setText(String.valueOf(Integer.parseInt((String) apprecaite_count.getText()) + 1));
                    }

                });
            }

        }

    };
}