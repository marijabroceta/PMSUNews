package com.example.marija.pmsunews.fragments;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marija.pmsunews.PostsActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.ReadPostActivity;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Marija on 5.4.2018..
 */

public class ReadPostFragment extends Fragment {

    View view;

    Post post = new Post();
    User user = new User();


    //ArrayList<Tag> tags = new ArrayList<>();

    ArrayList<Post> posts = new ArrayList<>();


    public ReadPostFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){



        view = inflater.inflate(R.layout.read_post_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title_view = view.findViewById(R.id.title_view);
        title_view.setText(getActivity().getIntent().getStringExtra("title"));

        TextView author_view = view.findViewById(R.id.author_view);
        author_view.setText(getActivity().getIntent().getStringExtra("author"));

        TextView date_view = view.findViewById(R.id.date_view);
        date_view.setText(getActivity().getIntent().getStringExtra("date"));

        TextView description_view = view.findViewById(R.id.description_view);
        description_view.setText(getActivity().getIntent().getStringExtra("description"));

        TextView like_text = view.findViewById(R.id.like_text);
        like_text.setText(getActivity().getIntent().getStringExtra("likes"));

        TextView dislike_text = view.findViewById(R.id.dislike_text);
        dislike_text.setText(getActivity().getIntent().getStringExtra("dislikes"));

        TextView tags_view = view.findViewById(R.id.tags_view);
        tags_view.setText(getActivity().getIntent().getStringExtra("tags"));

        ImageView image_view = view.findViewById(R.id.image_view);
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avengers1);
        //post.setPhoto(bitmap);


        Bitmap bmp = null;
        String filename = getActivity().getIntent().getStringExtra("photo");
        try {
            FileInputStream is = getActivity().openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        image_view.setImageBitmap(bmp);



        posts.add(post);



    }


}
