package com.example.marija.pmsunews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    public CommentListAdapter(Context context, List<Comment> comments){super(context,0,comments);}

    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        Comment comment = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item,viewGroup,false);
        }

        TextView title_view = view.findViewById(R.id.title_comment_view);
        TextView author_view = view.findViewById(R.id.author_comment_view);
        TextView date_view = view.findViewById(R.id.date_comment_view);
        String formatedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(comment.getDate());
        TextView comment_view = view.findViewById(R.id.comment_view);
        TextView like_view = view.findViewById(R.id.like_comment_text);
        TextView dislike_view = view.findViewById(R.id.dislike_comment_text);


        title_view.setText(comment.getTitle());
        author_view.setText(comment.getAuthor().getName());
        date_view.setText(formatedDate);
        comment_view.setText(comment.getDescription());
        like_view.setText(String.valueOf(comment.getLikes()));
        dislike_view.setText(String.valueOf(comment.getDislikes()));

        return view;
    }
}
