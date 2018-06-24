package com.example.marija.pmsunews.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.marija.pmsunews.LoginActivity;
import com.example.marija.pmsunews.model.User;

import java.security.Principal;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {



    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json",
            //"Authorization", "Bearer "+ token
    })

    @GET("users")
    Call<List<User>> getUsers();

    @GET("users/{id}")
    Call<User> getUser(@Path("id") int id);

    @GET(ServiceUtils.LOGIN)
    Call<User> login(@Path("username") String username,@Path("password") String password);

    @GET("users/user/{username}")
    Call<User> getUserByUsername(@Path("username") String username);

    @POST("users")
    Call<User> addUser(@Body User user);

    @PUT("users/{id}")
    Call<User> editUser(@Body User user,@Path("id") int id);

    @DELETE("users/{id}")
    Call<User> deleteUser(@Path("id") int id);
}
