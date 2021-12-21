package com.example.carouselfigure.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carouselfigure.R;
import com.example.carouselfigure.Bean.Community;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterForCommunity extends RecyclerView.Adapter<RecyclerAdapterForCommunity.MyViewHolder> {
    private List<Community> CommunityList;
    private static Map isClickedMap = new HashMap<>();
    private Handler handler = new Handler();
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView community_name;
        private TextView comments_count;
        private CircleImageView icon;
        private CardView cardView_community;


        public MyViewHolder(View itemView) {
            super(itemView);
            community_name = itemView.findViewById(R.id.community_name);
            comments_count = itemView.findViewById(R.id.community_commentsCount);
            cardView_community = itemView.findViewById(R.id.cardView_community);
            icon=itemView.findViewById(R.id.icon_community);

        }
    }

    @Override
    public int getItemCount() {
        return CommunityList.size();
    }


    public RecyclerAdapterForCommunity(List<Community> CommunityList) {
        this.CommunityList = CommunityList;
    }

    private MyViewHolder holder;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item, parent, false);
       holder = new MyViewHolder(itemView);
        return holder;
    }


    //填充商品属性数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Community CommunityItem = CommunityList.get(position);
        holder.community_name.setText(CommunityItem.getName());
        holder.comments_count.setText(CommunityItem.getComments_count());
        holder.icon.setImageResource(CommunityItem.getImageId());
    }
}
