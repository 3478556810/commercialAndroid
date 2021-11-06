package com.example.carouselfigure.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carouselfigure.entity.Comments;
import com.example.carouselfigure.R;

import java.util.List;


public class RecyclerAdapterForComments extends RecyclerView.Adapter<RecyclerAdapterForComments.MyViewHolder> {
    private List<Comments> CommentsList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
     private TextView comments_introView;
     private TextView comments_passtime;
     private TextView appreciate_count;
     private ImageButton appreciate_View;

     private ImageView imgView;
        public MyViewHolder(View itemView) {
            super(itemView);
comments_introView=itemView.findViewById(R.id.comments_introView);
comments_passtime=itemView.findViewById(R.id.comments_passTime);
appreciate_count=itemView.findViewById(R.id.appreciate_count);
appreciate_View=itemView.findViewById(R.id.appreciate_comments);
imgView= itemView.findViewById(R.id.comments_imgView);
        }
    }

    @Override
    public int getItemCount() {
        return CommentsList.size();
    }



    public RecyclerAdapterForComments(List<Comments> CommentsList) {
        this.CommentsList = CommentsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_item, parent, false);
        final MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }






    //填充商品属性数据
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comments CommentsItem = CommentsList.get(position);
//        holder.Comments_imageView.setImageResource(CommentsItem.getImageId());
//        holder.Comments_introView.setText(CommentsItem.getIntroduction());
//        holder.Comments_prizeView.setText(CommentsItem.TYPE+String.valueOf(CommentsItem.getPrize()));

        holder.comments_introView.setText(CommentsItem.getIntroduction());
        holder.comments_passtime.setText(CommentsItem.getPass_time());
        holder.imgView.setImageBitmap(CommentsItem.getImg());

   }
}




