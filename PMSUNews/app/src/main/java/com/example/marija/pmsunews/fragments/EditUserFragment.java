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

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.UsersActivity;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.UserService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditUserFragment extends Fragment {

    View view;

    private EditText name;
    private EditText username;
    private EditText password;
    private ImageView user_image;
    private Button upload_photo;
    private FloatingActionButton fab;
    private Bitmap bitmap;

    private UserService userService;
    private int userId;
    private User user;

    public static EditUserFragment newInstance(){
        EditUserFragment euf = new EditUserFragment();
        return euf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.edit_user_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        password = view.findViewById(R.id.password);
        upload_photo = view.findViewById(R.id.upload_btn_user);
        fab = view.findViewById(R.id.fab);

        upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });

        userService = ServiceUtils.userService;
        userId = getActivity().getIntent().getIntExtra("userId",0);

        Call<User> call = userService.getUser(userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                user = response.body();
                if(user != null){
                    name.setText(user.getName());
                    username.setText(user.getUsername());
                    password.setText(user.getPassword());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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

                ImageView uploaded_photo = view.findViewById(R.id.uploaded_photo_user);
                uploaded_photo.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    public void editUser(){
        String name_string = name.getText().toString();
        String username_string = username.getText().toString();
        String password_string = password.getText().toString();

        user.setName(name_string);
        user.setUsername(username_string);
        user.setPassword(password_string);
        if(bitmap != null){
            user.setPhoto(bitmap);
        }else{
            user.setPhoto(user.getPhoto());
        }

        Call<User> call = userService.editUser(user,userId);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUser();
                Intent intent = new Intent(getContext(), UsersActivity.class);
                startActivity(intent);
            }
        });
    }
}
