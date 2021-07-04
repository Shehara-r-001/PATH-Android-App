package com.example.path_02;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    private static int SPLASH_DURATION = 2000;

    Animation top_animation, bott_animation;
    ImageView image;
    TextView text;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);


        top_animation = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        //bott_animation = AnimationUtils.loadAnimation(this, R.anim.bott_anim);

        image = findViewById(R.id.logo_for_splash);
        //text = findViewById(R.id.text_welcome);

        image.setAnimation(top_animation);
        //text.setAnimation(bott_animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_DURATION);

    }
}


