package com.example.carouselfigure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carouselfigure.Bean.Commodity;
import com.example.carouselfigure.R;

import java.util.List;


public class RecyclerAdapterForCommodity extends RecyclerView.Adapter<RecyclerAdapterForCommodity.MyViewHolder> {
    private List<Commodity> commodityList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View commodityView;
        //图片
        public ImageView commodity_imageView;
        //内容
        public TextView commodity_introView;
        //价格
        public TextView commodity_prizeView;
        public TextView commodity_belongView;

        public MyViewHolder(View itemView) {
            super(itemView);
            commodityView = itemView;
            commodity_imageView = itemView.findViewById(R.id.commodity_imageView);
            commodity_introView = itemView.findViewById(R.id.commodity_introView);
            commodity_prizeView = itemView.findViewById(R.id.commodity_prizeView);
            commodity_belongView = itemView.findViewById(R.id.commodity_belong);

        }
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


    public RecyclerAdapterForCommodity(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.commodity_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }


    //填充商品属性数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Commodity commodityItem = commodityList.get(position);

        holder.commodity_imageView.setImageResource(commodityItem.getImageId());
        holder.commodity_introView.setText(commodityItem.getIntroduction());
        holder.commodity_prizeView.setText(commodityItem.TYPE + String.valueOf(commodityItem.getPrize()));
        holder.commodity_belongView.setText(commodityItem.getBelong());
    }
}




