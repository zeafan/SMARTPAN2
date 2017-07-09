package com.app.mohamedgomaa.smartpan;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class GPS extends AppCompatActivity {
    public static Criteria criteria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        TextView t1=(TextView)findViewById(R.id.v1);
        TextView t2=(TextView)findViewById(R.id.v2);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestprovider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestprovider);
        if(location==null)
        {
            location=locationManager.getLastKnownLocation(locationManager.PASSIVE_PROVIDER);
        }
        t1.setText(String.valueOf(location.getLatitude()));
        t2.setText(String.valueOf(location.getLongitude()));
    }
}
