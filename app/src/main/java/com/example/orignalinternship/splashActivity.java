package com.example.orignalinternship;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class splashActivity extends AppCompatActivity {

    ImageView imageView;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
          getSupportActionBar().hide();
         sp = getSharedPreferences(constantsp.pref,MODE_PRIVATE);
        imageView = findViewById(R.id.splash_image);
        AlphaAnimation animation = new AlphaAnimation(0,1);
        animation.setDuration(3000);
        animation.setRepeatCount(1);
        imageView.startAnimation(animation);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString(constantsp.EMAIL,"").equalsIgnoreCase("")) {
                    new Commonmethod(splashActivity.this, MainActivity.class);
                    finish();
                }
                else {
                    new Commonmethod(splashActivity.this,homeactivity.class);
                }
            }
        },3000);
    }
}