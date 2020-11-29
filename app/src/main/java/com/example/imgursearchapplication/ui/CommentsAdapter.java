package com.example.imgursearchapplication.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.imgursearchapplication.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentViewHolder> {
    List<String> commentsList;
    public CommentsAdapter(List<String> comments) {
        commentsList = comments;
    }

    public void updateComments(List<String> comments) {
        commentsList = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_comment_item, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(commentsList.get(position));
    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView comment_text;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            comment_text = itemView.findViewById(R.id.comment_text);
        }
        void bind(String comment) {
            comment_text.setText(comment);
        }
    }
}
