package com.example.marija.pmsunews.service;

import com.example.marija.pmsunews.model.Tag;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TagService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET("tags/post/{id}")
    Call<List<Tag>> getTagsByPost(@Path("id") int id);

    @GET("tags/getName/{name}")
    Call<Tag> getTagByName(@Path("name") String name);

    @POST("tags")
    Call<Tag> addTag(@Body Tag tag);


}
