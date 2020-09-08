package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    List<String> professions = new ArrayList<>();
    List<String> cities = new ArrayList<>();
    List<User> users = new ArrayList<>();
    String interested_profession,interested_city;
    AutoCompleteTextView searching_city,searching_profession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        professions = DatabaseHandler.getAllProfessions();
        searching_profession = (AutoCompleteTextView) findViewById(R.id.searching_profession);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,professions);
        searching_profession.setAdapter(adapter);

        cities = DatabaseHandler.getAllCities();
        searching_city = (AutoCompleteTextView) findViewById(R.id.searching_city);
        adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,cities);
        searching_city.setAdapter(adapter);


        searching_profession.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                interested_profession = adapterView.getItemAtPosition(i).toString();

            }
        });


        searching_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                interested_city = adapterView.getItemAtPosition(i).toString();


            }
        });

    }
    public void getResults(View v){

        Intent intent = new Intent(SearchActivity.this,ResultActivity.class);
        intent.putExtra("interested_city",interested_city);
        intent.putExtra("interested_profession",interested_profession);

        startActivity(intent);
    }

}
