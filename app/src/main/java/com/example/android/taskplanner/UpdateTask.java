package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Update;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.google.mlkit.nl.entityextraction.Entity;
import com.google.mlkit.nl.entityextraction.EntityAnnotation;
import com.google.mlkit.nl.entityextraction.EntityExtraction;
import com.google.mlkit.nl.entityextraction.EntityExtractionParams;
import com.google.mlkit.nl.entityextraction.EntityExtractor;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText updateTitle,updateDate,updateTime,updateEndDate,updateEndTime;
    private static final String TAG = "MainActivityJAVA";
    private TextView output;
    Button updateButton;
    Spinner taskRepeat,updatePriority;
    int tyear,tmonth,tday,thour,tminute,endYear,endMonth,endDay,endhour,endminute;
    String priority;
    private EntityExtractor entityExtractor;
    int status = 1;
    int id;
    String date,time,title;
    private static EntityExtractionParams getEntityExtractionParams(String input) {
        return new EntityExtractionParams.Builder(input).build();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);
        thour = tminute = 0;
        priority = "Low";
        endYear = endMonth = endDay = endhour = endminute = -1;
        updateTitle = findViewById(R.id.UpdateTitle);
        updateDate = findViewById(R.id.UpdateDate);
        updateTime = findViewById(R.id.UpdateTime);
        updateButton = findViewById(R.id.UpdateButton);
        updateEndDate = findViewById(R.id.UpdateEndDate);
        updateEndTime = findViewById(R.id.UpdateEndTime);
        updatePriority = findViewById(R.id.UpdatePriority);
        taskRepeat = findViewById(R.id.UpdateRepeat);
        output = findViewById(R.id.output);
        entityExtractor =
                EntityExtraction.getClient(
                        new EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                                .build());
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
        updateEndDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            UpdateTask.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            endYear = year;
                            endMonth = month;
                            endDay = day;
                            month = month + 1;
                            String date = day + "/" + month + "/" + year;
                            updateEndDate.setText(date);
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
        updateEndTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            UpdateTask.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourofDay, int minute) {
                                    endhour = hourofDay;
                                    endminute = minute;
                                    String time = endhour + ":" + endminute;
                                    SimpleDateFormat fdate = new SimpleDateFormat("HH:mm");
                                    try {
                                        Date date = fdate.parse(time);
                                        SimpleDateFormat f12hour = new SimpleDateFormat("hh:mm aa");
                                        updateEndTime.setText(f12hour.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, 12, 0, false
                    );
                    timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    timePickerDialog.updateTime(endhour, endminute);
                    timePickerDialog.show();
                } else {

                }
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskRepeat.setAdapter(adapter);
        taskRepeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    status = 1;
                else{
                    if(i == 1)
                        status = 3600;
                    if(i == 2)
                        status = 86400;
                    if(i == 3)
                        status = 86400*7;
                    if(i == 4)
                        status = 86400*14;
                    updateEndDate.setVisibility(View.VISIBLE);
                    updateEndTime.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Priority,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        updatePriority.setAdapter(adapter2);
        updatePriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0)
                    priority = "Low";
                if(i == 1)
                    priority = "Medium";
                if(i == 2)
                    priority = "High";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                priority = "Low";
            }
        });
        updateTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                extractEntities(updateTitle.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task = updateTitle.getText().toString();
                if(task.isEmpty()) {
                    Toast.makeText(UpdateTask.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                    return;
                }
//
                calendar.set(tyear,tmonth,tday,thour,tminute,0);
                if (validate()) {
                    if(System.currentTimeMillis()<calendar.getTimeInMillis()) {
                        db.UpdateUserData(id,task,tyear,tmonth+1,tday,thour,tminute,status,endhour,endminute,priority,endYear,endMonth,endDay,-1);
                        //long id = db.insertUserData(task, tyear, tmonth + 1, tday, thour, tminute, status,0,endhour,endminute,priority,endYear,endMonth,endDay);
                        if (id != -1) {
                            int intentId = (int) id;
                            // Toast.makeText(AddTask.this, "value" + year + "/" + month + "/" + day, Toast.LENGTH_LONG).show();
                            setAlarm(calendar.getTimeInMillis(), intentId);
                            Toast.makeText(UpdateTask.this, "Task Updated", Toast.LENGTH_SHORT).show();
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

    public void extractEntities(final String input) {
        entityExtractor
                .downloadModelIfNeeded()
                .onSuccessTask(ignored -> entityExtractor.annotate(getEntityExtractionParams(input)))
                .addOnFailureListener(
                        e -> {
                            Log.e(TAG, "Annotation failed", e);
                            output.setText(getString(R.string.entity_extraction_error));
                        })
                .addOnSuccessListener(
                        result -> {
                            if (result.isEmpty()) {

                            }
                            for (EntityAnnotation entityAnnotation : result) {
                                List<Entity> entities = entityAnnotation.getEntities();
                                String annotatedText = entityAnnotation.getAnnotatedText();
                                for (Entity entity : entities) {
//                                    displayEntityInfo(annotatedText, entity);
                                    output.append("\n");
                                    if(entity.getType() == Entity.TYPE_DATE_TIME){
                                        String tm_date = String.valueOf(DateFormat.getDateInstance(DateFormat.SHORT)
                                                .format(new Date(entity.asDateTimeEntity().getTimestampMillis())));
                                        String tm_time = String.valueOf(DateFormat.getTimeInstance(DateFormat.SHORT)
                                                .format(new Date(entity.asDateTimeEntity().getTimestampMillis())));
                                        if(!tm_date.isEmpty()){
                                            updateDate.setText(tm_date);
                                            String tmpo = updateDate.getText().toString();
                                            String syear, smonth, sday;
                                            syear = smonth = sday = "";
                                            int check = 0;
                                            for(int i=0;i<tmpo.length();i++){
                                                if(tmpo.charAt(i) == '/'){
                                                    check++;
                                                    continue;
                                                }
                                                if(check == 0){
                                                    sday += tmpo.charAt(i);
                                                }
                                                if(check == 1){
                                                    smonth += tmpo.charAt(i);
                                                }
                                                if(check == 2){
                                                    syear += tmpo.charAt(i);
                                                }
                                            }
                                            tyear = Integer.parseInt(syear);
                                            tmonth = Integer.parseInt(smonth);
                                            tmonth -= 1;
                                            tday = Integer.parseInt(sday);
//                                        output.append(tmp);
                                            Log.d("Rohan","Output : " +tyear +"/"+ tmonth + tday);
                                        }
                                        if(!tm_time.isEmpty()){

                                            updateTime.setText(tm_time);
                                            String tmpo1 = updateTime.getText().toString();
                                            String shour, sminute;
                                            shour = sminute = "";
                                            int check = 0;
                                            for(int i=0;i<tm_time.length();i++){
                                                if(tmpo1.charAt(i) == ':' || tmpo1.charAt(i) == ' '){
                                                    check++;
                                                    continue;
                                                }
                                                if(check == 0){
                                                    shour += tmpo1.charAt(i);
                                                }
                                                if(check == 1) {
                                                    sminute += tmpo1.charAt(i);
                                                }
                                            }

                                            thour = Integer.parseInt(shour);
                                            tminute = Integer.parseInt(sminute);
                                            int ln = tm_time.length();
                                            if(ln>0 && tm_time.charAt(tm_time.length()-2) == 'p'){
                                                thour += 12;
                                            }
                                            if(ln>0 && tm_time.charAt(tm_time.length()-2) == 'a' && thour == 12){
                                                thour = 0;
                                            }
                                            Log.d("Rohan","Output : " + updateTime.getText().toString());
                                            Log.d("Rohan","Output : " + tminute+"/" + thour);
                                        }

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
        //Toast.makeText(AddTask.this,"Alarm is Set " + status,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,myAlarm.class);
        intent.putExtra("id",id);
        intent.putExtra("task",updateTitle.getText().toString());
        intent.putExtra("day",tday);
        intent.putExtra("month",tmonth);
        intent.putExtra("year",tyear);
        intent.putExtra("hour",thour);
        intent.putExtra("minute",tminute);
        intent.putExtra("Status",status);
        intent.putExtra("Priority",priority);
        intent.putExtra("check",0);
        intent.putExtra("endHour",endhour);
        intent.putExtra("endMinute",endminute);
        intent.putExtra("endYear",endYear);
        intent.putExtra("endMonth",endMonth);
        intent.putExtra("endDay",endDay);
        //Toast.makeText(AddTask.this,"Task Title: "+input.getText().toString(),1).show();
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
        //Toast.makeText(this,"The Status is " + status,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        status = 1;
    }

}