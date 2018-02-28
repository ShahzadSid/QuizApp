package com.shahzadthedeveloper.quizapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.shahzadthedeveloper.quizapp.R;

public class SplashActivity extends Activity {

    //Splash Screen Timer for 5 Seconds
    private static int SPLASH_SCREEN_TIMER = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Start the main activity here after five seconds
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN_TIMER);
    }
}
