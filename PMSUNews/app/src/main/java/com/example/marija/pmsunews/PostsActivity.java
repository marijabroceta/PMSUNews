package com.example.marija.pmsunews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.preference.PreferenceManager;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.adapters.DrawerListAdapter;
import com.example.marija.pmsunews.adapters.PostListAdapter;
import com.example.marija.pmsunews.fragments.MapFragment;
import com.example.marija.pmsunews.fragments.ReadPostFragment;
import com.example.marija.pmsunews.model.NavItem;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.PostService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.tools.FragmentTransition;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class PostsActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout mDrawerPane;
    private CharSequence mTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    private List<Post> posts;

    private PostListAdapter postListAdapter;
    private ListView listView;

    private PostService postService;

    private Post post;

    public static ArrayList<Tag> tags = new ArrayList<>();

    private boolean sortPostByDate;
    private boolean sortPostByLikes;
    private boolean sortPostByDislikes;


    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        prepareMenu(mNavItems);

        mTitle = getTitle();

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

        TextView textViewUser = findViewById(R.id.user);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPreferances,Context.MODE_PRIVATE);
        if(sharedPreferences.contains(LoginActivity.Username)){
            textViewUser.setText(sharedPreferences.getString(LoginActivity.Name,""));
        }

        listView = findViewById(R.id.post_list);
        postService = ServiceUtils.postService;

        Call call = postService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
               posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
                consultPreferences();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
               t.printStackTrace();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                post = posts.get(i);

                Call<Post> call = postService.getPost(post.getId());

                call.enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        post = response.body();
                        Intent intent = new Intent(PostsActivity.this,ReadPostActivity.class);
                        intent.putExtra("postId",post.getId());
                        intent.putExtra("user",post.getAuthor().getUsername());

                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {

                    }
                });

            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


    private void consultPreferences(){
        sortPostByDate = sharedPreferences.getBoolean(getString(R.string.pref_sort_post_by_date_key),false);
        sortPostByLikes = sharedPreferences.getBoolean(getString(R.string.pref_sort_post_by_like_key),false);
        sortPostByDislikes = sharedPreferences.getBoolean(getString(R.string.pref_sort_post_by_dislike_key),false);

        if(sortPostByDate == true) {
            sortDate();
        }

        if(sortPostByLikes == true){
            sortByLikes();
        }

        if(sortPostByDislikes == true){
            setSortPostByDislikes();
        }
    }


    public void sortDate(){
        Call<List<Post>> call = postService.sortByDate();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
        postListAdapter.notifyDataSetChanged();
    }

    public void sortByLikes(){

       Call<List<Post>> call = postService.sortByLikes();
       call.enqueue(new Callback<List<Post>>() {
           @Override
           public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
               posts = response.body();
               postListAdapter = new PostListAdapter(getApplicationContext(),posts);
               listView.setAdapter(postListAdapter);
           }

           @Override
           public void onFailure(Call<List<Post>> call, Throwable t) {

           }
       });
        postListAdapter.notifyDataSetChanged();
    }

    public void setSortPostByDislikes(){
        Call<List<Post>> call = postService.sortByDislikes();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private void prepareMenu(ArrayList<NavItem> mNavItems ){
        mNavItems.add(new NavItem(getString(R.string.home),getString(R.string.all_post),R.drawable.ic_action_home));
        mNavItems.add(new NavItem(getString(R.string.create_post),getString(R.string.create_post_long),R.drawable.ic_action_add));
        mNavItems.add(new NavItem(getString(R.string.map),getString(R.string.map_long),R.drawable.ic_map));
        mNavItems.add(new NavItem(getString(R.string.preferances), getString(R.string.preferance_long), R.drawable.ic_action_settings));
        mNavItems.add(new NavItem(getString(R.string.logout),getString(R.string.logout_long),R.drawable.ic_logout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_post, menu);
        MenuItem search_item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) search_item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchByAuthor(s);
                searchByTag(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getPosts();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
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
        if(position == 0) {
            Intent postIntent = new Intent(this,PostsActivity.class);
            startActivity(postIntent);
        }else if(position == 1){
            Intent createIntent = new Intent(this, CreatePostActivity.class);
            startActivity(createIntent);
        }else if(position == 2){
            FragmentTransition.to(MapFragment.newInstance(),this,false);
        }else if(position == 3){
            Intent preferanceIntent = new Intent(this,SettingsActivity.class);
            startActivity(preferanceIntent);
        }else if(position == 4) {
            SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.MyPreferances, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.commit();
            Intent logoutIntent = new Intent(this, LoginActivity.class);
            startActivity(logoutIntent);
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

    public void searchByAuthor(String s){
        Call<List<Post>> call = postService.searchByAuthor(s);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    public void searchByTag(String s){
        Call<List<Post>> call = postService.searchByTag(s);
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

            }
        });
    }

    public void getPosts(){
        Call call = postService.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                postListAdapter = new PostListAdapter(getApplicationContext(),posts);
                listView.setAdapter(postListAdapter);
                consultPreferences();
            }
            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
