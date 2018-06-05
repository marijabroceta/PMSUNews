package com.example.marija.pmsunews;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.service.CommentService;
import com.example.marija.pmsunews.service.ServiceUtils;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCommentActivity extends AppCompatActivity {

    private EditText title_edit;
    private EditText description_edit;
    private FloatingActionButton fab;

    private Integer commentId;

    private CommentService commentService;

    private Comment comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment_activity);


        title_edit = findViewById(R.id.title_edit);
        description_edit = findViewById(R.id.description_edit);
        fab = findViewById(R.id.fab);

        commentService = ServiceUtils.commentService;

        commentId = getIntent().getIntExtra("commentId",0);

        Call<Comment> call = commentService.getCommentById(commentId);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                comment = response.body();
                if(comment != null){
                    title_edit.setText(comment.getTitle());
                    description_edit.setText(comment.getDescription());
                }

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

    }

    public void editComment(){
        String title = title_edit.getText().toString();
        String description = description_edit.getText().toString();
        Date date = Calendar.getInstance().getTime();
        comment.setTitle(title);
        comment.setDescription(description);
        comment.setDate(date);

        Call<Comment> call = commentService.editComment(comment,commentId);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editComment();
                Intent intent = new Intent(EditCommentActivity.this, PostsActivity.class);
                startActivity(intent);
            }
        });
    }
}
