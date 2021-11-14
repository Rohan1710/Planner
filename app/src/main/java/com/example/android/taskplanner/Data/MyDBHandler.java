package com.example.android.taskplanner.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Parameters.Params;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public MyDBHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " CREATE TABLE " + Params.TABLE_NAME + " (" +
                Params.KEY_ID + " INTEGER PRIMARY KEY, " +
                Params.KEY_TASK + " TEXT NOT NULL, " +
                Params.KEY_DATE + " TEXT NOT NULL, " +
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

    public void openDatabase() {
        db = this.getWritableDatabase();
    }

    public void addTask(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK, todoModel.getTask());
        values.put(Params.KEY_DATE, todoModel.getDdate());
        values.put(String.valueOf(Params.KEY_STATUS), todoModel.getStatus());

        db.insert(Params.TABLE_NAME, null, values);
        Log.d("dbrohan","Successfully inserted ");

        db.close();
    }

    public List<TodoModel> getAllTasks(){
        List<TodoModel> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                TodoModel task = new TodoModel();
                task.setId(Integer.parseInt(cursor.getString(0)));
                task.setTask(cursor.getString(1));
                task.setDdate(cursor.getString(2));
                task.setStatus(Integer.parseInt(cursor.getString(3)));
                taskList.add(task);
            }while (cursor.moveToNext());
        }
        return taskList;
    }

    public int updateTask(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK, todoModel.getTask());
        values.put(Params.KEY_DATE, todoModel.getDdate());
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
