package com.example.android.taskplanner.Adapter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Data.MyNoteHandler;
import com.example.android.taskplanner.Model.NoteModel;
import com.example.android.taskplanner.Model.taskModel;
import com.example.android.taskplanner.R;
import com.example.android.taskplanner.SubTask;
import com.example.android.taskplanner.myAlarm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder>{
    private List<NoteModel> noteList,noteListAll;
    static Context context;

    public NotesAdapter(List<NoteModel> list, Context context){
        this.noteList = list;
        this.noteListAll = new ArrayList<>(noteList);
        this.context = context;
    }

    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.notes_cardview, parent, false);
        return new NotesAdapter.ViewHolder(itemView);
    }

    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        NoteModel item = noteList.get(position);
        holder.notetext.setText(item.getNtext());
        holder.date.setText(item.getNdate());
        holder.time.setText(item.getNtime());
        holder.id.setText(""+item.getNid());
    }

    public int getItemCount(){
        return noteList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView notetext,date,time,id;
        ImageView delete;
        ViewHolder(View view){
            super(view);
            notetext = view.findViewById(R.id.NoteContent);
            date = view.findViewById(R.id.NoteDate);
            time = view.findViewById(R.id.NoteTime);
            id = view.findViewById(R.id.NoteID);
            delete = view.findViewById(R.id.deleteNoteIcon);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Delete Note");
                    builder.setMessage("Are You Sure to Delete this Note");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int intentId = Integer.parseInt(id.getText().toString());
                            MyNoteHandler db = new MyNoteHandler(context);
                            List<String>list = db.DeleteNoteData(intentId);
                            Toast.makeText(context,"Task Deleted Successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    if(!(context instanceof Activity && ((Activity) context).isFinishing()))
                        builder.create().show();
                }
            });
        }
    }
}