package com.example.android.taskplanner.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskplanner.AddTask;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.HomeActivity;
import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<TodoModel> todoList;
    private MyDBHandler db;
    HomeActivity activity;

    public TodoAdapter(MyDBHandler db, HomeActivity activity) {
        this.db = db;
        this.activity = activity;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        TodoModel item = todoList.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setId(item.getId());
//        holder.deleteiocon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                db.deleteTaskByID(item.getId());
////                (activity).finish();
////                activity.startActivity(activity.getIntent());
//                Log.d("dbrohan","deleted Task : " + item.getId());
//            }
//        });
    }

    public int getItemCount(){
        return todoList.size();
    }

    private boolean toBoolean(int n){
        return n!=0;
    }

    public void setTasks(List<TodoModel> todoList){
        this.todoList = todoList;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        TodoModel item = todoList.get(position);
        db.deleteTaskByID(item.getId());
        todoList.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        ImageView deleteiocon;
        TextView taskID;
//        MyDBHandler db = new MyDBHandler(activity);
        ViewHolder(View view){
            super(view);
            task = view.findViewById(R.id.todoCheckbox);
            deleteiocon = view.findViewById(R.id.deletetaskicon);
            taskID = view.findViewById(R.id.TaskID);
        }
    }

}
