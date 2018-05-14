package com.example.marija.pmsunews.service;

import com.example.marija.pmsunews.model.Tag;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface TagService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("tags/post/{id}")
    Call<List<Tag>> getTagsByPost(@Path("id") int id);
}
