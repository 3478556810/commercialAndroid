package com.example.carouselfigure.EntertainmentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carouselfigure.R;
import com.plattysoft.leonids.ParticleSystem;
import com.plattysoft.leonids.modifiers.ScaleModifier;
import com.wang.avi.AVLoadingIndicatorView;

import cn.bmob.v3.util.V;

public class EntertainmentActivityForHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment_for_home);
findViewById(R.id.avloadingIndicatorView_BallClipRotatePulse).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(EntertainmentActivityForHome.this,EntertainmentActivityForDialogue.class));
    }
});



    }










}