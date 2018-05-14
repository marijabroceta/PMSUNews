package com.example.marija.pmsunews.model;

import android.os.AsyncTask;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Marija on 4.4.2018..
 */

public class Comment implements Serializable{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("author")
    @Expose
    private User author;
    @SerializedName("date")
    @Expose
    private Date date;
    @SerializedName("post")
    @Expose
    private Post post;
    @SerializedName("likes")
    @Expose
    private int likes;
    @SerializedName("dislikes")
    @Expose
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
