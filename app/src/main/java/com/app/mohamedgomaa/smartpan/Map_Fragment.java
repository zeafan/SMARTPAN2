package com.app.mohamedgomaa.smartpan;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map_Fragment extends Fragment implements OnMapReadyCallback {
    LatLng my_latLng,my_latLng1,my_latLng2,my_latLng3;
    private GoogleMap mMap;
    public static Criteria criteria;
    float[] results1 = new float[1];
    float[] results2 = new float[1];
    float[] results3 = new float[1];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        getLocation();
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.addMarker(new MarkerOptions().position(my_latLng1).title("position1").icon(BitmapDescriptorFactory.fromResource(R.drawable.position1)).snippet("distance:"+String.valueOf(results1[0])+" m")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(my_latLng2).title("position2").icon(BitmapDescriptorFactory.fromResource(R.drawable.position2)).snippet("distance:"+String.valueOf(results2[0])+" m")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(my_latLng3).title("position3").icon(BitmapDescriptorFactory.fromResource(R.drawable.position3)).snippet("distance:"+String.valueOf(results3[0])+" m")).showInfoWindow();
        mMap.addMarker(new MarkerOptions().position(my_latLng).title("my Location")).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(my_latLng, 18));
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" +marker.getPosition().latitude+","+marker.getPosition().latitude));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    void getLocation() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestprovider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestprovider);
        if(location==null)
        {
            location=locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
        }
       double Latitude= location.getLatitude();
        double Longitude= location.getLongitude();
        my_latLng=new LatLng(Latitude,Longitude);
        my_latLng1=new LatLng(Latitude+.0005,Longitude+.0005);
        my_latLng2=new LatLng(Latitude+.0009,Longitude-.001);
        my_latLng3=new LatLng(Latitude-.0007,Longitude+.0008);
        Location.distanceBetween(Latitude,Longitude,my_latLng1.latitude,my_latLng1.longitude,results1);
        Location.distanceBetween(Latitude,Longitude,my_latLng2.latitude,my_latLng2.longitude,results2);
        Location.distanceBetween(Latitude,Longitude,my_latLng3.latitude,my_latLng3.longitude,results3);


    }
}
