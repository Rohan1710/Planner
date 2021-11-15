package com.example.android.taskplanner.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskplanner.HomeActivity;
import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Model.taskModel;
import com.example.android.taskplanner.R;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private List<taskModel> todoList,todoListAll;
    Context context;

    public TodoAdapter(List<taskModel>list, Context context){
        this.todoList = list;
        this.todoListAll = list;
        this.context = context;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        taskModel item = todoList.get(position);
        holder.title.setText(item.getTitle());
        holder.date.setText(item.getDate());
        holder.time.setText(item.getTime());
        holder.hour.setText("Hour");
        holder.minute.setText("minute");
        holder.id.setText("id");
        holder.status.setText("status");
    }

    public int getItemCount(){
        return todoList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,time,id,hour,minute,status;
        ImageView delete;

        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.TaskTitle);
            date = view.findViewById(R.id.taskDate);
            time = view.findViewById(R.id.taskTime);
            id = view.findViewById(R.id.TaskID);
            hour = view.findViewById(R.id.Taskhour);
            minute = view.findViewById(R.id.TaskMinute);
            delete = view.findViewById(R.id.deletetaskicon);
            status = view.findViewById(R.id.TaskStatus);
        }
    }
}
