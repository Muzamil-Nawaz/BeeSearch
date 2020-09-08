package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {
    List<User> users = new ArrayList<>();
    static  List<String> professions= new ArrayList<>();
    static List<String> cities = new ArrayList<>();
    Uri imgPath=null;
    TextInputLayout name,email,password,phone,experience,description,profession;
    int selectedProfession;
    AutoCompleteTextView sp,working_cities;
    ImageView profilePic;
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        profilePic = (ImageView) findViewById(R.id.my_avatar);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        // Fetching available cities and professions from the database
        professions = DatabaseHandler.getAllProfessions();
        cities = DatabaseHandler.getAllCities();
        sp = (AutoCompleteTextView) findViewById(R.id.reg_profession2);
        working_cities = (AutoCompleteTextView)findViewById(R.id.reg_cities);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,professions);
        sp.setAdapter(adapter);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cities);
        working_cities.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i)+"",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registerUser(View v){
        if(!validateData()){
            return;
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean validateData() {
        name = (TextInputLayout)findViewById(R.id.reg_name);
        email = (TextInputLayout)findViewById(R.id.reg_email);
        password = (TextInputLayout)findViewById(R.id.reg_pass);
        phone = (TextInputLayout)findViewById(R.id.reg_phone);
        experience =(TextInputLayout)findViewById(R.id.reg_experience);
        description = (TextInputLayout)findViewById(R.id.reg_description);
        profession = (TextInputLayout) findViewById(R.id.reg_profession);

        if(imgPath==null){
            Toast.makeText(getApplicationContext(),"Please select an image for profile and try again",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.getEditText().getText().toString().isEmpty()){
            name.getEditText().setError("Field cannot be empty");
            name.requestFocus();
            return false;
        }
        if(email.getEditText().getText().toString().isEmpty()){
            email.getEditText().setError("Field cannot be empty");
            email.requestFocus();
            return false;
        }if(password.getEditText().getText().toString().isEmpty()){
            password.getEditText().setError("Field cannot be empty");
            password.requestFocus();
            return false;
        }if(experience.getEditText().getText().toString().isEmpty()){
            experience.getEditText().setError("Field cannot be empty");
            experience.requestFocus();
            return false;
        }if(phone.getEditText().getText().toString().isEmpty()){
            phone.getEditText().setError("Field cannot be empty");
            phone.requestFocus();
            return false;
        }if(description.getEditText().getText().toString().isEmpty()){
            description.getEditText().setError("Field cannot be empty");
            description.requestFocus();
            return false;
        }
        String user_name = name.getEditText().getText().toString().trim();
        String user_email = email.getEditText().getText().toString().trim();
        String user_pass = password.getEditText().getText().toString().trim();
        String user_phone = phone.getEditText().getText().toString().trim();
        String user_description = description.getEditText().getText().toString().trim();
        String user_profession = selectedProfession+"";
        String user_experience = experience.getEditText().getText().toString().trim();

        DatabaseReference reference = DatabaseHandler.getUserReference();
        uploadImage();
        User user = new User(user_name,user_profession,user_description
                ,user_email,user_pass,"city",user_experience,user_phone);
        reference.child(user.getName()).setValue(user);
        Toast.makeText(getApplicationContext(),"User Registered successfully.",Toast.LENGTH_LONG).show();
        startActivity(new Intent(SignUpActivity.this,SignInActivity.class));
        return true;
    }
    private void uploadImage() {
        StorageReference ref = storageReference.child("images/" + name.getEditText().getText().toString().trim());

        // adding listeners on upload
        // or failure of image
        ref.putFile(imgPath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(),"Image uploaded.",Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void uploadImage(View v){
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
            builder.show();

    }

    // for uploading image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (ContextCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            if (resultCode != RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "I am in", Toast.LENGTH_LONG).show();
                switch (requestCode) {
                    case 0:
                        if (resultCode == RESULT_OK && data != null) {
                            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");

                            profilePic.setImageBitmap(selectedImage);

                        }

                        break;
                    case 1:
                        if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();
                            // String picturePath contains the path of selected Image
                            profilePic.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                            imgPath = selectedImage;
                        }
                }

            }
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }


        super.onActivityResult(requestCode, resultCode, data);
    }


}

