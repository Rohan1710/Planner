package com.example.android.taskplanner;

import androidx.annotation.NonNull;

import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Model.taskModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView textView;
    BottomNavigationView bottomNavigation;
    public FloatingActionButton addTask;
    public RecyclerView taskRecyclerView;
    private TodoAdapter taskAdapter;

    private List<taskModel> taskList;
    public CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.homePageDate);

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
                        break;
                    case R.id.todolist:
                        Toast.makeText(HomeActivity.this, "todo list", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.notes:
                        Toast.makeText(HomeActivity.this, "Notes", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.alarm:
                        Toast.makeText(HomeActivity.this, "alarm", Toast.LENGTH_SHORT).show();
                        break;
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

        calendarView = findViewById(R.id.calenderView);




        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(manager);
        taskRecyclerView.setHasFixedSize(true);




//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);



        MyDBHandler db = new MyDBHandler(HomeActivity.this);
        List<taskModel> allTask = db.getAllTasks();
        Toast.makeText(this,"Tasks are " + allTask.size(),Toast.LENGTH_SHORT).show();
        for(taskModel todo : allTask){
            taskList.add(todo);
        }
        taskAdapter = new TodoAdapter(taskList,HomeActivity.this);
        if(taskList.size()>=1)
            taskRecyclerView.setAdapter(taskAdapter);

    }
}