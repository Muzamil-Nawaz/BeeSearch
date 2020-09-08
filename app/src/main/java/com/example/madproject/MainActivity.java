package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView logo_image;
    TextView logo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        logo_image = (ImageView)findViewById(R.id.main_image);


    }
    public  void toSignUp(View v){

        startActivity(new Intent(MainActivity.this,SignUpActivity.class));
    }
    public void toSignIn(View v){
        Intent intent = new Intent(MainActivity.this,SignInActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View,String>(logo_image,"logo_image");

        ActivityOptions options = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
        }
        startActivity(intent,options.toBundle());
      //  startActivity(new Intent(MainActivity.this,SignInActivity.class));
    }
}
