package com.example.carouselfigure.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.carouselfigure.MainActivity;
import com.example.carouselfigure.R;
import com.example.carouselfigure.SettingsActivity;
import com.example.carouselfigure.adapter.RecyclerAdapterForComments;
import com.example.carouselfigure.entity.Comments;
import com.example.carouselfigure.sqlite.DBHelper;
import com.example.carouselfigure.util.TimeUtil;
import com.example.carouselfigure.widget.MarqueeTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;


public class mine extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = 1;
    private Handler handler = new Handler();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //细碎组件
    String text;
    private ImageView hBack;
    private CircleImageView hHead;
    private MarqueeTextView signature;
    private ImageButton wallet;
    private TextView money;
    double money_amt = 0;
    private ImageButton setting_btn;
    private Fragment setting;
    private Bitmap bitmap;
    //RecyclerView
    private RecyclerView recyclerView;
    private RecyclerAdapterForComments rcAdapter;
    private LinearLayoutManager layoutManager;
    private List<Comments> mList = new ArrayList();
    private Date date;
    private static final int IMAGE_REQUEST_CODE = 1;

    public mine(String text) {
        // Required empty public constructor
        this.text = text;
    }

    public mine() {
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
        final View view = inflater.inflate(R.layout.fragment_mine, container, false);
        date = new Date(System.currentTimeMillis());
        recyclerView = view.findViewById(R.id.comments_view);
        rcAdapter = new RecyclerAdapterForComments(mList);
        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        hBack = view.findViewById(R.id.h_back);
        hHead = view.findViewById(R.id.h_head);
        setting_btn = view.findViewById(R.id.setting_btn);
        signature = view.findViewById(R.id.user_signature);
        Glide.with(getActivity()).load(R.drawable.sc1)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(hBack);

        //action
        askPermissions();
        hHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);

            }
        });

        handler.post(task);


        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });


        return view;
    }

    private void askPermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case IMAGE_REQUEST_CODE:

                if (true) {//resultcode是setResult里面设置的code值
                    try {

                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        bitmap = BitmapFactory.decodeFile(path);
                        hHead.setImageBitmap(bitmap);
                        Glide.with(getActivity()).load(selectedImage)
                                .bitmapTransform(new BlurTransformation(getActivity(), 15), new CenterCrop(getActivity()))
                                .into(hBack);

                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private static int commentsCount = 0;
    private Runnable task = new Runnable() {
        public void run() {
            // TODOAuto-generated method stub
            handler.postDelayed(this, 100);
            //需要执行的代码
            DBHelper dBHelper = new DBHelper(getActivity(), "Data.db", null, 1);
            dBHelper.getWritableDatabase();
            SQLiteDatabase db = dBHelper.getReadableDatabase();
            Cursor cursor = db.query(false, "comments", null, null, null, null, null, null, null);
            if (cursor.getCount() != commentsCount)
                if (cursor.moveToFirst()) {//游标指向第一行遍历对象
                    do {

                        mList.add(0, new Comments(cursor.getInt(cursor.getColumnIndex("commentsId")), cursor.getString(cursor.getColumnIndex("postTime")), cursor.getString(cursor.getColumnIndex("intro")), BitmapFactory.decodeByteArray(cursor.getBlob(cursor.getColumnIndex("img")), 0, cursor.getBlob(cursor.getColumnIndex("img")).length)));
                        rcAdapter = new RecyclerAdapterForComments(mList);
                        layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(rcAdapter);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());


                    } while (cursor.moveToNext());
                }
            commentsCount = cursor.getCount();

            //点赞和时间刷新
            for (int i = 0; i < recyclerView.getChildCount(); i++) {
                ConstraintLayout layout = (ConstraintLayout) recyclerView.getChildAt(i);
                ImageButton appreciateView = layout.findViewById(R.id.appreciate_comments);
                TextView apprecaite_count = layout.findViewById(R.id.appreciate_count);
                TextView past_time = layout.findViewById(R.id.comments_passTime);
                Long dateTime = null;
                if (cursor.moveToPosition(recyclerView.getChildCount()-i-1))
                    dateTime = cursor.getLong(cursor.getColumnIndex("postTime"));
                past_time.setText(TimeUtil.getTimeFormatText(dateTime));

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
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String preferencesString = preferences.getString("signature", "default_value");
            if (preferencesString != signature.getText())
                signature.setText(preferencesString);


        }

    };

}