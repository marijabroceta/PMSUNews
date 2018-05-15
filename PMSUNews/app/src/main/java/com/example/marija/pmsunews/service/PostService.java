package com.example.marija.pmsunews.service;


import com.example.marija.pmsunews.model.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface PostService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET(ServiceUtils.POSTS)
    Call<List<Post>> getPosts();

    @GET(ServiceUtils.POST)
    Call<Post> getPost(@Path("id") int id);

    @GET("posts/tag{1}")
    Call<List<Post>> getPostsByTag(@Path("id") int id);

}
