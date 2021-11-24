package com.example.android.taskplanner.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.taskplanner.Model.NoteModel;
import com.example.android.taskplanner.Model.TodoModel;
import com.example.android.taskplanner.Model.taskModel;
import com.example.android.taskplanner.Parameters.Params;
import com.example.android.taskplanner.Parameters.ParamsNotes;

import java.util.ArrayList;
import java.util.List;

public class MyNoteHandler extends SQLiteOpenHelper {
    public MyNoteHandler(Context context) {
        super(context, ParamsNotes.DB1_NAME, null, ParamsNotes.DB1_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = " CREATE TABLE " + ParamsNotes.NOTE_TABLE_NAME + " (" +
                ParamsNotes.KEY_NOTE_ID + " INTEGER PRIMARY KEY, " +
                ParamsNotes.KEY_NOTE_TASK + " TEXT NOT NULL, " +
                ParamsNotes.KEY_NOTE_DATE+ " TEXT NOT NULL, " +
                ParamsNotes.KEY_NOTE_TIME + " TEXT NOT NULL);";
        Log.d("dbrohan","query being run is "+ create);
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String tmp = "drop Table if exists " + ParamsNotes.NOTE_TABLE_NAME;
        db.execSQL(tmp);
        onCreate(db);
    }

    public long insertNoteData(String note,String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ParamsNotes.KEY_NOTE_TASK,note);
        values.put(ParamsNotes.KEY_NOTE_DATE,date);
        values.put(ParamsNotes.KEY_NOTE_TIME,time);
        long result = db.insert(ParamsNotes.NOTE_TABLE_NAME,null,values);
        Log.d("dbrohan","Added to database");
        return result;
    }
    public boolean UpdateNoteData(int id,String note,String date, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ParamsNotes.KEY_NOTE_TASK,note);
        values.put(ParamsNotes.KEY_NOTE_DATE,date);
        values.put(ParamsNotes.KEY_NOTE_TIME,time);
        int result = db.update(ParamsNotes.NOTE_TABLE_NAME, values, ParamsNotes.KEY_NOTE_ID + "=?",
                new String[]{String.valueOf(id)});
        if(result == -1)
            return false;
        return true;
    }

    public List<String> DeleteNoteData(int id){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + ParamsNotes.NOTE_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()) {
            do {
                int currid = cursor.getInt(0);
                if (currid == id) {
                    list.add(""+currid);
                }
            } while (cursor.moveToNext());
        }
        for(int i = 0;i<list.size();i++){
            int taskId = Integer.parseInt(list.get(i));
            db.delete(ParamsNotes.NOTE_TABLE_NAME, ParamsNotes.KEY_NOTE_ID + "=?", new String[]{String.valueOf(taskId)});
        }
        db.close();
        return list;
    }

    /*public taskModel getParticularData(int id){
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
    }*/

    /*public void addNote(NoteModel noteModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ParamsNotes.KEY_NOTE_TASK, noteModel.getNtext());
        values.put(ParamsNotes.KEY_NOTE_DAY, noteModel.getNday());

        db.insert(ParamsNotes.NOTE_TABLE_NAME, null, values);
        Log.d("dbrohan","Successfully inserted ");

        db.close();
    }
    */
    public List<NoteModel> getAllNotes(){
        List<NoteModel> noteList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + ParamsNotes.NOTE_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                NoteModel task = new NoteModel();
                int id = (cursor.getInt(0));
                //task.setId(Integer.parseInt(cursor.getString(0)));
                String note = (cursor.getString(1));
                String date = (cursor.getString(2));
                String time = (cursor.getString(3));
                task.setNid(id);
                task.setNtext(note);
                task.setNdate(date);
                task.setNtime(time);
                noteList.add(task);
            }while (cursor.moveToNext());
        }
        return noteList;
    }

    /*public int updateTask(TodoModel todoModel){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Params.KEY_TASK, todoModel.getTask());
        //values.put(Params.KEY_DAY, todoModel.getDdate());
        values.put(Params.KEY_STATUS, todoModel.getStatus());

        return db.update(Params.TABLE_NAME, values, Params.KEY_ID + "=?",
                new String[]{String.valueOf(todoModel.getId())});
    }*/

    public void deleteNoteByID(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ParamsNotes.NOTE_TABLE_NAME,ParamsNotes.KEY_NOTE_ID+"=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteNote(NoteModel note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ParamsNotes.NOTE_TABLE_NAME,ParamsNotes.KEY_NOTE_ID+"=?", new String[]{String.valueOf(note.getNid())});
    }

    public int getCount(){
        String query = "SELECT * FROM " + ParamsNotes.NOTE_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        return cursor.getCount();
    }
}
