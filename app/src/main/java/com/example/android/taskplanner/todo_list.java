package com.example.android.taskplanner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class todo_list extends AppCompatActivity {

    ListView myListView;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        myListView = findViewById(R.id.myList);

        String value = getIntent().getExtras().getString("key");
        tasks.add(value);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tasks);
        myListView.setAdapter(adapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(todo_list.this, "task number" , Toast.LENGTH_SHORT).show();
            }
        });



    }
}
