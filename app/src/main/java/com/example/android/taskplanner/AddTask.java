package com.example.android.taskplanner;

import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_DAY;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_HOUR;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_MINUTE;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_MONTH;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_SECOND;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_WEEK;
import static com.google.mlkit.nl.entityextraction.DateTimeEntity.GRANULARITY_YEAR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

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
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Model.TodoModel;
import com.google.mlkit.nl.entityextraction.DateTimeEntity;
import com.google.mlkit.nl.entityextraction.Entity;
import com.google.mlkit.nl.entityextraction.EntityAnnotation;
import com.google.mlkit.nl.entityextraction.EntityExtraction;
import com.google.mlkit.nl.entityextraction.EntityExtractionParams;
import com.google.mlkit.nl.entityextraction.EntityExtractor;
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions;
import com.google.mlkit.nl.entityextraction.FlightNumberEntity;
import com.google.mlkit.nl.entityextraction.IbanEntity;
import com.google.mlkit.nl.entityextraction.MoneyEntity;
import com.google.mlkit.nl.entityextraction.PaymentCardEntity;
import com.google.mlkit.nl.entityextraction.TrackingNumberEntity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTask extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivityJAVA";
    Button addTask,checkEmails;
    private EditText input;
    private TextView output;
    private EntityExtractor entityExtractor;
    EditText taskDate, taskTime;
    Spinner taskRepeat;
    int tyear, tmonth, tday, thour, tminute;
    int status = 1;


    private static EntityExtractionParams getEntityExtractionParams(String input) {
        return new EntityExtractionParams.Builder(input).build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        createNotificationChannel();
        entityExtractor =
                EntityExtraction.getClient(
                        new EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                                .build());

        thour = tminute = 0;
        input = findViewById(R.id.text_input);
        output = findViewById(R.id.output);
        addTask = findViewById(R.id.add_button);
        taskDate = findViewById(R.id.TaskDate);
        taskTime = findViewById(R.id.TaskTime);
        taskRepeat = findViewById(R.id.TaskRepeat);
        checkEmails = findViewById(R.id.Emailbutton);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        MyDBHandler db = new MyDBHandler(AddTask.this);
        taskDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            AddTask.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            tyear = year;
                            tmonth = month;
                            tday = day;
                            month = month + 1;
                            String date = day + "/" + month + "/" + year;
                            taskDate.setText(date);
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                } else {

                }
            }
        });
        taskTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            AddTask.this,
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
                                        taskTime.setText(f12hour.format(date));
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
        taskRepeat.setOnItemSelectedListener(this);
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                extractEntities(input.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String task = input.getText().toString();
                if(task.isEmpty()) {
                    Toast.makeText(AddTask.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                    return;
                }

//
                calendar.set(tyear,tmonth,tday,thour,tminute,0);
                if (validate()) {
                    if(System.currentTimeMillis()<calendar.getTimeInMillis()) {
                        long id = db.insertUserData(task, tyear, tmonth + 1, tday, thour, tminute, status);
                        if (id != -1) {
                            int intentId = (int) id;
                           // Toast.makeText(AddTask.this, "value" + year + "/" + month + "/" + day, Toast.LENGTH_LONG).show();
                            setAlarm(calendar.getTimeInMillis(), intentId);
                            Toast.makeText(AddTask.this, "New Task Added", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTask.this, "Some Error Occured!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(AddTask.this,"Check Date And Time Again",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        checkEmails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddTask.this);
                builder.setTitle("Check Emails");
                builder.setMessage("Check Emails to shedule meets?");
                builder.setPositiveButton("CHECK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = Intent.makeMainSelectorActivity(
                                Intent.ACTION_MAIN,
                                Intent.CATEGORY_APP_EMAIL);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "Email"));
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
                                            taskDate.setText(tm_date);
                                        String tmpo = taskDate.getText().toString();
                                        String syear, smonth, sday;
                                        syear = smonth = sday = "";
                                        syear = "20";
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

                                            taskTime.setText(tm_time);
                                            String tmpo1 = taskTime.getText().toString();
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
                                            Log.d("Rohan","Output : " + taskTime.getText().toString());
                                            Log.d("Rohan","Output : " + tminute+"/" + thour);
                                        }

                                    }
                                }
                            }
                        });
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel for Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public boolean validate() {
        if (input.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Task", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (taskDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter the Date", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (taskTime.getText().toString().isEmpty()) {
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
        intent.putExtra("task",input.getText().toString());
        intent.putExtra("day",tday);
        intent.putExtra("month",tmonth);
        intent.putExtra("year",tyear);
        intent.putExtra("hour",thour);
        intent.putExtra("minute",tminute);
        intent.putExtra("Status",status);
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