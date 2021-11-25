package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.android.taskplanner.Adapter.EveryTaskAdapter;
import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.taskModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class SortedTask extends AppCompatActivity{
    public RecyclerView taskRecyclerView;
    private EveryTaskAdapter taskAdapter;
    private List<taskModel> taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorted_task);
        taskRecyclerView= findViewById(R.id.everyTaskList);
        taskList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(manager);
        taskRecyclerView.setHasFixedSize(true);
        MyDBHandler db = new MyDBHandler(SortedTask.this);
        List<taskModel> allTask = db.getEveryTasks();
        Collections.sort(allTask,new callsComparator());
        taskAdapter = new EveryTaskAdapter(allTask,SortedTask.this);
        if(allTask.size()>=1)
            taskRecyclerView.setAdapter(taskAdapter);
    }
}