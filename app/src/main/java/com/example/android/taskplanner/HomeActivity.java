package com.example.android.taskplanner;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    BottomNavigationView bottomNavigation;
    public FloatingActionButton addTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.current_date);

        Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d");
        String dateformate = simpleDateFormat.format(calender.getTime());
        textView.setText(dateformate);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    case R.id.todolist:
                        Intent intent = new Intent(HomeActivity.this,todo_list.class);
                        startActivity(intent);
                    case R.id.notes:
                        Toast.makeText(HomeActivity.this, "Notes", Toast.LENGTH_SHORT).show();
                    case R.id.alarm:
                        Toast.makeText(HomeActivity.this, "alarm", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        addTask = findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,AddTask.class);
                startActivity(intent);
            }
        });

    }
}