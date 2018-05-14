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
import com.example.marija.pmsunews.service.CommentService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marija on 5.4.2018..
 */

public class CommentsFragment extends Fragment {

    View view;

    private CommentService commentService;

    private List<Comment> comments;
    private ListView listView;
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

        String jsonMyObject = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("Post");
        }
        Post post = new Gson().fromJson(jsonMyObject, Post.class);

        listView = view.findViewById(R.id.comment_list);

        commentService = ServiceUtils.commentService;

        Call<List<Comment>> call = commentService.getCommentsByPost(post.getId());

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                comments = response.body();
                commentListAdapter = new CommentListAdapter(getContext(),comments);
                listView.setAdapter(commentListAdapter);
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });


        setUp();
    }

    private void setUp(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //consultPreferences();
    }
/*
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
*/


}
