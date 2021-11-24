package com.example.android.taskplanner;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
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
        //Toast.makeText(context,"Task Title: "+task,1).show();
        int day = intent.getIntExtra("day", 0);
        int month = intent.getIntExtra("month", 0);
        int year = intent.getIntExtra("year", 0);
        int hour = intent.getIntExtra("hour",0);
        int minute = intent.getIntExtra("minute",0);
        int status = intent.getIntExtra("Status", 0);
        String priority = intent.getStringExtra("Priority");
        int endHour = intent.getIntExtra("endHour",0);
        int endMinute = intent.getIntExtra("endMinute",0);
        int endYear = intent.getIntExtra("endYear",0);
        int endMonth = intent.getIntExtra("endMonth",0);
        int endDay = intent.getIntExtra("endDay",0);
        int mainTask = intent.getIntExtra("mainTask",0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"foxandroid")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(task)
                .setContentText(hour+":"+minute)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(id,builder.build());
        if(priority.toLowerCase().equals("low")) {
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
            mediaPlayer.start();
        }
        else if(priority.toLowerCase().equals("medium")) {
            MediaPlayer mediaPlayer1 = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer1.start();
        }
        else if(priority.toLowerCase().equals("high")) {
            MediaPlayer mediaPlayer2 = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer2.start();
        }
        boolean check = false;
        //Toast.makeText(context,"Alarm ringed "+status,Toast.LENGTH_SHORT).show();
        if(endHour!=-1 && endMinute!=-1 && endYear!=-1 && endDay!=-1 && endMinute!=-1){
            Calendar finalCalender = Calendar.getInstance();
            finalCalender.set(endYear,endMonth,endDay,endHour,endMinute,0);
            Calendar currCalender = Calendar.getInstance();
            currCalender.set(year,month,day,hour,minute,0);
            if(currCalender.getTimeInMillis()>=finalCalender.getTimeInMillis()){
                check = true;
                MyDBHandler db = new MyDBHandler(context);
                List<String>list = db.DeleteUserData(id);
                for(int i = 0;i<list.size();i++)
                {
                    int intentId = Integer.parseInt(list.get(i));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
        if(status == 1){
            MyDBHandler db = new MyDBHandler(context);
            List<String>list = db.DeleteUserData(id);
            for(int i = 0;i<list.size();i++)
            {
                int intentId = Integer.parseInt(list.get(i));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,intentId,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
            //Toast.makeText(context,"Task Deleted " + value,Toast.LENGTH_SHORT).show();
        }
        else if(check == false){
            MyDBHandler db = new MyDBHandler(context);
            Intent intent1 = new Intent(context, myAlarm.class);
            long time = (long)status;
            time *= 1000;
            int totalDays = status/86400;
            for(int i = 0;i<totalDays;i++){
                if (day > 0 && day < 28) {
                    day += 1;
                }
                else if (day == 28) {
                    if (month == 1) {
                        if ((year % 400 == 0) || (year % 100 == 0) || (year % 4 == 0)) {
                            day = 29;
                        } else
                            day = 1;
                        month = 2;
                    } else
                        day += 1;
                }
                else if (day == 29) {
                    if (month == 1) {
                        day = 1;
                        month = 2;
                    } else {
                        day++;
                    }
                }
                else if(day == 30){
                    if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
                        day+=1;
                    }
                    else{
                        day = 1;
                        if(month == 11){
                            month = 0;
                            year += 1;
                        }
                        else
                            month += 1;
                    }
                }
                else if (day == 31) {
                    day = 1;
                    if (month == 11) {
                        year++;
                        month = 0;
                    } else
                        month += 1;
                }
                //Toast.makeText(context,day+"/"+(month+1)+"/"+year,Toast.LENGTH_SHORT).show();
            }
            if(status == 3600){
                minute += (status/60);
                if(minute>=60)
                {
                    minute -= 60;
                    hour++;
                    if(hour == 24){
                        hour = 0;
                        if (day > 0 && day < 28) {
                            day += 1;
                        }
                        else if (day == 28) {
                            if (month == 1) {
                                if ((year % 400 == 0) || (year % 100 == 0) || (year % 4 == 0)) {
                                    day = 29;
                                } else
                                    day = 1;
                                month = 2;
                            } else
                                day += 1;
                        }
                        else if (day == 29) {
                            if (month == 1) {
                                day = 1;
                                month = 2;
                            } else {
                                day++;
                            }
                        }
                        else if(day == 30){
                            if(month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11){
                                day+=1;
                            }
                            else{
                                day = 1;
                                if(month == 11){
                                    month = 0;
                                    year += 1;
                                }
                                else
                                    month += 1;
                            }
                        }
                        else if (day == 31) {
                            day = 1;
                            if (month == 11) {
                                year++;
                                month = 0;
                            } else
                                month += 1;
                        }
                    }
                }
            }
           // Toast.makeText(context,day+"/"+month+"/"+year,Toast.LENGTH_SHORT).show();
            intent1.putExtra("id",id);
            intent1.putExtra("task",task);
            intent1.putExtra("day",day);
            intent1.putExtra("month",month);
            intent1.putExtra("year",year);
            intent1.putExtra("hour",hour);
            intent1.putExtra("minute",minute);
            intent1.putExtra("Status",status);
            intent1.putExtra("Priority",priority);
            intent1.putExtra("check",0);
            intent1.putExtra("endHour",endHour);
            intent1.putExtra("endMinute",endMinute);
            intent1.putExtra("endYear",endYear);
            intent1.putExtra("endMonth",endMonth);
            intent1.putExtra("endDay",endDay);
            intent1.putExtra("mainTask",mainTask);
            Calendar calendar = Calendar.getInstance();
            calendar.set(year,month,day,hour,minute,0);
            db.UpdateUserData(id,task,year,month+1,day,hour,minute,status,endHour,endMinute,priority,endYear,endMonth,endDay,mainTask);
            //Toast.makeText(context,"Date: " + day +"/"+(month+1) + "/" + year,Toast.LENGTH_SHORT).show();
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent1, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
    }
}
