package com.example.marija.pmsunews.service;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.util.DateSerialization;
import com.example.marija.pmsunews.util.ImageSerialization;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceUtils {

    public static final String SERVICE_API_PATH = "http://192.168.0.12:8080/api/";
    public static final String POSTS = "posts";
    public static final String POST = "posts/{id}";
    public static final String LOGIN = "users/{username}/{password}";
    public static final String COMMENTSBYPOST = "comments/post/{id}";


    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Bitmap.class, ImageSerialization.getBitmapTypeAdapter()).setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();


/*
    static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();*/


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(test())
            .build();



    public static PostService postService = retrofit.create(PostService.class);
    public static UserService userService = retrofit.create(UserService.class);
    public static CommentService commentService = retrofit.create(CommentService.class);
    public static TagService tagService = retrofit.create(TagService.class);
}
