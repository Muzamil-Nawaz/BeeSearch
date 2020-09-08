package com.example.madproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<User> users = DatabaseHandler.getSearchResults(getIntent().getStringExtra("interested_city"),getIntent().getStringExtra("interested_profession"));
        System.out.println("I have reached result mode...");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this,users);
        recyclerView.setAdapter(adapter);

    }
}
