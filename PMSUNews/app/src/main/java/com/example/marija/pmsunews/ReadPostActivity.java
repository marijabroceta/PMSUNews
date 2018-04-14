package com.example.marija.pmsunews;

import android.content.Intent;
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
import com.example.marija.pmsunews.fragments.ReadPostFragment;
import com.example.marija.pmsunews.model.NavItem;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private Post post = new Post();
    private User user = new User();
    private Tag tag = new Tag();
    private ArrayList<Tag> tags = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);






/*
        TextView title_view = (TextView) findViewById(R.id.title_view);
        //post.setTitle("Neki naslov");
        title_view.setText("Neki naslov");

/*
        TextView author_view = findViewById(R.id.author_view);
        user.setUsername("Marija");
        post.setAuthor(user);
        author_view.setText(post.getAuthor().getUsername());

        TextView date_view = findViewById(R.id.date_view);
        //noinspection deprecation
        post.setDate(new Date(2018,2,23));
        date_view.setText(new SimpleDateFormat("dd.MM.yyyy").format(post.getDate()));

        TextView description_view = findViewById(R.id.description_view);
        post.setDescription("Neki post");
        description_view.setText(post.getDescription());

        TextView like_text = findViewById(R.id.like_text);
        post.setLikes(25);
        like_text.setText(String.valueOf(post.getLikes()));

        TextView dislike_text = findViewById(R.id.dislike_text);
        post.setDislikes(6);
        dislike_text.setText(String.valueOf(post.getDislikes()));

        TextView tags_view = findViewById(R.id.tags_view);
        tag.setName("#tag1");
        tag.setName("#tag2");
        tags.add(tag);
        post.setTags(tags);
        //tags_view.setText(post.getTags(tags));



        TextView title_comment_view = findViewById(R.id.title_comment_view);
        title_comment_view.setText("Naslov komentara");

        TextView author_comment_view = findViewById(R.id.author_comment_view);
        author_comment_view.setText("John");

        TextView date_comment_view = findViewById(R.id.date_comment_view);
        date_comment_view.setText("25.2.2018");

        TextView comment_view = findViewById(R.id.comment_view);
        comment_view.setText("Komentar 1");

        TextView like_comment_text = findViewById(R.id.like_comment_text);
        like_comment_text.setText("12");

        TextView dislike_comment_text = findViewById(R.id.dislike_comment_text);
        dislike_comment_text.setText("4");*/

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
        mNavItems.add(new NavItem(getString(R.string.preferances), getString(R.string.preferance_long), R.drawable.ic_action_settings));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_itemdetail, menu);
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
                Toast.makeText(this,"Edit post",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                Toast.makeText(this,"Delete post",Toast.LENGTH_SHORT).show();
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




}
