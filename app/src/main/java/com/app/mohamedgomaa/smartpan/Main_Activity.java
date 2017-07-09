package com.app.mohamedgomaa.smartpan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Main_Activity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        tabLayout = (TabLayout) findViewById(R.id.tabLayer);
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        FragmentPagerAdpter fragmentPagerAdpter = new FragmentPagerAdpter(getSupportFragmentManager());
        fragmentPagerAdpter.addFragment(new Map_Fragment(), "Map");
        fragmentPagerAdpter.addFragment(new Search_Fragment(), "Search");
        fragmentPagerAdpter.addFragment(new Contact_Fragment(), "Contact");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(fragmentPagerAdpter);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alerm = new AlertDialog.Builder(this);
        alerm.setTitle("Exit");
        alerm.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        alerm.setMessage("sure,you want Exit app ?");
        alerm.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alerm.show();
    }
}
