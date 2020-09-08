package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    TextInputLayout layout;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
        layout = findViewById(R.id.username);
        username = layout.getEditText();
        layout = findViewById(R.id.password);
        password = layout.getEditText();

    }
    public  void toSignUp(View v){
        startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
    }
    public void toSearch(View v){
        //loginUser();
       // startActivity(new Intent(SignInActivity.this,SearchActivity.class));
    }
    public void loginUser(View v){
        if(!validateUserName() && !validatePassword()){
            return;
        }
        else{
            isUserRegistered();

        }


    }

    private void isUserRegistered() {

        final String user = username.getText().toString().trim();
        final String pass = password.getText().toString().trim();

        DatabaseReference reference = DatabaseHandler.getUserReference();
        Query checkUser = reference.orderByChild("name").equalTo(user);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String passwordFromDB = snapshot.child(user).child("password").getValue().toString();


                    if(passwordFromDB.equals(pass)){
                        String emailFromDB = snapshot.child(user).child("email").getValue().toString();
                        String phoneFromDB = snapshot.child(user).child("phone").getValue().toString();
                        String experienceFromDB = snapshot.child(user).child("experience").getValue().toString();
                        String professionFromDB = snapshot.child(user).child("profession").getValue().toString();
                        String descriptionFromDB = snapshot.child(user).child("description").getValue().toString();

                        Intent intent = new Intent(SignInActivity.this,ShowProfileActivity.class);

                        intent.putExtra("name",user);
                        intent.putExtra("password",passwordFromDB);
                        intent.putExtra("email",emailFromDB);
                        intent.putExtra("experience",experienceFromDB);
                        intent.putExtra("phone",phoneFromDB);
                        intent.putExtra("profession",professionFromDB.trim());
                        intent.putExtra("description",descriptionFromDB);

                        startActivity(intent);
                    }
                    else{
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }
                }
                else{
                    username.setError("No such user exists");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean validatePassword() {
        String pass = password.getText().toString().trim();
        if(pass.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }
        else{
            password.setError(null);
//            password.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUserName() {
        String name = username.getText().toString().trim();
        if(name.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            return true;

        }
    }
}
