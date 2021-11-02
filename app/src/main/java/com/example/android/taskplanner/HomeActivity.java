package com.example.android.taskplanner;

import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity{

    TextView textView,date,todo,over;
    BottomNavigationView bottomNavigation;
    public FloatingActionButton addTask;
    private CalendarView mCalenderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mCalenderView = (CalendarView) findViewById(R.id.calenderView);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        date = (TextView) findViewById(R.id.homePageDate);
        todo = (TextView) findViewById(R.id.ToDoSize);
        over = (TextView) findViewById(R.id.taskOver);
        date.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date()));
        todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskList = new Intent(HomeActivity.this,ToDoList.class);
                taskList.putExtra("Date",date.getText());
                startActivity(taskList);
            }
        });
        over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent taskList = new Intent(HomeActivity.this,ToDoList.class);
                taskList.putExtra("Date",date.getText());
                startActivity(taskList);
            }
        });
        mCalenderView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1+1) + "/" + i;
                Intent taskList = new Intent(HomeActivity.this,ToDoList.class);
                taskList.putExtra("Date",date);
                startActivity(taskList);
            }
        });
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.home:
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_SHORT).show();
                    case R.id.todolist:
                        Toast.makeText(HomeActivity.this, "todo list", Toast.LENGTH_SHORT).show();
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