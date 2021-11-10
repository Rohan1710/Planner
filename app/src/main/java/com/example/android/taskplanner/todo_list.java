package com.example.android.taskplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class todo_list extends AppCompatActivity {

    ImageView refresh, add;

    TextView output;
    ListView myListView;
    ArrayList<String> tasks = new ArrayList<>();
    ArrayAdapter<String> adapter;

    public static int top = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list);

        myListView = findViewById(R.id.myList);
        refresh = findViewById(R.id.refresh);
        add = findViewById(R.id.add_task);
        output = (TextView) findViewById(R.id.output);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,tasks);
        myListView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(todo_list.this, AddTask.class);
                startActivity(intent);
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = output.getText().toString();
                Toast.makeText(todo_list.this, text, Toast.LENGTH_SHORT).show();
//                if(text == null || text.length() == 0){
//                    Toast.makeText(todo_list.this, "Please add Task", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    tasks.add(text);
//                    myListView.setAdapter(adapter);
//                }
            }
        });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(todo_list.this, "task number" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
