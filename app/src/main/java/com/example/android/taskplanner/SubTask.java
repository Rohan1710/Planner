package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Adapter.subtaskAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.taskModel;

import java.util.ArrayList;
import java.util.List;

public class SubTask extends AppCompatActivity {
    TextView taskTitle;
    RecyclerView taskRecyclerView;
    Button addSubtask;
    private subtaskAdapter taskAdapter;
    private List<taskModel> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task);
        taskTitle = findViewById(R.id.TaskNametitle);
        taskRecyclerView = findViewById(R.id.subtaskList);
        addSubtask = findViewById(R.id.AddSub);
        String val = getIntent().getStringExtra("id");
        int id = Integer.parseInt(val);
        String task = getIntent().getStringExtra("task");
        //Toast.makeText(context,"Task Title: "+task,1).show();
        taskTitle.setText(task);
        taskList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(manager);
        taskRecyclerView.setHasFixedSize(true);
        MyDBHandler db = new MyDBHandler(SubTask.this);
        List<taskModel> allTask = db.getAllSubTasks(id);
        //Toast.makeText(this,"Tasks are " + allTask.size(),Toast.LENGTH_SHORT).show();
        for(taskModel todo : allTask){
            taskList.add(todo);
        }
        taskAdapter = new subtaskAdapter(taskList,SubTask.this);
        if(taskList.size()>=1)
            taskRecyclerView.setAdapter(taskAdapter);
        addSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(SubTask.this,AddSubtask.class);
                intent.putExtra("id",id);
                intent.putExtra("task",task);
                startActivity(intent);
            }
        });
    }
}