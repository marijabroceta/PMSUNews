package com.example.marija.pmsunews.adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.w3c.dom.Text;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class PostListAdapter extends ArrayAdapter<Post> {

    private TextView location_view;


    public PostListAdapter(Context context, List<Post> posts){
        super(context,0,posts);
    }




    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        final Post post = getItem(position);

        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.post_list_item,viewGroup,false);
        }



        final TextView date_view = view.findViewById(R.id.date_view);
        final TextView title_view = view.findViewById(R.id.title_view);
        final ImageView image_view = view.findViewById(R.id.image_view);
        location_view = view.findViewById(R.id.location_view);


        String newDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(post.getDate());


        date_view.setText(newDate);
        title_view.setText(post.getTitle());
        image_view.setImageBitmap(post.getPhoto());

        System.out.println("LATITUDE POST" + post.getLatitude() + " " + "LONGITUDE POST" + post.getLongitude());
        getAddress(post.getLatitude(),post.getLongitude());




        return view;
    }


    public void getAddress(double latitude,double longitude){
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());



        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String city = addresses.get(0).getLocality();
            String country = addresses.get(0).getCountryName();
            location_view.setText(city + "," + country);


            System.out.println(city);
            System.out.println(country);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
