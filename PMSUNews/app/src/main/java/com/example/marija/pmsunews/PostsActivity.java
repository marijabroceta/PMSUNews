package com.example.marija.pmsunews;

import android.content.Intent;
import android.content.res.Configuration;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.marija.pmsunews.adapters.DrawerListAdapter;
import com.example.marija.pmsunews.adapters.PostListAdapter;
import com.example.marija.pmsunews.model.NavItem;
import com.example.marija.pmsunews.model.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("deprecation")
public class PostsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private ArrayList<Post> posts = new ArrayList<Post>();

    private Post post = new Post();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        prepareMenu(mNavItems);

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.nav_list);

        mDrawerPane = findViewById(R.id.drawer_pane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);


        Toolbar toolbar = findViewById(R.id.post_toolbar);
        setSupportActionBar(toolbar);
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            //actionBar.setIcon(R.drawable.ic_launcher_background);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setHomeButtonEnabled(true);
        }

       mDrawerToggle = new ActionBarDrawerToggle(
               this,
               mDrawerLayout,
               toolbar,
               R.string.drawer_open,
               R.string.drawer_close
       ){
            public void onDrawerClosed(View view){
                getSupportActionBar().setTitle(mTitle);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView){
                getSupportActionBar().setTitle("PMSUNews");
                invalidateOptionsMenu();
            }
       };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PostsActivity.this,CreatePostActivity.class);
                startActivity(intent);
            }
        });


        post.setDate(new Date(2018-1900,2-1,23,8,45));
        //String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());
        post.setTitle("Avengers Infinity War release on April 26th");

        post.getDate();
        post.getTitle();

        posts.add(post);

        PostListAdapter postListAdapter = new PostListAdapter(this,posts);
        ListView listView = findViewById(R.id.post_list);

        //postListAdapter.add(post);
        listView.setAdapter(postListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PostsActivity.this,ReadPostActivity.class);
                startActivity(intent);
            }
        });

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

    private void prepareMenu(ArrayList<NavItem> mNavItems ){
        mNavItems.add(new NavItem(getString(R.string.home), getString(R.string.all_post), R.drawable.ic_action_home));
        mNavItems.add(new NavItem(getString(R.string.create_post),getString(R.string.create_post_long),R.drawable.ic_action_add));
        mNavItems.add(new NavItem(getString(R.string.preferances), getString(R.string.preferance_long), R.drawable.ic_action_settings));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_post, menu);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_create_post:
                Toast.makeText(this,"Create post",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_search:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItemFromDrawer(position);
        }
    }

    private void selectItemFromDrawer(int position){
        if(position == 0){
            Intent homeIntent = new Intent(this, PostsActivity.class);
            startActivity(homeIntent);
        }else if(position == 1){
            Intent createIntent = new Intent(this,CreatePostActivity.class);
            startActivity(createIntent);
        }else if(position == 2){
            Intent preferanceIntent = new Intent(this,SettingsActivity.class);
            startActivity(preferanceIntent);
        }

        mDrawerList.setItemChecked(position, true);
        setTitle(mNavItems.get(position).getmTitle());
        mDrawerLayout.closeDrawer(mDrawerPane);
    }



    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
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
