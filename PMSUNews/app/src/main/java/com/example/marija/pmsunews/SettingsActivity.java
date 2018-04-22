package com.example.marija.pmsunews;

import android.preference.PreferenceActivity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;


public class SettingsActivity extends PreferenceActivity {



    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
