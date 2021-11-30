package com.example.carouselfigure.EntertainmentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.carouselfigure.R;

public class EntertainmentActivityForCombat extends AppCompatActivity {


    private ImageView animContainer;
    private Button commonAtkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        animContainer=findViewById(R.id.myFirstAnim);

              animContainer.setImageResource(R.drawable.anim1);
              AnimationDrawable drawable = (AnimationDrawable) animContainer.getDrawable();
              drawable.setOneShot(true);
              drawable.start();
          }



    }





