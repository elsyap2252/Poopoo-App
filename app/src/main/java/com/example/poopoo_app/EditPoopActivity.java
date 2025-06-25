package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EditPoopActivity extends AppCompatActivity {

    private TextView dayText, monthText, yearText, hourText, minuteText, ampmText;
    private EditText notesEditText;
    private String selectedShape = "", selectedColor = "", selectedSize = "";
    private int poopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_poop);

        // Inisialisasi View
        dayText = findViewById(R.id.dayText);
        monthText = findViewById(R.id.monthText);
        yearText = findViewById(R.id.yearText);
        hourText = findViewById(R.id.hourText);
        minuteText = findViewById(R.id.minuteText);
        ampmText = findViewById(R.id.ampmText);
        notesEditText = findViewById(R.id.notesEditText);
        Button btnSavePoo = findViewById(R.id.btnSavePoo);

        // Ambil data dari Intent
        Intent intent = getIntent();
        poopId = intent.getIntExtra("id", -1);
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        selectedShape = intent.getStringExtra("shape");
        selectedColor = intent.getStringExtra("color");
        selectedSize = intent.getStringExtra("size");
        String notes = intent.getStringExtra("notes");

        // Set tanggal
        if (date != null && date.split("/").length == 3) {
            String[] parts = date.split("/");
            dayText.setText(parts[0]);
            monthText.setText(parts[1]);
            yearText.setText(parts[2]);
        }

        // Set waktu
        if (time != null) {
            time = time.trim();
            String[] parts = time.split(":");
            if (parts.length == 2) {
                int hour = Integer.parseInt(parts[0].trim());
                String[] minParts = parts[1].trim().split(" ");
                int minute = Integer.parseInt(minParts[0].trim());
                String ampm = (minParts.length > 1) ? minParts[1].toUpperCase() : "AM";

                int hour12 = hour;
                if (ampm.equals("PM") && hour > 12) hour12 = hour - 12;
                else if (ampm.equals("AM") && hour == 0) hour12 = 12;

                hourText.setText(String.format("%02d", hour12));
                minuteText.setText(String.format("%02d", minute));
                ampmText.setText(ampm);
            }
        }

        notesEditText.setText(notes);
        setShapeSelected(selectedShape);
        setColorSelected(selectedColor);
        setSizeSelected(selectedSize);

        btnSavePoo.setOnClickListener(v -> {
            String updatedNotes = notesEditText.getText().toString();
            String newTime = hourText.getText() + ":" + minuteText.getText() + " " + ampmText.getText();
            String newDate = dayText.getText() + "/" + monthText.getText() + "/" + yearText.getText();

            PoopLog updatedLog = new PoopLog(poopId, newDate, newTime, selectedShape, selectedColor, selectedSize, updatedNotes);
            PoopDatabaseHelper db = new PoopDatabaseHelper(this);
            db.updatePoopLog(updatedLog);

            Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        });

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setShapeSelected(String shape) {
        int[] shapeIds = {R.id.poopshape1, R.id.poopshape2, R.id.poopshape3, R.id.poopshape4, R.id.poopshape5, R.id.poopshape6};
        for (int id : shapeIds) {
            ImageButton btn = findViewById(id);
            String desc = btn.getContentDescription().toString();
            if (desc.equals(shape)) {
                btn.setBackgroundResource(R.drawable.shape_selected_border);
                selectedShape = desc;
            } else {
                btn.setBackgroundResource(getDrawableIdForShape(desc));
            }
        }
    }

    private int getDrawableIdForShape(String shapeName) {
        switch (shapeName) {
            case "shape1": return R.drawable.shape1;
            case "shape2": return R.drawable.shape2;
            case "shape3": return R.drawable.shape3;
            case "shape4": return R.drawable.shape4;
            case "shape5": return R.drawable.shape5;
            case "shape6": return R.drawable.shape6;
            default: return R.drawable.shape1;
        }
    }

    private void setColorSelected(String color) {
        int[] colorIds = {R.id.colorYellow, R.id.colorBrown, R.id.colorRed, R.id.colorBlack, R.id.colorGreen};
        for (int id : colorIds) {
            ImageButton btn = findViewById(id);
            String desc = btn.getContentDescription().toString().toLowerCase();
            if (desc.equals(color.toLowerCase())) {
                btn.setBackgroundResource(R.drawable.selected_outline);
                selectedColor = desc;
            } else {
                btn.setBackgroundResource(getColorDrawable(id));
            }
        }
    }

    private int getColorDrawable(int id) {
        if (id == R.id.colorYellow) return R.drawable.color_circle_yellow;
        else if (id == R.id.colorBrown) return R.drawable.color_circle_brown;
        else if (id == R.id.colorRed) return R.drawable.color_circle_red;
        else if (id == R.id.colorBlack) return R.drawable.color_circle_black;
        else if (id == R.id.colorGreen) return R.drawable.color_circle_green;
        else return R.drawable.color_circle_brown;
    }

    private void setSizeSelected(String size) {
        int[] sizeIds = {R.id.size1, R.id.size2, R.id.size3, R.id.size4, R.id.size5};
        for (int i = 0; i < sizeIds.length; i++) {
            ImageButton btn = findViewById(sizeIds[i]);
            String current = "size" + (i + 1);
            if (current.equals(size)) {
                btn.setBackgroundResource(R.drawable.selected_outline);
                selectedSize = current;
            } else {
                btn.setBackgroundColor(0xFFCCCCCC);
            }
        }
    }
}
