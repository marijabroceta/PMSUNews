package com.example.marija.pmsunews;

import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;


public class SettingsActivity extends PreferenceActivity {



    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawableResource(R.color.white);
        addPreferencesFromResource(R.xml.preference);

        CheckBoxPreference cbSortByDate = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_post_by_date_key));
        cbSortByDate.setOnPreferenceClickListener(cbListenerPostByDate);

        CheckBoxPreference cbSortByLikes = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_post_by_like_key));
        cbSortByLikes.setOnPreferenceClickListener(cbListenerPostByLikes);

        CheckBoxPreference cbSortByDislikes = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_post_by_dislike_key));
        cbSortByDislikes.setOnPreferenceClickListener(cbListenerPostByDislikes);

        CheckBoxPreference cbSortCommentByDate = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_comment_by_date_key));
        cbSortCommentByDate.setOnPreferenceClickListener(cbListenerCommentByDate);

        CheckBoxPreference cbSortCommentByLikes = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_comment_by_like_key));
        cbSortCommentByLikes.setOnPreferenceClickListener(cbListenerCommentByLikes);

        CheckBoxPreference cbSortCommentByDislikes = (CheckBoxPreference) findPreference(getString(R.string.pref_sort_comment_by_dislike_key));
        cbSortCommentByDislikes.setOnPreferenceClickListener(cbListenerCommentByDislikes);

        if (cbSortByDate.isChecked() & cbSortByDate.isEnabled()) {
            setStatus(findPreference(getString(R.string.pref_sort_post_by_like_key)), false);
            setStatus(findPreference(getString(R.string.pref_sort_post_by_dislike_key)), false);
        }

        if(cbSortByLikes.isChecked() & cbSortByLikes.isEnabled()){
            setStatus(findPreference(getString(R.string.pref_sort_post_by_date_key)),false);
            setStatus(findPreference(getString(R.string.pref_sort_post_by_dislike_key)), false);
        }

        if(cbSortByDislikes.isChecked() & cbSortByDislikes.isEnabled()){
            setStatus(findPreference(getString(R.string.pref_sort_post_by_date_key)),false);
            setStatus(findPreference(getString(R.string.pref_sort_post_by_like_key)), false);
        }


        if(cbSortCommentByDate.isChecked() & cbSortCommentByDate.isEnabled()){
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_like_key)),false);
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_dislike_key)),false);
        }

        if(cbSortCommentByLikes.isChecked() & cbSortCommentByLikes.isEnabled()){
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_date_key)),false);
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_dislike_key)),false);
        }

        if(cbSortCommentByDislikes.isChecked() & cbSortCommentByDislikes.isEnabled()){
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_date_key)),false);
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_like_key)),false);
        }

    }

    private void setStatus(Preference pref, boolean stats) {
        pref.setEnabled(stats);
    }

    private Preference.OnPreferenceClickListener cbListenerPostByDate = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_post_by_dislike_key)), !checkBoxPreference.isChecked());
            setStatus( findPreference(getString(R.string.pref_sort_post_by_like_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };

    private Preference.OnPreferenceClickListener cbListenerPostByLikes = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_post_by_date_key)), !checkBoxPreference.isChecked());
            setStatus(findPreference(getString(R.string.pref_sort_post_by_dislike_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };

    private Preference.OnPreferenceClickListener cbListenerPostByDislikes = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_post_by_date_key)), !checkBoxPreference.isChecked());
            setStatus(findPreference(getString(R.string.pref_sort_post_by_like_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };

    private Preference.OnPreferenceClickListener cbListenerCommentByDate = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_like_key)),!checkBoxPreference.isChecked());
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_dislike_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };

    private Preference.OnPreferenceClickListener cbListenerCommentByLikes= new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_date_key)), !checkBoxPreference.isChecked());
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_dislike_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };

    private Preference.OnPreferenceClickListener cbListenerCommentByDislikes= new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_date_key)), !checkBoxPreference.isChecked());
            setStatus(findPreference(getString(R.string.pref_sort_comment_by_like_key)), !checkBoxPreference.isChecked());
            return true;
        }
    };


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
