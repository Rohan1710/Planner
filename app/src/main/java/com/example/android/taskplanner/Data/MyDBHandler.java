package com.example.android.taskplanner.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Model.taskModel;
import com.example.android.taskplanner.Parameters.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " CREATE TABLE " + Params.TABLE_NAME + " (" +
                Params.KEY_ID + " INTEGER PRIMARY KEY, " +
                Params.KEY_TASK + " TEXT NOT NULL, " +
                Params.KEY_DAY + " INTEGER NOT NULL, " +
                Params.KEY_MONTH + " INTEGER NOT NULL, " +
                Params.KEY_YEAR + " INTEGER NOT NULL, " +
                Params.KEY_HOUR + " INTEGER NOT NULL, " +
                Params.KEY_MINUTE + " INTEGER NOT NULL, " +
                Params.KEY_STATUS + " INTEGER NOT NULL, " +
                Params.KEY_PRIORITY + " TEXT NOT NULL, " +
                Params.KEY_REPEATENDHOUR + " INTEGER NOT NULL, " +
                Params.KEY_REPEATENDMINUTE + " INTEGER NOT NULL, " +
                Params.KEY_REPEATENDYEAR + " INTEGER NOT NULL, " +
                Params.KEY_REPEATENDMONTH + " INTEGER NOT NULL, " +
                Params.KEY_REPEATENDDAY + " INTEGER NOT NULL, " +
                Params.KEY_MAINTASK + " INTEGER NOT NULL);";

        Log.d("dbrohan","query being run is "+ create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String tmp = "drop Table if exists " + Params.TABLE_NAME;
        db.execSQL(tmp);
        onCreate(db);
    }
    public long insertSubTask(String task,int year,int month,int day,int hour,int minute,int status,int repeateEndHour,int repeateEndMinute,String priority,int RepeatEndYear,int repeateEndMonth,int repeatEndDay,int maintask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        values.put(Params.KEY_PRIORITY,priority);
        values.put(Params.KEY_REPEATENDHOUR,repeateEndHour);
        values.put(Params.KEY_REPEATENDMINUTE,repeateEndMinute);
        values.put(Params.KEY_REPEATENDYEAR,RepeatEndYear);
        values.put(Params.KEY_REPEATENDMONTH,repeateEndMonth);
        values.put(Params.KEY_REPEATENDDAY,repeatEndDay);
        values.put(Params.KEY_MAINTASK,maintask);
        long result = db.insert(Params.TABLE_NAME,null,values);
        return result;
    }
    public boolean UpdateSubtask(int id,String task,int year,int month,int day,int hour,int minute,int status,int repeateEndHour,int repeateEndMinute,String priority,int RepeatEndYear,int repeateEndMonth,int repeatEndDay,int maintask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        values.put(Params.KEY_PRIORITY,priority);
        values.put(Params.KEY_REPEATENDHOUR,repeateEndHour);
        values.put(Params.KEY_REPEATENDMINUTE,repeateEndMinute);
        values.put(Params.KEY_REPEATENDYEAR,RepeatEndYear);
        values.put(Params.KEY_REPEATENDMONTH,repeateEndMonth);
        values.put(Params.KEY_REPEATENDDAY,repeatEndDay);
        values.put(Params.KEY_MAINTASK,maintask);
        int result = db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        if(result == -1)
            return false;
        return true;
    }
    public long insertUserData(String task,int year,int month,int day,int hour,int minute,int status,int repeateEndHour,int repeateEndMinute,String priority,int RepeatEndYear,int repeateEndMonth,int repeatEndDay,int maintask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        values.put(Params.KEY_PRIORITY,priority);
        values.put(Params.KEY_REPEATENDHOUR,repeateEndHour);
        values.put(Params.KEY_REPEATENDMINUTE,repeateEndMinute);
        values.put(Params.KEY_REPEATENDYEAR,RepeatEndYear);
        values.put(Params.KEY_REPEATENDMONTH,repeateEndMonth);
        values.put(Params.KEY_REPEATENDDAY,repeatEndDay);
        values.put(Params.KEY_MAINTASK,maintask);
        long result = db.insert(Params.TABLE_NAME,null,values);
        return result;
    }
    public boolean UpdateUserData(int id,String task,int year,int month,int day,int hour,int minute,int status,int repeateEndHour,int repeateEndMinute,String priority,int RepeatEndYear,int repeateEndMonth,int repeatEndDay,int maintask){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        values.put(Params.KEY_PRIORITY,priority);
        values.put(Params.KEY_REPEATENDHOUR,repeateEndHour);
        values.put(Params.KEY_REPEATENDMINUTE,repeateEndMinute);
        values.put(Params.KEY_REPEATENDYEAR,RepeatEndYear);
        values.put(Params.KEY_REPEATENDMONTH,repeateEndMonth);
        values.put(Params.KEY_REPEATENDDAY,repeatEndDay);
        values.put(Params.KEY_MAINTASK,maintask);
        int result = db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        if(result == -1)
            return false;
        return true;
    }
    public List<String> DeleteUserData(int id){
        List<String>list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()) {
            do {
                int currid = cursor.getInt(0);
                int mainTask = cursor.getInt(14);
                if (currid == id || mainTask == id) {
                    list.add(""+currid);
                }
            } while (cursor.moveToNext());
        }
        for(int i = 0;i<list.size();i++){
            int taskId = Integer.parseInt(list.get(i));
            db.delete(Params.TABLE_NAME, Params.KEY_ID + "=?", new String[]{String.valueOf(taskId)});
        }
        db.close();
        return list;
    }
    public taskModel getParticularData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + Params.TABLE_NAME + "where id="+id,null);
        id = cursor.getInt(0);
        String task = cursor.getString(1);
        int day = cursor.getInt(2);
        int month = cursor.getInt(3);
        int year = cursor.getInt(4);
        int hour = cursor.getInt(5);
        int minute = cursor.getInt(6);
        int status = cursor.getInt(7);
        String priority = cursor.getString(8);
        int repeatEndHour = cursor.getInt(9);
        int repeateEndMinute = cursor.getInt(10);
        int repeatEndYear = cursor.getInt(11);
        int repeatEndMonth = cursor.getInt(12);
        int repeatEndDay = cursor.getInt(13);
        int check = 0;
        int maintask = cursor.getInt(14);
        taskModel t = new taskModel(task,day + "/" + month + "/" + year,hour+":"+minute,id,hour,minute,status,check,repeatEndDay,repeatEndMonth,repeatEndYear,repeatEndHour,repeateEndMinute,priority,maintask);
        return t;
    }
    public void addTask(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK, todoModel.getTask());
        values.put(Params.KEY_DAY, todoModel.getDay());
        values.put(String.valueOf(Params.KEY_STATUS), todoModel.getStatus());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbrohan","Successfully inserted ");

        db.close();
    }

    public List<taskModel> getAllTasks(){
        List<taskModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                taskModel task = new taskModel();
                int id = (cursor.getInt(0));
                //task.setId(Integer.parseInt(cursor.getString(0)));
                String title = (cursor.getString(1));
                int day = (cursor.getInt(2));
                int month = (cursor.getInt(3));
                int year = (cursor.getInt(4));
                int hour = (cursor.getInt(5));
                int minute = (cursor.getInt(6));
                int status = (cursor.getInt(7));
                String priority = cursor.getString(8);
                int repeatEndHour = cursor.getInt(9);
                int repeateEndMinute = cursor.getInt(10);
                int repeateEndYear = cursor.getInt(11);
                int repeateEndMonth = cursor.getInt(12);
                int repeateEndDay = cursor.getInt(13);
                int mainTask = cursor.getInt(14);
                if(mainTask == -1) {
                    String date = day + "/" + month + "/" + year;
                    String time = "";
                    if (hour / 10 == 0) {
                        time += "0";
                    }
                    time += hour + ":";
                    if (minute / 10 == 0) {
                        time += "0";
                    }
                    time += minute;
                    task.setId(id);
                    task.setStatus(status);
                    task.setDate(date);
                    task.setHour(hour);
                    task.setMinute(minute);
                    task.setTime(time);
                    task.setDate(date);
                    task.setTitle(title);
                    task.setRepeatEndHour(repeatEndHour);
                    task.setRepeatEndMinute(repeateEndMinute);
                    task.setPriority(priority);
                    task.setRepeateEndYear(repeateEndYear);
                    task.setRepeatEndMonth(repeateEndMonth);
                    task.setRepeatEndDay(repeateEndDay);
                    task.setMaintask(-1);
                    //task.setStatus(Integer.parseInt(cursor.getString(3)));
                    taskList.add(task);
                }
            }while (cursor.moveToNext());
        }
        return taskList;
    }
    public List<taskModel> getAllSubTasks(int maintaskid){
        List<taskModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                taskModel task = new taskModel();
                int id = (cursor.getInt(0));
                //task.setId(Integer.parseInt(cursor.getString(0)));
                String title = (cursor.getString(1));
                int day = (cursor.getInt(2));
                int month = (cursor.getInt(3));
                int year = (cursor.getInt(4));
                int hour = (cursor.getInt(5));
                int minute = (cursor.getInt(6));
                int status = (cursor.getInt(7));
                String priority = cursor.getString(8);
                int repeatEndHour = cursor.getInt(9);
                int repeateEndMinute = cursor.getInt(10);
                int repeateEndYear = cursor.getInt(11);
                int repeateEndMonth = cursor.getInt(12);
                int repeateEndDay = cursor.getInt(13);
                int maintask = cursor.getInt(14);
                if(maintask == maintaskid) {
                    String date = day + "/" + month + "/" + year;
                    String time = "";
                    if (hour / 10 == 0) {
                        time += "0";
                    }
                    time += hour + ":";
                    if (minute / 10 == 0) {
                        time += "0";
                    }
                    time += minute;
                    task.setId(id);
                    task.setStatus(status);
                    task.setDate(date);
                    task.setHour(hour);
                    task.setMinute(minute);
                    task.setTime(time);
                    task.setDate(date);
                    task.setTitle(title);
                    task.setRepeatEndHour(repeatEndHour);
                    task.setRepeatEndMinute(repeateEndMinute);
                    task.setPriority(priority);
                    task.setRepeateEndYear(repeateEndYear);
                    task.setRepeatEndMonth(repeateEndMonth);
                    task.setRepeatEndDay(repeateEndDay);
                    task.setMaintask(maintask);
                    //task.setStatus(Integer.parseInt(cursor.getString(3)));
                    taskList.add(task);
                }
            }while (cursor.moveToNext());
        }
        return taskList;
    }
    public int updateTask(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK, todoModel.getTask());
        //values.put(Params.KEY_DAY, todoModel.getDdate());
        values.put(Params.KEY_STATUS, todoModel.getStatus());

        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(todoModel.getId())});
    }

    public void deleteTaskByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteTask(TodoModel task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?", new String[]{String.valueOf(task.getId())});
    }

    public int getCount(){
        String query = "SELECT * FROM " + Params.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }
}
