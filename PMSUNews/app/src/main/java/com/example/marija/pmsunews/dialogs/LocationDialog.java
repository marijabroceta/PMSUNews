package com.example.marija.pmsunews.dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class LocationDialog extends AlertDialog.Builder {


    public LocationDialog(Context context) {
        super(context);

        setUpDialog();
    }

    private void setUpDialog(){
        setTitle("Oooops");
        setMessage("Your Locations seems to be disabled, do you want to enable it?");
        setCancelable(false);

        setPositiveButton("Sure", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                dialogInterface.dismiss();
            }
        });

        setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}
