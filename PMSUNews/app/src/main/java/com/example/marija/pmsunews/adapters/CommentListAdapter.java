package com.example.marija.pmsunews.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marija.pmsunews.LoginActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Comment;
import com.example.marija.pmsunews.service.CommentService;
import com.example.marija.pmsunews.service.ServiceUtils;


import java.text.SimpleDateFormat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentListAdapter extends ArrayAdapter<Comment> {

    private CommentService commentService;
    private Comment comment;

    private ImageButton like_btn;
    private ImageButton dislike_btn;
    private TextView like_text;
    private TextView dislike_text;

    private boolean clickedLike;
    private boolean clickedDislike;

    private int counterLikes;
    private int counterDislikes;

    private SharedPreferences sharedPreferences;

    String userNamePref;

    public CommentListAdapter(Context context, List<Comment> comments){super(context,0,comments);}

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup){
        comment = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.comment_list_item,viewGroup,false);
        }

        TextView title_view = view.findViewById(R.id.title_comment_view);
        TextView author_view = view.findViewById(R.id.author_comment_view);
        TextView date_view = view.findViewById(R.id.date_comment_view);
        String formatedDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(comment.getDate());
        TextView comment_view = view.findViewById(R.id.comment_view);
        like_text = view.findViewById(R.id.like_comment_text);
        dislike_text = view.findViewById(R.id.dislike_comment_text);
        ImageButton deleteBtn = view.findViewById(R.id.delete_btn);
        like_btn = view.findViewById(R.id.like_comment_image);
        dislike_btn = view.findViewById(R.id.dislike_comment_image);


        title_view.setText(comment.getTitle());
        author_view.setText(comment.getAuthor().getName());
        date_view.setText(formatedDate);
        comment_view.setText(comment.getDescription());
        like_text.setText(String.valueOf(comment.getLikes()));
        dislike_text.setText(String.valueOf(comment.getDislikes()));


        sharedPreferences  = getContext().getSharedPreferences(LoginActivity.MyPreferances, Context.MODE_PRIVATE);
        userNamePref = sharedPreferences.getString(LoginActivity.Username,"");

        if(!userNamePref.equals(comment.getAuthor().getUsername())){
            deleteBtn.setVisibility(View.INVISIBLE);
        }

        commentService = ServiceUtils.commentService;

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete(comment.getId());
                Toast.makeText(getContext(),"Comment is deleted",Toast.LENGTH_SHORT).show();

            }
        });

        clickedLike = false;
        like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNamePref.equals(comment.getAuthor().getUsername())){
                    Toast.makeText(getContext(),"You can't like your comment",Toast.LENGTH_SHORT).show();
                }else{
                    if(clickedLike == false){
                        addLike();
                        like_text.setText(String.valueOf(comment.getLikes()));
                        clickedLike = true;
                        dislike_btn.setEnabled(false);
                    }else{
                        removeLike();
                        like_text.setText(String.valueOf(comment.getLikes()));
                        clickedLike = false;
                        dislike_btn.setEnabled(true);
                    }
                }
            }
        });

        clickedDislike = false;
        dislike_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userNamePref.equals(comment.getAuthor().getUsername())){
                    Toast.makeText(getContext(),"You can't dislike your comment",Toast.LENGTH_SHORT).show();
                }else{
                    if(clickedDislike == false){
                        addDislike();
                        dislike_text.setText(String.valueOf(comment.getDislikes()));
                        clickedDislike = true;
                        like_btn.setEnabled(false);
                    }else{

                        removeDislike();
                        dislike_text.setText(String.valueOf(comment.getDislikes()));
                        clickedDislike = false;
                        like_btn.setEnabled(false);
                    }
                }
            }
        });

        return view;
    }

    public void delete(int id){
        Call<Comment> call = commentService.deleteComment(id);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {

            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });

        this.notifyDataSetChanged();
    }

    public void addLike(){
        counterLikes = comment.getLikes();
        System.out.println("*************");
        System.out.println(counterLikes);
        comment.setLikes(counterLikes+1);
        System.out.println("---------------");
        System.out.println(counterLikes);

        System.out.println("============");
        System.out.println(comment.getId());


        Call<Comment> call = commentService.addLikeDislike(comment,comment.getId());
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                like_btn.setImageResource(R.drawable.ic_like_green);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }


    public void addDislike(){
        counterDislikes = comment.getDislikes();
        comment.setDislikes(counterDislikes+1);

        Call<Comment> call = commentService.addLikeDislike(comment,comment.getId());
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                dislike_btn.setImageResource(R.drawable.ic_dislike_red);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    public void removeLike(){
        counterLikes = comment.getLikes();
        comment.setLikes(counterLikes-1);

        Call<Comment> call = commentService.addLikeDislike(comment,comment.getId());
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                like_btn.setImageResource(R.drawable.ic_action_like_white);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    public void removeDislike(){
        counterDislikes = comment.getDislikes();
        comment.setDislikes(counterDislikes-1);

        Call<Comment> call = commentService.addLikeDislike(comment,comment.getId());
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                dislike_btn.setImageResource(R.drawable.ic_action_dislike_white);
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }
}
