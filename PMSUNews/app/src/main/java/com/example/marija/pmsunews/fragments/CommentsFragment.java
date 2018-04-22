package com.example.marija.pmsunews.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.User;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Marija on 5.4.2018..
 */

public class CommentsFragment extends Fragment {

    View view;
    Comment comment = new Comment();
    User user = new User();
    Post post = new Post();

    public CommentsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.comment_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title_view = view.findViewById(R.id.title_comment_view);
        comment.setTitle("Movie lover");
        title_view.setText(comment.getTitle());

        TextView author_comment_view = view.findViewById(R.id.author_comment_view);
        user.setUsername("Pera");
        comment.setUser(user);
        author_comment_view.setText(comment.getUser().getUsername());

        TextView date_comment_view = view.findViewById(R.id.date_comment_view);
        //noinspection deprecation
        comment.setDate(new Date(2018-1900,03-1,26,9,34));
        date_comment_view.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(comment.getDate()));

        TextView comment_view = view.findViewById(R.id.comment_view);
        comment.setDescription("It's going to be awesome movie");
        comment_view.setText(comment.getDescription());

        TextView like_comment_text = view.findViewById(R.id.like_comment_text);
        comment.setLikes(12);
        like_comment_text.setText(String.valueOf(comment.getLikes()));

        TextView dislike_comment_text = view.findViewById(R.id.dislike_comment_text);
        comment.setDislikes(4);
        dislike_comment_text.setText(String.valueOf(comment.getDislikes()));



    }
}
