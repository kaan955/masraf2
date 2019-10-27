package com.example.kaanb.masrafmain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class intro extends AppCompatActivity {
    private static int SPLASH_TIME = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.intro);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(intro.this,masrafmain.class);
                startActivity(homeintent);
                finish();
            }
        },SPLASH_TIME);


    }
}
