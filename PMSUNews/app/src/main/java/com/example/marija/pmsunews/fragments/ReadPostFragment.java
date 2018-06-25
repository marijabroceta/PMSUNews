package com.example.marija.pmsunews.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.marija.pmsunews.LoginActivity;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marija on 5.4.2018..
 */

public class ReadPostFragment extends Fragment {

    View view;

    private Post post;
    private Integer postId;

    private TagService tagService;
    private PostService postService;


    private TextView title_view;
    private TextView author_view;
    private TextView date_view;
    private TextView description_view;
    private TextView like_text;
    private TextView dislike_text;
    private ImageView image_view;
    private TextView tags_view;
    private TextView place;
    private ImageButton like_view;
    private ImageButton dislike_view;


    private List<Tag> tags;
    private LinearLayout linearLayout;
    private LinearLayout newLinearLayout;
    private int counterLikes;
    private int counterDislikes;

    private boolean clickedLike;
    private boolean clickedDislike;

    private SharedPreferences sharedPreferences;
    private String userNamePref;
    private String rolePref;

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

        title_view = view.findViewById(R.id.title_view);
        author_view = view.findViewById(R.id.author_view);
        date_view = view.findViewById(R.id.date_view);
        description_view = view.findViewById(R.id.description_view);
        like_text = view.findViewById(R.id.like_text);
        dislike_text = view.findViewById(R.id.dislike_text);
        image_view = view.findViewById(R.id.image_view);
        place = view.findViewById(R.id.place);
        linearLayout = view.findViewById(R.id.linear_layout);
        tags_view = view.findViewById(R.id.tags_view);

        postService = ServiceUtils.postService;
        tagService = ServiceUtils.tagService;

        postId = getActivity().getIntent().getIntExtra("postId", 0);

        Call<Post> callPost = postService.getPost(postId);

        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();
                if(post != null){
                    title_view.setText(post.getTitle());
                    author_view.setText(post.getAuthor().getName());
                    String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());
                    date_view.setText(newDate);
                    description_view.setText(post.getDescription());
                    image_view.setImageBitmap(post.getPhoto());
                    like_text.setText(String.valueOf(post.getLikes()));
                    dislike_text.setText(String.valueOf(post.getDislikes()));
                    getAddress(post.getLatitude(),post.getLongitude());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        Call<List<Tag>> call = tagService.getTagsByPost(postId);

        call.enqueue(new Callback<List<Tag>>() {
            @Override
            public void onResponse(Call<List<Tag>> call, Response<List<Tag>> response) {
                tags = response.body();

                String empty = "";
                for(Tag t:tags) {
                    empty+= t.getName();
                    tags_view.setText(empty);
                }
            }

            @Override
            public void onFailure(Call<List<Tag>> call, Throwable t) {

            }
        });

        like_view = view.findViewById(R.id.like_view);
        dislike_view = view.findViewById(R.id.dislike_view);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MyPreferances, Context.MODE_PRIVATE);
        userNamePref = sharedPreferences.getString(LoginActivity.Username,"");
        rolePref = sharedPreferences.getString(LoginActivity.Role,"");

        if(rolePref.equals("USER")){
            like_view.setEnabled(false);
            dislike_view.setEnabled(false);
        }

        clickedLike = false;
        like_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNamePref.equals(post.getAuthor().getUsername())){
                    Toast.makeText(getContext(),"You can't like your post",Toast.LENGTH_SHORT).show();
                }else{
                    if(clickedLike == false){
                        addLike();
                        like_text.setText(String.valueOf(post.getLikes()));
                        clickedLike = true;
                        dislike_view.setEnabled(false);
                    }else{
                        removeLike();
                        like_text.setText(String.valueOf(post.getLikes()));
                        clickedLike = false;
                        dislike_view.setEnabled(true);
                    }
                }
            }
        });


        clickedDislike = false;
        dislike_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNamePref.equals(post.getAuthor().getUsername())){
                    Toast.makeText(getContext(),"You can't dislike your post",Toast.LENGTH_SHORT).show();
                }else{
                    if(clickedDislike == false){
                        addDislike();
                        dislike_text.setText(String.valueOf(post.getDislikes()));
                        clickedDislike = true;
                        like_view.setEnabled(false);
                    }else{

                        removeDislike();
                        dislike_text.setText(String.valueOf(post.getDislikes()));
                        clickedDislike = false;
                        like_view.setEnabled(false);
                    }

                }
            }
        });

    }


    public void addLike(){

        counterLikes = post.getLikes();

        post.setLikes(counterLikes+1);

        System.out.println(post.getId());
        System.out.println(post.getLikes());

        Call<Post> call = postService.editPost(post,post.getId());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                like_view.setImageResource(R.drawable.ic_like_green);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void addDislike(){

        counterDislikes = post.getDislikes();
        post.setDislikes(counterDislikes+1);

        Call<Post> call = postService.editPost(post,post.getId());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                dislike_view.setImageResource(R.drawable.ic_dislike_red);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    public void removeLike(){
        counterLikes = post.getLikes();
        post.setLikes(counterLikes-1);
        Call<Post> call = postService.editPost(post,post.getId());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                like_view.setImageResource(R.drawable.ic_action_like_white);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void removeDislike(){
        counterDislikes = post.getDislikes();
        post.setDislikes(counterDislikes - 1);
        Call<Post> call = postService.editPost(post,post.getId());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                dislike_view.setImageResource(R.drawable.ic_action_dislike_white);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    public void getAddress(double latituda,double longituda){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        //double lon = post.getLongitude();
        //double lat = post.getLatitude();
        try {
            addresses = geocoder.getFromLocation(latituda, longituda, 1);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            place.setText(city + "," + country);


            System.out.println(city);
            System.out.println(country);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
