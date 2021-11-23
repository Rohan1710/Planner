package com.example.android.taskplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.taskplanner.Adapter.NotesAdapter;
import com.example.android.taskplanner.Adapter.TodoAdapter;
import com.example.android.taskplanner.Data.MyDBHandler;
import com.example.android.taskplanner.Data.MyNoteHandler;
import com.example.android.taskplanner.Model.NoteModel;
import com.example.android.taskplanner.Model.taskModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NotesSection extends AppCompatActivity {

    public RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private List<NoteModel> noteList;
    private EditText noteContent;
    private Button noteButton;
    String ndate, ntime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_section);

        noteContent = findViewById(R.id.noteText);
        noteButton = findViewById(R.id.NoteButton);
        Calendar calendar = Calendar.getInstance();

        MyNoteHandler db = new MyNoteHandler(NotesSection.this);

        noteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String notetext = noteContent.getText().toString();
                if(notetext.isEmpty()) {
                    Toast.makeText(NotesSection.this, R.string.empty_input, Toast.LENGTH_LONG).show();
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                ndate = dateFormat.format(calendar.getTime());

                dateFormat = new SimpleDateFormat("HH:mm:ss");
                ntime = dateFormat.format(calendar.getTime());

                long rpb = db.insertNoteData(notetext,ndate, ntime);
                Toast.makeText(NotesSection.this, "New Note Added", Toast.LENGTH_SHORT).show();
            }
        });

        noteList = new ArrayList<>();

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(manager);
        notesRecyclerView.setHasFixedSize(true);


        List<NoteModel> allNotes = db.getAllNotes();
        Log.d("dbrohan", "all notes" + allNotes.size());
        for(NoteModel tmp : allNotes){
            noteList.add(tmp);
        }

        Log.d("dbrohan", "all notes" + noteList.size());

        notesAdapter = new NotesAdapter(noteList,NotesSection.this);

        if(noteList.size()>=1)
            notesRecyclerView.setAdapter(notesAdapter);
    }
}