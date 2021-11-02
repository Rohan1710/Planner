package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ToDoList extends AppCompatActivity {

    TextView date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        date = (TextView) findViewById(R.id.date);
        Intent i = getIntent();
        date.setText(i.getStringExtra("Date"));
    }
}