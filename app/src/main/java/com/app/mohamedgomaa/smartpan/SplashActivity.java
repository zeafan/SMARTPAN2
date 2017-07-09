package com.app.mohamedgomaa.smartpan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splach);
        final ImageView title=(ImageView)findViewById(R.id.splash);
        anim= AnimationUtils.loadAnimation(this,R.anim.translate);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    title.setAnimation(anim);
                    sleep(3000);
                    startActivity(new Intent(getApplicationContext(), Login_Register.class));
                    finish();
                } catch (Exception E) {
                }

            }
        }.start();
    }
}
