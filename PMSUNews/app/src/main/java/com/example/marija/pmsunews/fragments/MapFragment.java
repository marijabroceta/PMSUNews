package com.example.marija.pmsunews.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.marija.pmsunews.LoginActivity;
import com.example.marija.pmsunews.R;
import com.example.marija.pmsunews.dialogs.LocationDialog;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment implements LocationListener,OnMapReadyCallback{

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private LocationManager locationManager;
    private String provider;
    private SupportMapFragment mMapFragment;
    private AlertDialog dialog;
    private Marker home;
    private GoogleMap map;
    private SharedPreferences sharedPreferences;

    public static MapFragment newInstance() {

        MapFragment mpf = new MapFragment();

        return mpf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        locationManager =  (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    private void createMapFragmentAndInflate() {

        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, true);

        mMapFragment = SupportMapFragment.newInstance();

       FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
       transaction.replace(R.id.map_container, mMapFragment).commit();

        mMapFragment.getMapAsync(this);
    }


    private void showLocatonDialog() {
        if (dialog == null) {
            dialog = new LocationDialog(getActivity()).prepareDialog();
        } else {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

        dialog.show();
    }

    @Override
    public void onResume(){
        super.onResume();

        createMapFragmentAndInflate();

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!gps && !wifi){
            showLocatonDialog();
        }else{
            if(checkLocationPermission()){
                if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    locationManager.requestLocationUpdates(provider,0,0,this);

                }else if(ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                    locationManager.requestLocationUpdates(provider,0,0,this);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle data){
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_map,viewGroup,false);

        return  view;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(map != null){
            addMarker(location);
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public boolean checkLocationPermission(){
        System.out.println("CHECK PERMISSION");
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){

                new AlertDialog.Builder(getActivity())
                        .setTitle("Allow user location")
                        .setMessage("To continue working we need your locations... Allow now?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            }else{
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int [] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_LOCATION:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(provider,0,0,this);
                    }
                }else if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(provider,0,0,this);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        Location location = null;

        if(checkLocationPermission()){
            if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                location = locationManager.getLastKnownLocation(provider);
            }else if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                location = locationManager.getLastKnownLocation(provider);
            }
        }
/*
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.addMarker(new MarkerOptions()
                            .title("YOUR_POSITION")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                    .position(latLng));
                home.setFlat(true);

                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14).build();

                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });*/

        if(location != null){
            addMarker(location);
        }
    }

    private void addMarker(Location location){
        LatLng loc = new LatLng(location.getLatitude(),location.getLongitude());

        System.out.println("LATITUDE: " + location.getLatitude());
        System.out.println("LONGITUDE: " + location.getLongitude());


        if(home != null){
            home.remove();
        }

        home = map.addMarker(new MarkerOptions()
                .title("YOUR POSITION")
                 .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(loc));
        home.setFlat(true);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(loc).zoom(14).build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onPause(){
        super.onPause();
        locationManager.removeUpdates(this);
    }
}
