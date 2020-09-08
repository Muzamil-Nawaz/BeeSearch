package com.example.madproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    Animation topAnim,bottomAnim;

    ImageView welcome_image;
    TextView welcome_Text,welcome_Slogan,developer_Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Splash screen animation

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        welcome_image = (ImageView) findViewById(R.id.welcome_image);
        welcome_Text = (TextView)  findViewById(R.id.welcome_text);
        welcome_Slogan = (TextView) findViewById(R.id.welcome_slogan);
        developer_Name = (TextView) findViewById(R.id.developer_name);

        welcome_image.setAnimation(topAnim);
        welcome_Text.setAnimation(topAnim);
        welcome_Slogan.setAnimation(bottomAnim);
        developer_Name.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                Pair [] pairs = new Pair[2];
                pairs[0] = new Pair<View,String>(welcome_image,"logo_image");
                pairs[1] = new Pair<View,String>(welcome_Slogan,"logo_text");

                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    options = ActivityOptions.makeSceneTransitionAnimation(WelcomeActivity.this,pairs);
                }
                startActivity(intent,options.toBundle());

                finish();
            }
        },5000);
    }
}
