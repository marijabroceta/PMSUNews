package com.example.marija.pmsunews.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marija on 4.4.2018..
 */

public class Tag {
    private int id;
    private String name;
    private ArrayList<Post> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
}
