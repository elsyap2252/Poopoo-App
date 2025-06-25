package com.example.poopoo_app;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class AddPoopActivity extends AppCompatActivity {

    TextView dayText, monthText, yearText, hourText, minuteText, ampmText;
    EditText notesEditText;
    Button btnSave;

    // Untuk menyimpan pilihan
    String selectedShape = "";
    String selectedColor = "";
    String selectedSize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_poop);

        // Inisialisasi komponen
        dayText = findViewById(R.id.dayText);
        monthText = findViewById(R.id.monthText);
        yearText = findViewById(R.id.yearText);
        hourText = findViewById(R.id.hourText);
        minuteText = findViewById(R.id.minuteText);
        ampmText = findViewById(R.id.ampmText);
        notesEditText = findViewById(R.id.notesEditText);
        btnSave = findViewById(R.id.btnSavePoo);

        LinearLayout dateLayout = findViewById(R.id.datePickerLayout);
        LinearLayout timeLayout = findViewById(R.id.timePickerLayout);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // Pilih tanggal
        dateLayout.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay) -> {
                dayText.setText(String.format("%02d", selectedDay));
                monthText.setText(String.format("%02d", selectedMonth + 1));
                yearText.setText(String.valueOf(selectedYear));
            }, year, month, day).show();
        });

        // Pilih waktu
        timeLayout.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            new TimePickerDialog(this, (view, selectedHour, selectedMinute) -> {
                boolean isPM = selectedHour >= 12;
                int hour12 = selectedHour % 12;
                if (hour12 == 0) hour12 = 12;

                hourText.setText(String.format("%02d", hour12));
                minuteText.setText(String.format("%02d", selectedMinute));
                ampmText.setText(isPM ? "PM" : "AM");
            }, hour, minute, false).show();
        });

        // Shape
        int[] shapeIds = {
                R.id.poopshape1, R.id.poopshape2, R.id.poopshape3,
                R.id.poopshape4, R.id.poopshape5, R.id.poopshape6
        };
        for (int id : shapeIds) {
            ImageButton shapeButton = findViewById(id);
            shapeButton.setOnClickListener(v -> {
                selectedShape = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(shapeIds);
                applyOutline(v, R.drawable.outline_rectangle);
            });
        }

        // Color
        int[] colorIds = {
                R.id.colorYellow, R.id.colorBrown, R.id.colorRed,
                R.id.colorBlack, R.id.colorGreen
        };
        for (int id : colorIds) {
            ImageButton colorButton = findViewById(id);
            colorButton.setOnClickListener(v -> {
                selectedColor = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(colorIds);
                applyOutline(v, R.drawable.outline_circle);
            });
        }

        // Size
        int[] sizeIds = {
                R.id.size1, R.id.size2, R.id.size3,
                R.id.size4, R.id.size5
        };
        for (int id : sizeIds) {
            ImageButton sizeButton = findViewById(id);
            sizeButton.setOnClickListener(v -> {
                selectedSize = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(sizeIds);
                applyOutline(v, R.drawable.outline_rectangle);
            });
        }

        // Tombol Simpan
        btnSave.setOnClickListener(v -> {
            String date = dayText.getText() + "/" + monthText.getText() + "/" + yearText.getText();
            String time = hourText.getText() + ":" + minuteText.getText() + " " + ampmText.getText();
            String notes = notesEditText.getText().toString();

            if (selectedShape.isEmpty() || selectedColor.isEmpty() || selectedSize.isEmpty()) {
                Toast.makeText(this, "Please select shape, color, and size", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Saved!\nDate: " + date + "\nTime: " + time +
                    "\nShape: " + selectedShape + "\nColor: " + selectedColor + "\nSize: " + selectedSize +
                    "\nNotes: " + notes, Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, CalendarActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void resetBackgrounds(int[] viewIds) {
        for (int id : viewIds) {
            View view = findViewById(id);
            Drawable original = ContextCompat.getDrawable(this, getOriginalBackgroundResId(view.getId()));
            view.setBackground(original);
        }
    }

    private void applyOutline(View view, int outlineResId) {
        Drawable base = ContextCompat.getDrawable(this, getOriginalBackgroundResId(view.getId()));
        Drawable outline = ContextCompat.getDrawable(this, outlineResId);
        Drawable[] layers = new Drawable[]{base, outline};
        LayerDrawable layered = new LayerDrawable(layers);
        view.setBackground(layered);
    }

    private int getOriginalBackgroundResId(int viewId) {
        if (viewId == R.id.colorYellow) return R.drawable.color_circle_yellow;
            // Colors
        else if (viewId == R.id.colorBrown) return R.drawable.color_circle_brown;
        else if (viewId == R.id.colorRed) return R.drawable.color_circle_red;
        else if (viewId == R.id.colorBlack) return R.drawable.color_circle_black;
        else if (viewId == R.id.colorGreen) return R.drawable.color_circle_green;

        if (viewId == R.id.size1) return R.drawable.size_background;
        else if (viewId == R.id.size2) return R.drawable.size_background;
        else if (viewId == R.id.size3) return R.drawable.size_background;
        else if (viewId == R.id.size4) return R.drawable.size_background;
        else if (viewId == R.id.size5) return R.drawable.size_background;

        if (viewId == R.id.poopshape1) return R.drawable.shape1;
        else if (viewId == R.id.poopshape2) return R.drawable.shape2;
        else if (viewId == R.id.poopshape3) return R.drawable.shape3;
        else if (viewId == R.id.poopshape4) return R.drawable.shape4;
        else if (viewId == R.id.poopshape5) return R.drawable.shape5;
        else if (viewId == R.id.poopshape6) return R.drawable.shape6;

            return android.R.color.transparent;

    }
}