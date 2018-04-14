package com.example.marija.pmsunews.model;

import android.os.AsyncTask;

import java.util.Date;

/**
 * Created by Marija on 4.4.2018..
 */

public class Comment {

    private int id;
    private String title;
    private String description;
    private User user;
    private Date date;
    private Post post;
    private int likes;
    private int dislikes;
    private AsyncTask.Status status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public AsyncTask.Status getStatus() {
        return status;
    }

    public void setStatus(AsyncTask.Status status) {
        this.status = status;
    }
}
