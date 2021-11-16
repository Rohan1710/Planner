package com.example.android.taskplanner.Adapter;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.android.taskplanner.UpdateTask;
import com.example.android.taskplanner.myAlarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> implements Filterable {

    private List<taskModel> todoList,todoListAll;
    static Context context;

    public TodoAdapter(List<taskModel>list, Context context){
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
    }

    public int getItemCount(){
        return todoList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            //Toast.makeText(context,charSequence.toString(),Toast.LENGTH_SHORT).show();
            List<taskModel>filteredList = new ArrayList<>();
            if(charSequence.toString().isEmpty()){
                filteredList.addAll(todoListAll);
            }else{
                for(taskModel task: todoListAll){
                    if(task.getDate().contains(charSequence.toString())){
                        filteredList.add(task);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            todoList.clear();
            todoList.addAll((Collection<? extends taskModel>) filterResults.values);
            notifyDataSetChanged();
        }
    };

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
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateTask.class);
                    intent.putExtra("id",id.getText().toString());
                    intent.putExtra("date",date.getText().toString());
                    intent.putExtra("time",time.getText().toString());
                    intent.putExtra("title",title.getText().toString());
                    context.startActivity(intent);
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
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                            AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);
                            MyDBHandler db = new MyDBHandler(context);
                            boolean value = db.DeleteUserData(intentId);
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
                    builder.create().show();
                }
            });
        }
    }
}
