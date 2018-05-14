package com.example.marija.pmsunews.adapters;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostListAdapter extends ArrayAdapter<Post> {


    public PostListAdapter(Context context, List<Post> posts){
        super(context,0,posts);
    }




    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        final Post post = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item,viewGroup,false);
        }

        final TextView date_view = view.findViewById(R.id.date_view);
        final TextView title_view = view.findViewById(R.id.title_view);
        final ImageView image_view = view.findViewById(R.id.image_view);



        String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());


        date_view.setText(newDate);
        title_view.setText(post.getTitle());

        image_view.setImageBitmap(post.getPhoto());


        return view;
    }


}
