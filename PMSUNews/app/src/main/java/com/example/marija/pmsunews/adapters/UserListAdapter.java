package com.example.marija.pmsunews.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.User;

import java.util.List;

public class UserListAdapter extends ArrayAdapter<User> {


    public UserListAdapter(@NonNull Context context, List<User> users) {
        super(context,0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_list_item,parent,false);
        }

        TextView name = convertView.findViewById(R.id.user_name_text);
        TextView username = convertView.findViewById(R.id.user_username_text);
        ImageView user_image = convertView.findViewById(R.id.user_image);

        name.setText(user.getName());
        username.setText(user.getUsername());
        user_image.setImageBitmap(user.getPhoto());

        return convertView;
    }
}
