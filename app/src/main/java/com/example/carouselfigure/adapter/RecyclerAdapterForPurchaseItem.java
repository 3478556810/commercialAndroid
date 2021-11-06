package com.example.carouselfigure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carouselfigure.R;
import com.example.carouselfigure.entity.Commodity;

import java.util.List;

public class RecyclerAdapterForPurchaseItem extends RecyclerView.Adapter<RecyclerAdapterForPurchaseItem.MyViewHolder> {
    private List<Commodity> commodityList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View purchaseView;
        //图片
        public ImageView purchase_imageView;
        //内容
        public TextView purchase_introView;
        //价格
        public TextView purchase_prizeView;
        //店铺
        public TextView purchase_storeView;

        public TextView purchase_singleItemCount;
        public MyViewHolder(View itemView) {
            super(itemView);
            purchaseView = itemView;
            purchase_imageView = itemView.findViewById(R.id.purchase_imageView);
            purchase_introView = itemView.findViewById(R.id.purchase_introView);
            purchase_prizeView = itemView.findViewById(R.id.purchase_prizeView);
            purchase_storeView = itemView.findViewById(R.id.purchase_storeView);
purchase_singleItemCount=itemView.findViewById(R.id.purchaseSingleTypeItemCount);
        }
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }


    public RecyclerAdapterForPurchaseItem(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

    @NonNull
    @Override
    public RecyclerAdapterForPurchaseItem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
        final RecyclerAdapterForPurchaseItem.MyViewHolder holder = new RecyclerAdapterForPurchaseItem.MyViewHolder(itemView);
        return holder;
    }


    //填充商品属性数据
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterForPurchaseItem.MyViewHolder holder, int position) {
        Commodity commodityItem = commodityList.get(position);
        holder.purchase_imageView.setImageResource(commodityItem.getImageId());
        holder.purchase_introView.setText(commodityItem.getIntroduction());
        holder.purchase_prizeView.setText(String.valueOf(commodityItem.getPrize()));
        holder.purchase_storeView.setText(commodityItem.getBelong());

    }
}
