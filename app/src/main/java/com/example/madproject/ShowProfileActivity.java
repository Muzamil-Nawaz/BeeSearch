package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ShowProfileActivity extends AppCompatActivity {
    TextInputLayout showUsername,showPassword,showEmail,showPhone,showExperience,showDescription;
    AutoCompleteTextView showProfession,showCity;
    String name,pass,email,phone,experience,profession,descripton;
    ImageView profile_pic;
    DatabaseReference reference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile);

        // Get Input fields objects to set initial data
        
        showUsername = findViewById(R.id.update_name);
        showEmail = findViewById(R.id.update_email);
        showPhone = findViewById(R.id.update_phone);
        showPassword = findViewById(R.id.update_password);

        showExperience = findViewById(R.id.update_experience);
        showProfession = (AutoCompleteTextView) findViewById(R.id.profile_profession);
       // showProfession.getAdapter()//setSelection(3);
        showDescription = findViewById(R.id.update_descripton);
        showCity = findViewById(R.id.profile_city);

        profile_pic = (ImageView)findViewById(R.id.profile_pic);


        reference = DatabaseHandler.getUserReference();

        List<String> professions = DatabaseHandler.getAllProfessions();
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,professions);
        showProfession.setAdapter(adapter);
        
        // For showing initial data in fields
        
        showUserData();

    }

    private void showUserData() {

        // Retreiving data fetched from Firebase Realtime database
        Intent intent = getIntent();
        name  = intent.getStringExtra("name");
        pass  = intent.getStringExtra("password");
        email  = intent.getStringExtra("email");
        phone  = intent.getStringExtra("phone");
        experience  = intent.getStringExtra("experience");
        profession  = intent.getStringExtra("profession");
        descripton  = intent.getStringExtra("description");

        showProfession.setListSelection(3);
        showCity.setListSelection(2);

        StorageReference reference = FirebaseStorage.getInstance().getReference().child("images/"+name.trim());

       // GlideApp.with(getApplicationContext()).load(reference).into(profile_pic);


        // Display data into fields

        showUsername.getEditText().setText(name);
        showPassword.getEditText().setText(pass);
        showEmail.getEditText().setText(email);
        showPhone.getEditText().setText(phone);
        showExperience.getEditText().setText(experience);

        showDescription.getEditText().setText(descripton);
        showUsername.getEditText().setText(name);
        System.out.println(profession);
        //showProfession.setSelection(Integer.parseInt(profession));

        Glide.with(getApplicationContext()).load(FirebaseStorage.getInstance().getReferenceFromUrl("gs://madproject-ee5db.appspot.com/images/"+name)).into(profile_pic);


    }
    public  void update(View v){
        if(isNameChanged() || isPasswordChanged() || isEmailChanged() || isPhoneChanged() || isDescriptionChanged() || isProfessionChanged() || isExperienceChanged()){
            Toast.makeText(getApplicationContext(),"Data updated successfully.",Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(getApplicationContext(),"Data is not changed.",Toast.LENGTH_SHORT).show();

        }

    }

    private  boolean isExperienceChanged() {
        if(!experience.equals(showExperience.getEditText().getText().toString())){
            reference.child(name.toString()).child("experience").setValue(showExperience.getEditText().getText().toString());
            experience = showExperience.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isProfessionChanged() {
        if(!profession.equals(showProfession.getText().toString())){
            reference.child(name.toString()).child("profession").setValue(showProfession.getText().toString());
            profession = showProfession.getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isDescriptionChanged() {
        if(!descripton.equals(showDescription.getEditText().getText().toString())){
            reference.child(name.toString()).child("description").setValue(showDescription.getEditText().getText().toString());
            descripton = showDescription.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isPhoneChanged() {
        if(!phone.equals(showPhone.getEditText().getText().toString())){
            reference.child(name.toString()).child("phone").setValue(showPhone.getEditText().getText().toString());
            phone = showPhone.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isEmailChanged() {
        if(!email.equals(showEmail.getEditText().getText().toString())){
            reference.child(name.toString()).child("email").setValue(showEmail.getEditText().getText().toString());
            email = showEmail.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isPasswordChanged() {
        if(!pass.equals(showPassword.getEditText().getText().toString())){
            reference.child(name.toString()).child("password").setValue(showPassword.getEditText().getText().toString());
            pass = showPassword.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }

    private  boolean isNameChanged() {

        if(!name.equals(showUsername.getEditText().getText().toString())){
            reference.child(showUsername.getEditText().getText().toString()).child("name").setValue(showUsername.getEditText().getText().toString());
            name = showUsername.getEditText().getText().toString();
            return true;
        }
        else{
            return false;
        }
    }
    public  void toSearch(View v){
        startActivity(new Intent(ShowProfileActivity.this,SearchActivity.class));
    }
}
