package com.example.carouselfigure;
//constants

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.carouselfigure.adapter.RecyclerAdapterForCommodity;
import com.example.carouselfigure.entity.Commodity;
import com.example.carouselfigure.sqlite.DBHelper;

import java.util.ArrayList;
import java.util.List;


public class SearchExportActivity extends AppCompatActivity {


    //RecyclerView
    public RecyclerView recyclerView;
    private RecyclerAdapterForCommodity rcAdapter;
    private StaggeredGridLayoutManager layoutManager;
    private List<Commodity> mList = new ArrayList();


    //??
    private DBHelper dBHelper;
    private Fragment mine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_export);
        recyclerView = findViewById(R.id.exportCommodity_view);

        dBHelper = new DBHelper(this, "Data.db", null, 1);
        dBHelper.getWritableDatabase();
        SQLiteDatabase db = dBHelper.getReadableDatabase();
        Intent intent=getIntent();
        ArrayList<Commodity> oList = (ArrayList<Commodity>) intent.getSerializableExtra("oList");
        rcAdapter = new RecyclerAdapterForCommodity(oList);
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //每行两个瀑布流排列
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rcAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
}