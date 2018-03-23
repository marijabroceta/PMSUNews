package com.example.marija.pmsunews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class PostsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

    public void btnCreatePostActivity(View view) {
        Intent intent = new Intent(this,CreatePostActivity.class);
        startActivity(intent);
    }

    public void btnReadPostActivity(View view) {
        Intent intent = new Intent(this,ReadPostActivity.class);
        startActivity(intent);
    }

    public void btnSettingsActivity(View view) {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }
}
