package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EditPoopActivity extends AppCompatActivity {

    private EditText timeEditText, shapeEditText, colorEditText, sizeEditText, notesEditText;
    private Button saveButton, deleteButton;
    private PoopDatabaseHelper dbHelper;
    private PoopLog currentLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poop); // Buat layout ini ya

        dbHelper = new PoopDatabaseHelper(this);

        timeEditText = findViewById(R.id.editTime);
        shapeEditText = findViewById(R.id.editShape);
        colorEditText = findViewById(R.id.editColor);
        sizeEditText = findViewById(R.id.editSize);
        notesEditText = findViewById(R.id.editNotes);
        saveButton = findViewById(R.id.btnUpdate);
        deleteButton = findViewById(R.id.btnDelete);

        // Ambil log dari intent
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String shape = intent.getStringExtra("shape");
        String color = intent.getStringExtra("color");
        String size = intent.getStringExtra("size");
        String notes = intent.getStringExtra("notes");

        currentLog = new PoopLog(id, date, time, shape, color, size, notes);

        timeEditText.setText(time);
        shapeEditText.setText(shape);
        colorEditText.setText(color);
        sizeEditText.setText(size);
        notesEditText.setText(notes);

        saveButton.setOnClickListener(v -> {
            currentLog.setTime(timeEditText.getText().toString());
            currentLog.setShape(shapeEditText.getText().toString());
            currentLog.setColor(colorEditText.getText().toString());
            currentLog.setSize(sizeEditText.getText().toString());
            currentLog.setNotes(notesEditText.getText().toString());

            dbHelper.updatePoopLog(currentLog);
            Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
            finish();
        });

        deleteButton.setOnClickListener(v -> {
            dbHelper.deletePoopLog(currentLog.getId());
            Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
