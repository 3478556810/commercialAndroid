package com.example.carouselfigure;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EntertainmentActivity extends AppCompatActivity {


    private ImageView animContainer;
    private Button commonAtkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        animContainer=findViewById(R.id.myFirstAnim);
        commonAtkButton=findViewById(R.id.commonAtk);

      commonAtkButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              animContainer.setImageResource(R.drawable.anim1);
              AnimationDrawable drawable = (AnimationDrawable) animContainer.getDrawable();
              drawable.setOneShot(true);
              drawable.start();
          }
      });


    }





}