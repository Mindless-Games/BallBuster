package com.example.andrew.ballbuster;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        iv = findViewById(R.id.logo);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.splash);
        iv.startAnimation(anim);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4500);
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
