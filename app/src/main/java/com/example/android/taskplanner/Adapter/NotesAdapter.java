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
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.R;
import com.example.android.taskplanner.SubTask;
import com.example.android.taskplanner.myAlarm;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title,date,maintask,time,id,hour,minute;
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

            /*view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    *//*Intent intent = new Intent(context, UpdateTask.class);
                    intent.putExtra("id",id.getText().toString());
                    intent.putExtra("date",date.getText().toString());
                    intent.putExtra("time",time.getText().toString());
                    intent.putExtra("title",title.getText().toString());
                    context.startActivity(intent);*//*
                    Intent intent = new Intent(context, SubTask.class);
                    intent.putExtra("id",id.getText().toString());
                    intent.putExtra("task",title.getText().toString());
                    intent.putExtra("date",date.getText().toString());
                    intent.putExtra("Status",status.getText().toString());
                    intent.putExtra("Priority",priority.getText().toString());
                    intent.putExtra("endHour",repeatEndHour.getText().toString());
                    intent.putExtra("endMinute",repeatEndMinute.getText().toString());
                    intent.putExtra("endYear",repeateEndYear.getText().toString());
                    intent.putExtra("endMonth",repeateEndMonth.getText().toString());
                    intent.putExtra("endDay",repeateEndDay.getText().toString());
                    context.startActivity(intent);
                }
            });*/
            /*delete.setOnClickListener(new View.OnClickListener() {
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
            });*/
        }
    }
}
