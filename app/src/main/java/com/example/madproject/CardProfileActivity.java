package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;

public class CardProfileActivity extends AppCompatActivity {
    ImageView profilePic;
    TextView name,email,experience,phone,description,city,profession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_profile);

        name = (TextView) findViewById(R.id.card_profile_name);
        email = (TextView) findViewById(R.id.card_profile_email);
        experience = (TextView) findViewById(R.id.card_profile_experience);
        phone = (TextView) findViewById(R.id.card_profile_phone);
        description = (TextView) findViewById(R.id.card_profile_description);
        city = (TextView) findViewById(R.id.card_profile_city);
        profession = (TextView) findViewById(R.id.card_profile_profession);
        profilePic = (ImageView) findViewById(R.id.card_profile_pic);

        loadData();
    }
    public void loadData(){


        Intent intent = getIntent();

        Glide.with(getApplicationContext()).load(FirebaseStorage.getInstance().getReferenceFromUrl(intent.getStringExtra("image"))).into(profilePic);

        name.setText("Muzamil123");
        phone.setText("03222592161");
        email.setText("mzmlnwz3@gmail.com");
        experience.setText("2 ");
        city.setText("Shahdadpur");
        profession.setText("Software Engineer");
        description.setText("I am a Muzamil Nawaz, a software engineering student with 2.5 years of experience in software" +
                "development as a freelancer.");


    }
    public void sendEmail(View v){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{email.getText().toString()});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Request for personal project");
        intent.putExtra(Intent.EXTRA_TEXT,"Hello there, I found you through BeeSearch platform and want to work" +
                "with you after seeing your profile...");
        startActivity(intent);



    }
    public void makeCall(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone.getText().toString()));
        startActivity(intent);
    }
}
