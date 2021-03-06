package com.example.marija.pmsunews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    public static final String MyPreferances = "MyPrefs";
    public static final String Username = "usernameKey";
    public static final String Name = "nameKey";
    public static final String Role = "roleKey";

    private UserService userService;

    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    private TextView registerText;
    private TextView skipText;

    private SharedPreferences sharedPreferences;
    private static User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userService = ServiceUtils.userService;

        editUsername = findViewById(R.id.username_edit);
        editPassword = findViewById(R.id.password_edit);
        btnLogin = findViewById(R.id.login_btn);
        registerText = findViewById(R.id.register);
        skipText = findViewById(R.id.skip);

        sharedPreferences = getSharedPreferences(MyPreferances,Context.MODE_PRIVATE);

        String userNamePref = sharedPreferences.getString(Username,"");

        System.out.println(userNamePref);


        if(userNamePref.equals("")){
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = editUsername.getText().toString();
                    String password = editPassword.getText().toString();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    if(validateLogin(username,password)){
                        editor.putString(Username,username);
                        editor.commit();
                        doLogin(username,password);

                    }
                }
            });

            registerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                    startActivity(i);
                }
            });
            skipText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), PostsActivity.class);
                    startActivity(intent);
                }
            });
        }else{
            Intent intent = new Intent(this,PostsActivity.class);
            startActivity(intent);
        }

    }

    private boolean validateLogin(String username,String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this,"Username is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this,"Password is required",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin(final String username, final String password){

        Call<User> call = userService.login(username,password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();

                if(user == null){
                    Toast.makeText(LoginActivity.this,"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                }else{
                    if(username.equals(user.getUsername()) && password.equals(user.getPassword())){

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Name,user.getName());
                        editor.putString(Role,user.getRole().toString());
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this,PostsActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(LoginActivity.this,"Username or password is incorrect",Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
