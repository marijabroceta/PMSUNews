package com.example.marija.pmsunews.fragments;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.adapters.CommentListAdapter;
import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Marija on 5.4.2018..
 */

public class CommentsFragment extends Fragment {

    View view;
    Comment comment1 = new Comment();
    Comment comment2 = new Comment();
    User user1 = new User();
    User user2 = new User();

    private ArrayList<Comment> comments = new ArrayList<>();
    //Post post = new Post();
    private CommentListAdapter commentListAdapter;

    private SharedPreferences sharedPreferences;

    private boolean sortCommentsByDate;
    private boolean sortCommentsByPopularity;

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
/*
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
        dislike_comment_text.setText(String.valueOf(comment.getDislikes()));*/

        comment1.setTitle("Movie lover");
        user1.setUsername("Pera");
        comment1.setUser(user1);
        comment1.setDate(new Date(2018-1900,03-1,26,9,34));
        comment1.setDescription("It's going to be awesome movie");
        comment1.setLikes(12);
        comment1.setDislikes(4);

        comment2.setTitle("Super awesome");
        user2.setUsername("Jova");
        comment2.setUser(user2);
        comment2.setDate(new Date(2018-1900,03-1,24,9,34));
        comment2.setDescription("I can't wait to watch it");
        comment2.setLikes(15);
        comment2.setDislikes(5);

        comments.add(comment1);
        comments.add(comment2);

        commentListAdapter = new CommentListAdapter(getContext(),comments);
        ListView listView = view.findViewById(R.id.comment_list);

        listView.setAdapter(commentListAdapter);

        setUp();
    }

    private void setUp(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        consultPreferences();
    }

    private void consultPreferences(){
        sortCommentsByDate = sharedPreferences.getBoolean(getString(R.string.pref_sort_comment_by_date_key),false);
        sortCommentsByPopularity = sharedPreferences.getBoolean(getString(R.string.pref_sort_comment_by_popularity_key),false);

        if(sortCommentsByDate == true){
            sortByDate();
        }

        if(sortCommentsByPopularity == true){
            sortByPopularity();
        }
    }

    public void sortByDate(){
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment comment, Comment comment1) {
                return comment1.getDate().compareTo(comment.getDate());
            }
        });


        commentListAdapter.notifyDataSetChanged();
    }

    public void sortByPopularity(){
        Collections.sort(comments, new Comparator<Comment>() {
            @Override
            public int compare(Comment comment, Comment comment1) {
                int first;
                int second;
                first = comment.getLikes() - comment.getDislikes();
                second = comment1.getLikes() - comment1.getDislikes();
                return Integer.valueOf(second).compareTo(first);
            }
        });

        commentListAdapter.notifyDataSetChanged();
    }


}
