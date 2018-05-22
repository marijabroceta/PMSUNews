package com.example.marija.pmsunews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.adapters.DrawerListAdapter;
import com.example.marija.pmsunews.model.NavItem;
import com.example.marija.pmsunews.model.Post;
import com.example.marija.pmsunews.model.Tag;
import com.example.marija.pmsunews.model.User;
import com.example.marija.pmsunews.service.PostService;
import com.example.marija.pmsunews.service.ServiceUtils;
import com.example.marija.pmsunews.service.TagService;
import com.example.marija.pmsunews.service.UserService;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class CreatePostActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerPane;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private ListView mDrawerList;
    private ArrayList<NavItem> mNavItems = new ArrayList<NavItem>();

    private EditText title_edit;
    private EditText description_edit;
    private static EditText tags_edit;

    private UserService userService;
    private PostService postService;
    private static TagService tagService;

    private SharedPreferences sharedPreferences;

    private List<Tag> tagsList = new ArrayList<>();
    private List<Post> postList = new ArrayList<>();

    private Bitmap bitmap;
    private byte[] byteArray;

    public static Tag tagResponse;
    public static Tag tag;
    public static User user;
    public static Post postResponse;
    public static Post newPost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        title_edit = findViewById(R.id.title_edit);


        description_edit = findViewById(R.id.description_edit);


        tags_edit = findViewById(R.id.tags_edit);


        Button upload_btn = findViewById(R.id.upload_btn);
        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
            }
        });



        //Button create_btn = findViewById(R.id.create_btn);

        prepareMenu(mNavItems);

        mTitle = getTitle();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.nav_list);

        mDrawerPane = findViewById(R.id.drawer_pane);
        DrawerListAdapter adapter = new DrawerListAdapter(this, mNavItems);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.create_post_toolbar);
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
                createPost();
                //setTagsInPost();

                title_edit.setText("");
                description_edit.setText("");
                tags_edit.setText("");
            }
        });

        TextView textViewUser = findViewById(R.id.user);
        sharedPreferences = getSharedPreferences(LoginActivity.MyPreferances,Context.MODE_PRIVATE);
        if(sharedPreferences.contains(LoginActivity.Username)){
            textViewUser.setText(sharedPreferences.getString(LoginActivity.Name,""));
        }

        postService = ServiceUtils.postService;
        userService = ServiceUtils.userService;
        tagService = ServiceUtils.tagService;

        String userNamePref = sharedPreferences.getString(LoginActivity.Username,"");

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

    public void createPost() {
        final Post post = new Post();

        String title = title_edit.getText().toString();
        String description = description_edit.getText().toString();
        post.setTitle(title);
        post.setDescription(description);
        post.setAuthor(user);
        post.setLikes(0);
        post.setDislikes(0);
        //post.setPhoto(bitmap);
        Date date = Calendar.getInstance().getTime();

        post.setDate(date);
        Call<Post> call = postService.createPost(post);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Snackbar.make(findViewById(R.id.coordinator),"Post is created",Snackbar.LENGTH_SHORT).show();
                postResponse =  response.body();
                System.out.println("-------------------");
                System.out.println(postResponse.getId());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });

        addTags();


    }

    public  void addTags(){
        String tagsString = tags_edit.getText().toString().trim();
        String[] separated = tagsString.split("#");

        List<String> tagFilter =Arrays.asList(separated);
        tag = new Tag();
        for(String tagString : tagFilter.subList(1,tagFilter.size())) {
            tag.setName(tagString);
            System.out.println(tag.getName());
            Call<Tag> callTag = tagService.addTag(tag);
            callTag.enqueue(new Callback<Tag>() {
                @Override
                public void onResponse(Call<Tag> call, Response<Tag> response) {
                    System.out.println("****Tag created*****");
                    tagResponse = response.body();
                    System.out.println(postResponse.getId());
                    System.out.println(tagResponse.getId());
                    setTagsInPost(postResponse.getId(),tagResponse.getId());

                }

                @Override
                public void onFailure(Call<Tag> call, Throwable t) {

                }
            });
        }
    }

    public void setTagsInPost(int postId,int tagId){



        Call<Post> call = postService.setTagsInPost(postId,tagId);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                System.out.println("****Added tags*****");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode== 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ImageView uploaded_photo = findViewById(R.id.uploaded_photo);
                uploaded_photo.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
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

    private void prepareMenu(ArrayList<NavItem> mNavItems ){
        mNavItems.add(new NavItem(getString(R.string.home), getString(R.string.all_post), R.drawable.ic_action_home));
        mNavItems.add(new NavItem(getString(R.string.preferances), getString(R.string.preferance_long), R.drawable.ic_action_settings));
        mNavItems.add(new NavItem(getString(R.string.logout),getString(R.string.logout_long),R.drawable.ic_logout));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_create_post, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_create_done:
                Toast.makeText(this,"Created",Toast.LENGTH_SHORT).show();
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
            Intent preferanceIntent = new Intent(this,SettingsActivity.class);
            startActivity(preferanceIntent);
        }else if(position == 2){
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
