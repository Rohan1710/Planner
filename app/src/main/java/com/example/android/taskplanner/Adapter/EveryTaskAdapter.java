package com.example.android.taskplanner.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.HomeActivity;
import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Model.taskModel;
import com.example.android.taskplanner.R;
import com.example.android.taskplanner.SubTask;
import com.example.android.taskplanner.UpdateTask;
import com.example.android.taskplanner.myAlarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class EveryTaskAdapter extends RecyclerView.Adapter<EveryTaskAdapter.ViewHolder> {

    private List<taskModel> todoList,todoListAll;
    static Context context;

    public EveryTaskAdapter(List<taskModel>list, Context context){
        this.todoList = list;
        this.todoListAll = new ArrayList<>(todoList);
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
        holder.hour.setText(""+item.getHour());
        holder.minute.setText(""+item.getMinute());
        holder.id.setText(""+item.getId());
        holder.status.setText(""+item.getStatus());
        holder.checked.setChecked(false);
        holder.priority.setText(""+item.getPriority());
        holder.repeateEndDay.setText(""+item.getRepeatEndDay());
        holder.repeateEndMonth.setText(""+item.getRepeatEndMonth());
        holder.repeateEndYear.setText(""+item.getRepeateEndYear());
        holder.repeatEndHour.setText(""+item.getRepeatEndHour());
        holder.repeatEndMinute.setText(""+item.getRepeatEndMinute());
        holder.maintask.setText(""+item.getMaintask());
    }

    public int getItemCount(){
        return todoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,maintask,time,id,hour,minute,status,priority,repeateEndDay,repeateEndMonth,repeateEndYear,repeatEndHour,repeatEndMinute;
        ImageView delete;
        CheckBox checked;
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
            checked = view.findViewById(R.id.TaskChecked);
            priority = view.findViewById(R.id.TaskPriority);
            repeatEndHour = view.findViewById(R.id.TaskRepeatEndHour);
            repeatEndMinute = view.findViewById(R.id.TaskRepeatEndMinute);
            repeateEndYear = view.findViewById(R.id.TaskRepeatEndYear);
            repeateEndMonth = view.findViewById(R.id.TaskRepeatEndMonth);
            repeateEndDay = view.findViewById(R.id.TaskRepeatEndDay);
            maintask = view.findViewById(R.id.mainTask);
            if(priority.getText().toString().toLowerCase() == "low"){
                title.setTextColor(Color.GREEN);
            }
            if(priority.getText().toString().toLowerCase() == "medium"){
                title.setTextColor(Color.RED);
            }
            if(priority.getText().toString().toLowerCase() == "high"){
                title.setTextColor(Color.BLUE);
            }
            checked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checked.isChecked()){
                        Intent intent = new Intent(context, myAlarm.class);
                        int intentId = Integer.parseInt(id.getText().toString());
                        MyDBHandler db = new MyDBHandler(context);
                        List<String>list = db.DeleteUserData(intentId);
                        for(int i = 0;i<list.size();i++){
                            intentId = Integer.parseInt(list.get(i));
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);
                        }
                        title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    else{

                    }
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Task");
                    builder.setMessage("Are You Sure to Delete this Task");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, myAlarm.class);
                            int intentId = Integer.parseInt(id.getText().toString());
                            MyDBHandler db = new MyDBHandler(context);
                            List<String>list = db.DeleteUserData(intentId);
                            for(int i = 0;i<list.size();i++){
                                intentId = Integer.parseInt(list.get(i));
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                                AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                                alarmManager.cancel(pendingIntent);
                            }
                            Toast.makeText(context,"Task Deleted Successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    if(!(context instanceof Activity && ((Activity) context).isFinishing()))
                        builder.create().show();
                }
            });
        }
    }
}
