package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.taskplanner.Data.MyDBHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UpdateTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText updateTitle,updateDate,updateTime;
    Button updateButton;
    Spinner taskRepeat;
    int tyear,tmonth,tday,thour,tminute;
    int status = 1;
    int id;
    String date,time,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        updateTitle = findViewById(R.id.UpdateTitle);
        updateDate = findViewById(R.id.UpdateDate);
        updateTime = findViewById(R.id.UpdateTime);
        updateButton = findViewById(R.id.UpdateButton);
        taskRepeat = findViewById(R.id.UpdateRepeat);
        String val = getIntent().getStringExtra("id");
        id = Integer.parseInt(val);
        date = getIntent().getStringExtra("date");
        time = getIntent().getStringExtra("time");
        title = getIntent().getStringExtra("title");
        updateDate.setText(date);
        updateTime.setText(time);
        updateTitle.setText(title);
        String cday,cmonth,cyear,chour,cminute;
        cday = cmonth = cyear = chour = cminute = "";
        int cnt = 0;
        for(int i = 0;i<date.length();i++){
            if(date.charAt(i) == '/'){
                cnt++;
                continue;
            }
            if(cnt == 0){
                cday += date.charAt(i);
            }
            else if(cnt == 1){
                cmonth += date.charAt(i);
            }
            else
                cyear += date.charAt(i);
        }
        cnt = 0;
        for(int i = 0;i<time.length();i++){
            if(time.charAt(i) == ':'){
                cnt++;
                continue;
            }
            if(cnt == 0){
                chour += time.charAt(i);
            }
            else
                cminute += time.charAt(i);
        }
        tday =Integer.parseInt(cday);
        tmonth = Integer.parseInt(cmonth);
        tyear = Integer.parseInt(cyear);
        thour = Integer.parseInt(chour);
        tminute = Integer.parseInt(cminute);
        tmonth -= 1;
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        MyDBHandler db = new MyDBHandler(UpdateTask.this);
        updateDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            UpdateTask.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            tyear = year;
                            tmonth = month;
                            tday = day;
                            month = month + 1;
                            String date = day + "/" + month + "/" + year;
                            updateDate.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                } else {

                }
            }
        });
        updateTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            UpdateTask.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourofDay, int minute) {
                                    thour = hourofDay;
                                    tminute = minute;
                                    String time = thour + ":" + tminute;
                                    SimpleDateFormat fdate = new SimpleDateFormat("HH:mm");
                                    try {
                                        Date date = fdate.parse(time);
                                        SimpleDateFormat f12hour = new SimpleDateFormat("hh:mm aa");
                                        updateTime.setText(f12hour.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, 12, 0, false
                    );
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePickerDialog.updateTime(thour, tminute);
                    timePickerDialog.show();
                } else {

                }
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskRepeat.setAdapter(adapter);
        taskRepeat.setOnItemSelectedListener(UpdateTask.this);
        updateTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {

                }
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.set(tyear,tmonth,tday,thour,tminute,0);
                if (validate()) {
                    if(System.currentTimeMillis()<calendar.getTimeInMillis()) {
                        String task = updateTitle.getText().toString();
                        db.UpdateUserData(id,task,tyear,tmonth+1,tday,thour,tminute,status);
                        if (id != -1) {
                            int intentId = (int) id;
                            //Toast.makeText(AddTask.this, "value" + year + "/" + month + "/" + day, Toast.LENGTH_LONG).show();
                            setAlarm(calendar.getTimeInMillis(), intentId);
                            Toast.makeText(UpdateTask.this, "Task Updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateTask.this,HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateTask.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(UpdateTask.this,"Check Date And Time Again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public boolean validate() {
        if (updateTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (updateDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (updateTime.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void setAlarm(long timeInMillis,int id) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        // Toast.makeText(AddTask.this,"Alarm is Set " + status,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,myAlarm.class);
        intent.putExtra("id",id);
        intent.putExtra("task",updateTitle.getText().toString());
        intent.putExtra("day",tday);
        intent.putExtra("month",tmonth);
        intent.putExtra("year",tyear);
        intent.putExtra("hour",thour);
        intent.putExtra("minute",tminute);
        intent.putExtra("Status",status);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,id,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,timeInMillis,pendingIntent);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,timeInMillis-15000,AlarmManager.INTERVAL_DAY,pendingIntent);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i == 1)
            status = 900;
        if(i == 2)
            status = 1800;
        if(i == 3)
            status = 3600;
        if(i == 4)
            status = 86400;
        if(i == 0)
            status = 1;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        status = 1;
    }
}