package com.example.marija.pmsunews.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.LoginActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.adapters.CommentListAdapter;
import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.CommentService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.UserService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marija on 5.4.2018..
 */

public class CommentsFragment extends Fragment {

    View view;

    private CommentService commentService;
    private UserService userService;

    private Post post;
    private User user;

    private List<Comment> comments;
    private ListView listView;
    private CommentListAdapter commentListAdapter;

    private EditText comment_edit;
    private EditText title_comment_edit;

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
        post = new Gson().fromJson(jsonMyObject, Post.class);

        listView = view.findViewById(R.id.comment_list);

        commentService = ServiceUtils.commentService;

        Call<List<Comment>> call = commentService.getCommentsByPost(post.getId());

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {

                comments = response.body();

                commentListAdapter = new CommentListAdapter(getContext(),comments);
                commentListAdapter.notifyDataSetChanged();
                listView.setAdapter(commentListAdapter);

                consultPreferences();
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

        userService = ServiceUtils.userService;
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPreferances,Context.MODE_PRIVATE);
        String userNamePref = sharedPreferences.getString(LoginActivity.Username,"");

       Call<User> call_user = userService.getUserByUsername(userNamePref);

        call_user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        title_comment_edit = view.findViewById(R.id.title_comment_edit);
        comment_edit = view.findViewById(R.id.comment_edit);
        Button btn_comment = view.findViewById(R.id.btn_comment);

        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment();
                title_comment_edit.setText("");
                comment_edit.setText("");
                FragmentTransaction t = getActivity().getSupportFragmentManager().beginTransaction();
                t.setAllowOptimization(false);
                t.detach(CommentsFragment.this).attach(CommentsFragment.this).commitAllowingStateLoss();
            }
        });



        setUp();
    }

    private void addComment(){
        Comment comment = new Comment();
        String title = title_comment_edit.getText().toString();
        comment.setTitle(title);
        String comment_text = comment_edit.getText().toString();
        comment.setDescription(comment_text);
        Date date = Calendar.getInstance().getTime();
        comment.setDate(date);
        comment.setAuthor(user);
        comment.setPost(post);
        comment.setLikes(0);
        comment.setDislikes(0);
        Call<ResponseBody> call = commentService.addComment(comment);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getContext(),"Added comment",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    private void setUp(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
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
