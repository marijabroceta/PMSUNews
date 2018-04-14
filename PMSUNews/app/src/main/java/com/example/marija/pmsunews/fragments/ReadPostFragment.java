package com.example.marija.pmsunews.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Marija on 5.4.2018..
 */

public class ReadPostFragment extends Fragment {

    View view;
    Post post = new Post();
    User user = new User();


    ArrayList<Tag> tags = new ArrayList<>();

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
        post.setTitle("Avengers Infinity War release on April 26th");
        title_view.setText(post.getTitle());

        TextView author_view = view.findViewById(R.id.author_view);
        user.setUsername("Marija");
        post.setAuthor(user);
        author_view.setText(post.getAuthor().getUsername());

        TextView date_view = view.findViewById(R.id.date_view);
        //noinspection deprecation
        post.setDate(new Date(2018,2,23,8,45));
        date_view.setText(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate()));

        TextView description_view = view.findViewById(R.id.description_view);
        post.setDescription("Neki post");
        description_view.setText(post.getDescription());

        TextView like_text = view.findViewById(R.id.like_text);
        post.setLikes(25);
        like_text.setText(String.valueOf(post.getLikes()));

        TextView dislike_text = view.findViewById(R.id.dislike_text);
        post.setDislikes(6);
        dislike_text.setText(String.valueOf(post.getDislikes()));

        TextView tags_view = view.findViewById(R.id.tags_view);
        Tag tag = new Tag();
        Tag tag1 = new Tag();
        tag.setName("#tag1");
        tag1.setName("#tag2");
        tags.add(tag);
        tags.add(tag1);
        post.setTags(tags);

        for (Tag t:tags){
            tags_view.setText(t.getName());
        }
    }
}
