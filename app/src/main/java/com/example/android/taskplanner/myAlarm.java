package com.example.android.taskplanner;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.TodoModel;

import java.util.Calendar;
import java.util.List;

public class myAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra("id", 0);
        String task = intent.getStringExtra("task");
        int day = intent.getIntExtra("day", 0);
        int month = intent.getIntExtra("month", 0);
        int year = intent.getIntExtra("year", 0);
        int hour = intent.getIntExtra("hour",0);
        int minute = intent.getIntExtra("minute",0);
        int status = intent.getIntExtra("Status", 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(task)
                .setContentText(task)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id,builder.build());
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();
        Toast.makeText(context,"Alarm ringed "+status,Toast.LENGTH_SHORT).show();
        if(status == 1){
            MyDBHandler db = new MyDBHandler(context);
            boolean value = db.DeleteUserData(id);
            Toast.makeText(context,"Task Deleted " + value,Toast.LENGTH_SHORT).show();
        }
        else {
            MyDBHandler db = new MyDBHandler(context);
            Intent intent1 = new Intent(context, myAlarm.class);
            intent1.putExtra("id",id);
            intent1.putExtra("task",task);
            intent1.putExtra("day",day);
            intent1.putExtra("month",month);
            intent1.putExtra("year",year);
            intent1.putExtra("hour",hour);
            intent1.putExtra("minute",minute);
            intent1.putExtra("Status",status);
            long time = (long)status;
            time *= 1000;
            task += "check";
            if(day> 0 && day<28){
                day +=1;
            }
            if(day == 28){
                if(month == 1){
                    if((year%400 == 0)||(year%100 == 0)||(year%4 == 0)){
                        day = 29;
                    }
                    else
                        day = 1;
                    month = 2;
                }
                else
                    day+=1;
            }
            if(day == 29){
                if(month == 1){
                    day = 1;
                    month = 2;
                }
                else{
                    day++;
                }
            }
            if(day == 31){
                day = 1;
                if(month == 11){
                    year++;
                    month = 0;
                }
                else
                    month+=1;
            }
            db.UpdateUserData(id,task,year,month+1,day,hour,minute,status);
            Toast.makeText(context,"Date: " + day +"/"+(month+1) + "/" + year,Toast.LENGTH_SHORT).show();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, 0);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, pendingIntent);
        }
    }
}
