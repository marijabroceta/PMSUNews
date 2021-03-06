package com.example.marija.pmsunews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.adapters.DrawerListAdapter;
import com.example.marija.pmsunews.adapters.ViewPagerAdapter;
import com.example.marija.pmsunews.fragments.CommentsFragment;
import com.example.marija.pmsunews.fragments.EditPostFragment;
import com.example.marija.pmsunews.fragments.MapFragment;
import com.example.marija.pmsunews.fragments.ReadPostFragment;
import com.example.marija.pmsunews.model.NavItem;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.PostService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.UserService;
import com.example.marija.pmsunews.tools.FragmentTransition;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadPostActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private CharSequence mTitle;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();
    //private Post post = new Post();
    private User user = new User();

    private Integer postId;

    private TextView viewProfile;
    private String userNamePref;
    private String rolePref;
    private SharedPreferences sharedPreferences;

    private PostService postService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        sharedPreferences = getSharedPreferences(LoginActivity.MyPreferances,Context.MODE_PRIVATE);
        userNamePref = sharedPreferences.getString(LoginActivity.Username,"");
        rolePref = sharedPreferences.getString(LoginActivity.Role,"");

        prepareMenu(mNavItems);

        mTitle = getTitle();

        tabLayout = findViewById(R.id.tablayout);
        appBarLayout = findViewById(R.id.app_bar);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.AddFragment(new ReadPostFragment(),"Read post");
        pagerAdapter.AddFragment(new CommentsFragment(),"Comments");

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.nav_list);

        mDrawerPane = findViewById(R.id.drawer_pane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);




        Toolbar toolbar = findViewById(R.id.read_post_toolbar);
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


        viewProfile = findViewById(R.id.view_profile);

        TextView textViewUser = findViewById(R.id.user);

        if(sharedPreferences.contains(LoginActivity.Username)){
            textViewUser.setText(userNamePref);
            viewProfile.setText("View profile");
            viewProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Call<User> call = userService.getUserByUsername(userNamePref);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            user = response.body();
                            Intent intent = new Intent(ReadPostActivity.this,SingleUserActivity.class);
                            intent.putExtra("userId",user.getId());
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });
                }
            });
        }else{
            textViewUser.setText("Not logged in");
        }

        postService = ServiceUtils.postService;
        userService = ServiceUtils.userService;



        if(!userNamePref.equals("")){
            Call<User> call = userService.getUserByUsername(userNamePref);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    user = response.body();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }



        postId = getIntent().getIntExtra("postId",0);

        invalidateOptionsMenu();
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void prepareMenu(ArrayList<NavItem> mNavItems ){
        mNavItems.add(new NavItem(getString(R.string.home), getString(R.string.all_post), R.drawable.ic_action_home));
        if(!userNamePref.equals("") || !rolePref.equals("USER")){
            mNavItems.add(new NavItem(getString(R.string.create_post),getString(R.string.create_post_long),R.drawable.ic_action_add));
        }

        mNavItems.add(new NavItem(getString(R.string.preferances), getString(R.string.preferance_long), R.drawable.ic_action_settings));
        if(!userNamePref.equals("")){
            mNavItems.add(new NavItem(getString(R.string.logout),getString(R.string.logout_long),R.drawable.ic_logout));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_itemdetail, menu);
        String userName = getIntent().getStringExtra("user");
        if(!userNamePref.equals(userName) || userNamePref.equals("")) {
            MenuItem item_delete = menu.findItem(R.id.action_delete);
            MenuItem item_edit = menu.findItem(R.id.action_edit);
            item_delete.setVisible(false);
            item_edit.setVisible(false);
        }
        if(!userNamePref.equals("")){
            MenuItem login_item = menu.findItem(R.id.action_login);
            MenuItem register_tem = menu.findItem(R.id.action_register);
            login_item.setVisible(false);
            register_tem.setVisible(false);
        }
        if(rolePref.equals("ADMIN")){
            MenuItem item_delete = menu.findItem(R.id.action_delete);
            MenuItem item_edit = menu.findItem(R.id.action_edit);
            item_delete.setVisible(true);
            item_edit.setVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_edit:
                FragmentTransition.to(EditPostFragment.newInstance(),this,false);
                return true;
            case R.id.action_delete:
                delete();
                Toast.makeText(getApplicationContext(),"Post is deleted!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,PostsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_login:
                Intent intent_login = new Intent(this,LoginActivity.class);
                startActivity(intent_login);
                return true;
            case R.id.action_register:
                Intent intent1 = new Intent(this,RegisterActivity.class);
                startActivity(intent1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void delete(){
        Call<Post> call = postService.deletePost(postId);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
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
        }else if(position == 3){
            sharedPreferences.edit().clear().commit();
            Intent logoutIntent = new Intent(this,LoginActivity.class);
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




}
