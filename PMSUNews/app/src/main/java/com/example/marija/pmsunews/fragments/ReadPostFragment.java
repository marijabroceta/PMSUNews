package com.example.marija.pmsunews.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;

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

        post.setDate(new Date(2018-1900,2-1,23,8,45));
        String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());
        date_view.setText(newDate);

        TextView description_view = view.findViewById(R.id.description_view);
        post.setDescription("There's a reason that all of Marvel's heroes have to come together in Infinity War, and his name is Thanos. " +
                "Thing is, exactly how powerful is he?\n" +
                "The topic of Thano's impressive power set came up during a chat with Avengers: Infinity War directors Anthony and Joe Russo, " +
                "where they revealed just how nearly indestructible Thanos happens to be. " +
                "They also used quite the comparison for his strength, suggesting that he's even stronger than the Avengers' biggest hitter.");
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


        String empty = "";
        for (Tag t:tags){
            empty+=t.getName();
            tags_view.setText(empty);
        }

        ImageView image_view = view.findViewById(R.id.image_view);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.avengers1);
        post.setPhoto(bitmap);
        image_view.setImageBitmap(post.getPhoto());

    }
}
