package com.example.marija.pmsunews.service;


import com.example.marija.pmsunews.model.Post;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @GET("posts/sort/bydate")
    Call<List<Post>> sortByDate();

    @GET("posts/sort/bylikes")
    Call<List<Post>> sortByLikes();

    @GET("posts/sort/bydislikes")
    Call<List<Post>> sortByDislikes();

    @GET("posts/searchbyauthor/{username}")
    Call<List<Post>> searchByAuthor(@Path("username") String username);

    @GET("posts/tags/{name}")
    Call<List<Post>> searchByTag(@Path("name") String name);

    @POST(ServiceUtils.POSTS)
    Call<Post> createPost(@Body Post post);

    @PUT("posts/{id}")
    Call<Post> editPost(@Body Post post,@Path("id") int id);

    @PUT("posts/setTags/{postId}/{tagId}")
    Call<Post> setTagsInPost(@Path("postId") int postId,@Path("tagId") int tagId);

    @DELETE("posts/{id}")
    Call<Post> deletePost(@Path("id") int id);

}
