package com.example.marija.pmsunews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReadPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_post);

        TextView title_view = findViewById(R.id.title_view);
        title_view.setText("Neki naslov");

        TextView author_view = findViewById(R.id.author_view);
        author_view.setText("Marija");

        TextView date_view = findViewById(R.id.date_view);
        date_view.setText("23.2.2018");

        TextView description_view = findViewById(R.id.description_view);
        description_view.setText("Neki post");

        TextView like_text = findViewById(R.id.like_text);
        like_text.setText("25");

        TextView dislike_text = findViewById(R.id.dislike_text);
        dislike_text.setText("6");

        TextView tags_view = findViewById(R.id.tags_view);
        tags_view.setText("#tag1#tag2");

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
        dislike_comment_text.setText("4");
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
}
