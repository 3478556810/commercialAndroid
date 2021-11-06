package com.example.carouselfigure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carouselfigure.sqlite.DBHelper;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ReadyPostActivity extends AppCompatActivity {
    private DBHelper dBHelper;

    private ImageButton cancelBtn;
    private TextView postFinish;
    private EditText intro;
    private int imgId;
    private Button addPic;
    private ImageView imgPre;
    private Bitmap bitmap;
    private static int idCount=0;
    //constant
    private static final int IMAGE_REQUEST_CODE = 1;
    private Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_post);
        //id
        cancelBtn = findViewById(R.id.post_cancelBtn);
        postFinish = findViewById(R.id.postFinishBtn);
        intro = findViewById(R.id.post_intro);
        addPic = findViewById(R.id.addPic);
        imgPre = findViewById(R.id.imgPre);
        date = new Date(System.currentTimeMillis());
        TextView communityName=findViewById(R.id.communityName);
        //action
        Intent it=getIntent();
        String communityTypeName=it.getExtras().getString("communityType");
        communityName.setText(communityTypeName);
        cancelBtn.setOnClickListener(new View  .OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发帖成功
        postFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("intro", intro.getText().toString());
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                intent.putExtra("img", bs.toByteArray());
                ReadyPostActivity.this.setResult(RESULT_OK, intent);
                //SQLite
                dBHelper = new DBHelper(ReadyPostActivity.this, "Data.db", null, 1);
                dBHelper.getWritableDatabase();
                SQLiteDatabase db = dBHelper.getReadableDatabase();
                ContentValues values = new ContentValues();
                Cursor cursor = db.query(false, "comments", null, null, null ,null, null, null, null);
                cursor.moveToLast();
                if(cursor.getCount()!=0)
                values.put("commentsId", cursor.getInt(cursor.getColumnIndex("commentsId"))+1);
                else  values.put("commentsId", 0);
                cursor.close();
                //values.put("posterId", 0);
                values.put("intro", String.valueOf(intro.getText()));
                values.put("postTime", String.valueOf(date.getTime()));
                ByteArrayOutputStream bsi = new ByteArrayOutputStream();
                Bitmap bitmap1 = bitmap;
                bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, bsi);
                values.put("img",bsi.toByteArray());
                values.put("appreciatedCount", 0);
                db.insert("comments", null, values);


                finish();
            }
        });
        //添加图片
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }
        });
        //coreCode


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case IMAGE_REQUEST_CODE:

                if (true) {//resultcode是setResult里面设置的code值
                    try {

                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        bitmap = BitmapFactory.decodeFile(path);
                        imgPre.setImageBitmap(bitmap);


                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

}