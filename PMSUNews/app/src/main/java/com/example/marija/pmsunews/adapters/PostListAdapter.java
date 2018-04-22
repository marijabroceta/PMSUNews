package com.example.marija.pmsunews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PostListAdapter extends ArrayAdapter<Post> {

    public PostListAdapter(Context context, ArrayList<Post> posts){
        super(context,0,posts);
    }




    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        Post post = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item,viewGroup,false);
        }

        TextView date_view = view.findViewById(R.id.date_view);
        TextView title_view = view.findViewById(R.id.title_view);

        String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());

        date_view.setText(newDate);
        title_view.setText(post.getTitle());


        return view;
    }


}
