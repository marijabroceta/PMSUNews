package com.example.marija.pmsunews;

import android.content.Intent;
import android.content.res.Configuration;

import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.marija.pmsunews.adapters.DrawerListAdapter;
import com.example.marija.pmsunews.model.NavItem;

import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class PostsActivity extends AppCompatActivity {

    private DrawerLayout myDrawerLayout;
    private ListView myDrawerList;
    private ActionBarDrawerToggle myDrawerToggle;
    private RelativeLayout myDrawerPane;
    private CharSequence myDrawerTitle;
    private CharSequence myTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);



        Toolbar toolbar = findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setIcon(R.drawable.ic_launcher_background);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }



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
