package com.example.marija.pmsunews.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.marija.pmsunews.PostsActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.ReadPostActivity;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.PostService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.TagService;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marija on 5.4.2018..
 */

public class ReadPostFragment extends Fragment {

    View view;

    private Post post;

    private TagService tagService;
    private PostService postService;
    private TextView tags_view;
    private TextView tag_view;
    private List<Tag> tags;
    private LinearLayout linearLayout;
    private LinearLayout newLinearLayout;

    public ReadPostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){



        view = inflater.inflate(R.layout.read_post_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String jsonMyObject = null;
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            jsonMyObject = extras.getString("Post");
        }
        post = new Gson().fromJson(jsonMyObject, Post.class);

        TextView title_view = view.findViewById(R.id.title_view);
        //title_view.setText(getActivity().getIntent().getStringExtra("title"));
        title_view.setText(post.getTitle());

        TextView author_view = view.findViewById(R.id.author_view);
        //author_view.setText(getActivity().getIntent().getStringExtra("author"));
        author_view.setText(post.getAuthor().getName());

        TextView date_view = view.findViewById(R.id.date_view);
        //date_view.setText(getActivity().getIntent().getStringExtra("date"));
        String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());
        date_view.setText(newDate);

        TextView description_view = view.findViewById(R.id.description_view);
        //description_view.setText(getActivity().getIntent().getStringExtra("description"));
        description_view.setText(post.getDescription());

        TextView like_text = view.findViewById(R.id.like_text);
       //like_text.setText(getActivity().getIntent().getStringExtra("likes"));
        like_text.setText(String.valueOf(post.getLikes()));

        TextView dislike_text = view.findViewById(R.id.dislike_text);
        //dislike_text.setText(getActivity().getIntent().getStringExtra("dislikes"));
        dislike_text.setText(String.valueOf(post.getDislikes()));

        linearLayout = view.findViewById(R.id.linear_layout);

        //tags_view = view.findViewById(R.id.tags_view);
        //tags_view.setText(getActivity().getIntent().getStringExtra("tags"));
        postService = ServiceUtils.postService;
        tagService = ServiceUtils.tagService;

        Call<List<Tag>> call = tagService.getTagsByPost(post.getId());

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tags = response.body();

                newLinearLayout = new LinearLayout(getContext());
                newLinearLayout.setOrientation(LinearLayout.HORIZONTAL);

                for(Tag t:tags){
                    tags_view = new TextView(getContext());
                    tags_view.setId(t.getId());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(5,10,0,10);
                    tags_view.setTextColor(getResources().getColor(R.color.white));
                    tags_view.setClickable(true);
                    tags_view.setLayoutParams(params);
                    tags_view.setText(t.getName());

                    newLinearLayout.addView(tags_view);

                    System.out.println("******TAGS INSIDE*****");
                    System.out.println(tags_view.getId());

                    tags_view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Log.i("Tag view",String.valueOf(tags_view.getId()));
                            //Call<List<Post>> call = postService.getPostsByTag(tags_view.getId());
                            Intent intent = new Intent(getContext(),PostsActivity.class);
                            intent.putExtra("tag_id",tags_view.getId());
                            startActivity(intent);
                        }
                    });
                }
                linearLayout.addView(newLinearLayout);




            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {

            }


        });




        ImageView image_view = view.findViewById(R.id.image_view);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avengers1);
        //post.setPhoto(bitmap);

/*
        Bitmap bmp = null;
        String filename = getActivity().getIntent().getStringExtra("photo");
        try {
            FileInputStream is = getActivity().openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        image_view.setImageBitmap(bmp);*/







    }


}
