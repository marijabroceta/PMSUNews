package com.example.marija.pmsunews.service;

import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.model.Post;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CommentService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })

    @GET(ServiceUtils.COMMENTSBYPOST)
    Call<List<Comment>> getCommentsByPost(@Path("id") int id);

    @GET("comments/post/sort/bydate/{id}")
    Call<List<Comment>> sortCommentsByDate(@Path("id") int id);


    @GET("comments/post/sort/bylikes/{id}")
    Call<List<Comment>> sortCommentsByLike(@Path("id") int id);

    @GET("comments/post/sort/bydislikes/{id}")
    Call<List<Comment>> sortCommentsByDislikes(@Path("id") int id);

    @POST("comments")
    Call<ResponseBody> addComment(@Body Comment comment);

    @PUT("comments/id")
    Call<Comment> editComment(@Body Comment comment);

    @PUT("comments/{id}")
    Call<Comment> addLikeDislike(@Body Comment comment,@Path("id") int id);

    @DELETE("comments/{id}")
    Call<Comment> deleteComment(@Path("id") int id);
}
