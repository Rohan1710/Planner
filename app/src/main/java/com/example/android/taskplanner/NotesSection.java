package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.android.taskplanner.Adapter.NotesAdapter;
import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Data.MyNoteHandler;
import com.example.android.taskplanner.Model.NoteModel;
import com.example.android.taskplanner.Model.taskModel;

import java.util.ArrayList;
import java.util.List;

public class NotesSection extends AppCompatActivity {

    public RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private List<NoteModel> noteList;
    private TextView noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_section);

        noteContent = findViewById(R.id.NoteContent);

        noteList = new ArrayList<>();

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(manager);
        notesRecyclerView.setHasFixedSize(true);

        MyNoteHandler db = new MyNoteHandler(NotesSection.this);
        List<NoteModel> allNotes = db.getAllNotes();
        //Toast.makeText(this,"Tasks are " + allTask.size(),Toast.LENGTH_SHORT).show();
        for(NoteModel tmp : allNotes){
            noteList.add(tmp);
        }
        notesAdapter = new NotesAdapter(noteList,NotesSection.this);
        if(noteList.size()>=1)
            notesRecyclerView.setAdapter(notesAdapter);
//        notesAdapter.getFilter().filter(simpleDateFormat);
    }
}