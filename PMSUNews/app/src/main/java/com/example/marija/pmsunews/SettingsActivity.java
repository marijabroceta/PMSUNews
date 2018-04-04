package com.example.marija.pmsunews;

import android.preference.PreferenceActivity;

import android.os.Bundle;


public class SettingsActivity extends PreferenceActivity {



    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);


    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onResume(){
        super.onResume();
    }
}
