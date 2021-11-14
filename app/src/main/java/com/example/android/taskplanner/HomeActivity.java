package com.example.android.taskplanner;

import androidx.annotation.NonNull;

import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.TodoModel;
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
import android.widget.ImageView;
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

    private List<TodoModel> taskList;
    public CalendarView calendarView;
    private MyDBHandler db;


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

        calendarView = findViewById(R.id.calenderView);


        db = new MyDBHandler(this);
        db.openDatabase();


        taskList = new ArrayList<>();

        taskRecyclerView = findViewById(R.id.taskRecyclerView);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskAdapter = new TodoAdapter(db,HomeActivity.this);
        taskRecyclerView.setAdapter(taskAdapter);





//        taskList.add(task);
//        taskList.add(task);
//        taskList.add(task);



        MyDBHandler db = new MyDBHandler(HomeActivity.this);
        List<TodoModel> allTask = db.getAllTasks();
        for(TodoModel todo : allTask){
            Log.d("dbrohan","Task ID: " + todo.getId() + "\n" +
                    "Task: " + todo.getTask() + "\n" +
                    "Date: " + todo.getDdate() + "\n" +
                    "Status: " + todo.getStatus() + "\n" );
            taskList.add(todo);
        }
        taskAdapter.setTasks(taskList);
//        Log.d("dbTask","You have" + db.getCount());




    }
}