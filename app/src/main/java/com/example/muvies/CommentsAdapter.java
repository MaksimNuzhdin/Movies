package com.example.muvies;

import android.hardware.camera2.params.RecommendedStreamConfigurationMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder>{
    private final String POSITIVE_TYPE="Позитивный";
    private final String NEYTRAL_TYPE="Нейтральный";
    private final String NEGATOVE_TYPE="Негативный";
    private  List<Comment> comments= new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent,false);
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comment comment= comments.get(position);
        String type = comment.getType();
        holder.TextViewAuthor.setText(comment.getAuthor());
        holder.TextViewcomment.setText(comment.getReview());
        int colorResID=android.R.color.holo_red_light;
        switch (type){
            case POSITIVE_TYPE:
                colorResID= android.R.color.holo_green_light;
                break;
            case NEYTRAL_TYPE:
                colorResID= android.R.color.holo_orange_light;
                break;

        }
        int color= ContextCompat.getColor(holder.itemView.getContext(),colorResID);
        holder.CardViewComment.setCardBackgroundColor(color);

    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentsViewHolder extends RecyclerView.ViewHolder {
        private TextView TextViewAuthor;
        private TextView TextViewcomment;
        private CardView CardViewComment;
        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            TextViewAuthor=itemView.findViewById(R.id.TextViewAuthor);
            TextViewcomment=itemView.findViewById(R.id.TextViewcomment);
            CardViewComment=itemView.findViewById(R.id.CardViewComment);

        }
    }
}
