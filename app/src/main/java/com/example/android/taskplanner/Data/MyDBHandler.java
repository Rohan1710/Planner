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
                Params.KEY_STATUS + " INTEGER NOT NULL);";

        Log.d("dbrohan","query being run is "+ create);
        db.execSQL(create);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String tmp = "drop Table if exists " + Params.TABLE_NAME;
        db.execSQL(tmp);
        onCreate(db);
    }

    public long insertUserData(String task,int year,int month,int day,int hour,int minute,int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        long result = db.insert(Params.TABLE_NAME,null,values);
        return result;
    }
    public boolean UpdateUserData(int id,String task,int year,int month,int day,int hour,int minute,int status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK,task);
        values.put(Params.KEY_YEAR,year);
        values.put(Params.KEY_MONTH,month);
        values.put(Params.KEY_DAY,day);
        values.put(Params.KEY_HOUR,hour);
        values.put(Params.KEY_MINUTE,minute);
        values.put(Params.KEY_STATUS,status);
        int result = db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(id)});
        if(result == -1)
            return false;
        return true;
    }
    public boolean DeleteUserData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(Params.TABLE_NAME,Params.KEY_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
        if(result == -1)
            return false;
        return true;
    }
    public TodoModel getParticularData(int id){
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
        TodoModel t = new TodoModel(id,task,day,month,year,hour,minute,status);
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
                String date = day + "/" + month + "/" + year;
                String time = "";
                if(hour/10 == 0)
                {
                    time += "0";
                }
                time += hour + ":";
                if(minute/10 == 0)
                {
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
                //task.setStatus(Integer.parseInt(cursor.getString(3)));
                taskList.add(task);
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
