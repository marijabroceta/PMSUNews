package com.example.marija.pmsunews.fragments;

import android.app.Activity;


import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;



import com.example.marija.pmsunews.PostsActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.service.PostService;
import com.example.marija.pmsunews.service.ServiceUtils;


import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditPostFragment extends Fragment {

    View view;

    private EditText title_edit;
    private EditText description_edit;
    private EditText tags_edit;
    private EditText location_edit;
    private FloatingActionButton fab;
    private Button get_location;
    private Button upload_pic;

    private Integer postId;
    private Bitmap bitmap;

    private PostService postService;

    private Post post;

    public static EditPostFragment newInstance(){
        EditPostFragment epf = new EditPostFragment();
        return epf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_post_fragment,container,false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title_edit = view.findViewById(R.id.title_edit);
        description_edit = view.findViewById(R.id.description_edit);
        tags_edit = view.findViewById(R.id.tags_edit);
        location_edit = view.findViewById(R.id.location_edit);
        fab = view.findViewById(R.id.fab);
        get_location = view.findViewById(R.id.location_edit);
        upload_pic = view.findViewById(R.id.upload_btn);

        upload_pic = view.findViewById(R.id.upload_btn);
        upload_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });

        postService = ServiceUtils.postService;

        postId = getActivity().getIntent().getIntExtra("postId", 0);
        System.out.println(postId);
        Call<Post> callPost = postService.getPost(postId);
        callPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();
                if(post != null){
                    title_edit.setText(post.getTitle());
                    description_edit.setText(post.getDescription());

                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

    }

    public void editPost(){

        String title = title_edit.getText().toString();
        String description = description_edit.getText().toString();
        post.setTitle(title);
        post.setDescription(description);
        if(bitmap != null){
            post.setPhoto(bitmap);
        }else{
            post.setPhoto(post.getPhoto());
        }

        Date date = Calendar.getInstance().getTime();

        post.setDate(date);

        Call<Post> call = postService.editPost(post,postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode== 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);

                ImageView uploaded_photo = view.findViewById(R.id.uploaded_photo);
                uploaded_photo.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editPost();
                Intent intent = new Intent(getContext(), PostsActivity.class);
                startActivity(intent);
            }
        });
    }
}
