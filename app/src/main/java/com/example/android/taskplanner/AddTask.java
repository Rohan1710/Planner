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

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddTask extends AppCompatActivity {

    private static final String TAG = "MainActivityJAVA";
    Button addTask;
    private EditText input;
    private TextView output;
    private EntityExtractor entityExtractor;
    private CalendarView calendarView;

    private static EntityExtractionParams getEntityExtractionParams(String input) {
        return new EntityExtractionParams.Builder(input).build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        entityExtractor =
                EntityExtraction.getClient(
                        new EntityExtractorOptions.Builder(EntityExtractorOptions.ENGLISH)
                                .build());

        input = findViewById(R.id.text_input);
        output = findViewById(R.id.output);
        addTask = findViewById(R.id.add_button);
        calendarView = findViewById(R.id.calenderView);

        addTask.setOnClickListener(
                v -> {
                    String newInput = input.getText().toString();
                    if (newInput.isEmpty()) {
                        Toast.makeText(AddTask.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                        return;
                    }
                    extractEntities(newInput);
                });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {

            }
        });




    }

    public void extractEntities(final String input) {
        output.setText(R.string.wait_message);

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
                            MyDBHandler db = new MyDBHandler(AddTask.this);
                            TodoModel rohan = new TodoModel();
                            rohan.setTask(input);

                            rohan.setStatus(0);
//                            rohan.setId(2);
                            db.addTask(rohan);

                            if (result.isEmpty()) {

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                String date = sdf.format(new Date());

//                                task.setId(1);
//                                task.setDdate(date);
//                                output.setText(getString(R.string.no_entity_detected));
                                output.append("Task Added and entities not detected");
                                rohan.setDdate(date);
//                                db.addTask(rohan);
//                                Intent intent = new Intent(AddTask.this,HomeActivity.class);
//                                startActivity(intent);
                            }
                            output.setText(getString(R.string.entities_detected));
                            output.append("\n");
                            for (EntityAnnotation entityAnnotation : result) {
                                List<Entity> entities = entityAnnotation.getEntities();
                                String annotatedText = entityAnnotation.getAnnotatedText();
                                for (Entity entity : entities) {
//                                    displayEntityInfo(annotatedText, entity);
                                    output.append("\n");
                                    if(entity.getType() == Entity.TYPE_DATE_TIME){
                                        String tmp = String.valueOf(DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)
                                                .format(new Date(entity.asDateTimeEntity().getTimestampMillis())));
                                        rohan.setDdate(tmp);
                                        output.append(tmp);
                                    }
                                }
                            }
                            db.addTask(rohan);
                            output.append("\nTask Added and entities detected");
                            Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddTask.this,HomeActivity.class);
                            startActivity(intent);

                        });
    }

    /*private void displayEntityInfo(String annotatedText, Entity entity) {
        switch (entity.getType()) {
            case Entity.TYPE_ADDRESS:
                displayAddressInfo(annotatedText);
                break;
            case Entity.TYPE_DATE_TIME:
                displayDateTimeInfo(entity, annotatedText);
                break;
            case Entity.TYPE_EMAIL:
                displayEmailInfo(annotatedText);
                break;
            case Entity.TYPE_FLIGHT_NUMBER:
                displayFlightNoInfo(entity, annotatedText);
                break;
            case Entity.TYPE_IBAN:
                displayIbanInfo(entity, annotatedText);
                break;
            case Entity.TYPE_ISBN:
                displayIsbnInfo(entity, annotatedText);
                break;
            case Entity.TYPE_MONEY:
                displayMoneyEntityInfo(entity, annotatedText);
                break;
            case Entity.TYPE_PAYMENT_CARD:
                displayPaymentCardInfo(entity, annotatedText);
                break;
            case Entity.TYPE_PHONE:
                displayPhoneInfo(annotatedText);
                break;
            case Entity.TYPE_TRACKING_NUMBER:
                displayTrackingNoInfo(entity, annotatedText);
                break;
            case Entity.TYPE_URL:
                displayUrlInfo(annotatedText);
                break;
            default:
                displayDefaultInfo(annotatedText);
                break;
        }
    }*/

    /*private String convertGranularityToString(Entity entity) {
        DateTimeEntity dateTimeEntity = entity.asDateTimeEntity();
        @DateTimeEntity.DateTimeGranularity int granularity = dateTimeEntity.getDateTimeGranularity();
        switch (granularity) {
            case GRANULARITY_YEAR:
                return getString(R.string.granularity_year);
            case GRANULARITY_MONTH:
                return getString(R.string.granularity_month);
            case GRANULARITY_WEEK:
                return getString(R.string.granularity_week);
            case GRANULARITY_DAY:
                return getString(R.string.granularity_day);
            case GRANULARITY_HOUR:
                return getString(R.string.granularity_hour);
            case GRANULARITY_MINUTE:
                return getString(R.string.granularity_minute);
            case GRANULARITY_SECOND:
                return getString(R.string.granularity_second);
            default:
                return getString(R.string.granularity_unknown);
        }
    }*/

/*
    private void displayAddressInfo(String annotatedText) {
        output.append(getString(R.string.address_entity_info, annotatedText));
    }

    private void displayEmailInfo(String annotatedText) {
        output.append(getString(R.string.email_entity_info, annotatedText));
    }

    private void displayPhoneInfo(String annotatedText) {
        output.append(
                getString(
                        R.string.phone_entity_info_formatted,
                        annotatedText,
                        PhoneNumberUtils.formatNumber(annotatedText)));
    }

    private void displayDefaultInfo(String annotatedText) {
        output.append(getString(R.string.unknown_entity_info, annotatedText));
    }

    private void displayUrlInfo(String annotatedText) {
        output.append(getString(R.string.url_entity_info, annotatedText));
    }*/

   /* private void displayDateTimeInfo(Entity entity, String annotatedText) {
        output.append(
                getString(
                        R.string.date_time_entity_info,
                        annotatedText,
                        DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG)
                                .format(new Date(entity.asDateTimeEntity().getTimestampMillis())),
                        convertGranularityToString(entity)));
    }*/

/*
    private void displayTrackingNoInfo(Entity entity, String annotatedText) {
        TrackingNumberEntity trackingNumberEntity = entity.asTrackingNumberEntity();
        output.append(
                getString(
                        R.string.tracking_number_entity_info,
                        annotatedText,
                        trackingNumberEntity.getParcelCarrier(),
                        trackingNumberEntity.getParcelTrackingNumber()));
    }

    private void displayPaymentCardInfo(Entity entity, String annotatedText) {
        PaymentCardEntity paymentCardEntity = entity.asPaymentCardEntity();
        output.append(
                getString(
                        R.string.payment_card_entity_info,
                        annotatedText,
                        paymentCardEntity.getPaymentCardNetwork(),
                        paymentCardEntity.getPaymentCardNumber()));
    }

    private void displayIsbnInfo(Entity entity, String annotatedText) {
        output.append(
                getString(R.string.isbn_entity_info, annotatedText, entity.asIsbnEntity().getIsbn()));
    }

    private void displayIbanInfo(Entity entity, String annotatedText) {
        IbanEntity ibanEntity = entity.asIbanEntity();
        output.append(
                getString(
                        R.string.iban_entity_info,
                        annotatedText,
                        ibanEntity.getIban(),
                        ibanEntity.getIbanCountryCode()));
    }

    private void displayFlightNoInfo(Entity entity, String annotatedText) {
        FlightNumberEntity flightNumberEntity = entity.asFlightNumberEntity();
        output.append(
                getString(
                        R.string.flight_number_entity_info,
                        annotatedText,
                        flightNumberEntity.getAirlineCode(),
                        flightNumberEntity.getFlightNumber()));
    }

    private void displayMoneyEntityInfo(Entity entity, String annotatedText) {
        MoneyEntity moneyEntity = entity.asMoneyEntity();
        output.append(
                getString(
                        R.string.money_entity_info,
                        annotatedText,
                        moneyEntity.getUnnormalizedCurrency(),
                        moneyEntity.getIntegerPart(),
                        moneyEntity.getFractionalPart()));
    }*/
/*
    public void addtolist(TextView newOutput){
        String value = newOutput.getText().toString();
        Intent intent = new Intent(this, todo_list.class);
        intent.putExtra("key",value);
        startActivity(intent);
    }*/
}