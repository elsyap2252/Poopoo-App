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
    PoopDatabaseHelper dbHelper;

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
        dbHelper = new PoopDatabaseHelper(this);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // ⏱ Set waktu sekarang sebagai default
        Calendar now = Calendar.getInstance();
        setDateToNow(now);
        setTimeToNow(now);

        // 📅 Jika dipanggil dari CalendarActivity dengan tanggal tertentu
        Intent intent = getIntent();
        if (intent.hasExtra("selectedDate")) {
            String[] parts = intent.getStringExtra("selectedDate").split("/");
            if (parts.length == 3) {
                dayText.setText(parts[0]);
                monthText.setText(parts[1]);
                yearText.setText(parts[2]);
            }
        }

        // 🗓 Date Picker
        LinearLayout dateLayout = findViewById(R.id.datePickerLayout);
        dateLayout.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                dayText.setText(String.format("%02d", dayOfMonth));
                monthText.setText(String.format("%02d", month + 1));
                yearText.setText(String.valueOf(year));
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        // ⏰ Time Picker
        LinearLayout timeLayout = findViewById(R.id.timePickerLayout);
        timeLayout.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, hour, minute) -> {
                boolean isPM = hour >= 12;
                int hour12 = hour % 12;
                if (hour12 == 0) hour12 = 12;

                hourText.setText(String.format("%02d", hour12));
                minuteText.setText(String.format("%02d", minute));
                ampmText.setText(isPM ? "PM" : "AM");
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
        });

        // 🔶 Shape
        int[] shapeIds = {
                R.id.poopshape1, R.id.poopshape2, R.id.poopshape3,
                R.id.poopshape4, R.id.poopshape5, R.id.poopshape6
        };
        for (int id : shapeIds) {
            ImageButton btn = findViewById(id);
            btn.setOnClickListener(v -> {
                selectedShape = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(shapeIds);
                applyOutline(v, R.drawable.outline_rectangle);
            });
        }

        // 🔵 Color
        int[] colorIds = {
                R.id.colorYellow, R.id.colorBrown, R.id.colorRed,
                R.id.colorBlack, R.id.colorGreen
        };
        for (int id : colorIds) {
            ImageButton btn = findViewById(id);
            btn.setOnClickListener(v -> {
                selectedColor = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(colorIds);
                applyOutline(v, R.drawable.outline_circle);
            });
        }

        // 🔸 Size
        int[] sizeIds = {
                R.id.size1, R.id.size2, R.id.size3,
                R.id.size4, R.id.size5
        };
        for (int id : sizeIds) {
            ImageButton btn = findViewById(id);
            btn.setOnClickListener(v -> {
                selectedSize = getResources().getResourceEntryName(v.getId());
                resetBackgrounds(sizeIds);
                applyOutline(v, R.drawable.outline_rectangle);
            });
        }

        // 💾 Simpan log
        btnSave.setOnClickListener(v -> {
            String date = dayText.getText() + "/" + monthText.getText() + "/" + yearText.getText();
            String time = hourText.getText() + ":" + minuteText.getText() + " " + ampmText.getText();
            String notes = notesEditText.getText().toString();

            if (selectedShape.isEmpty() || selectedColor.isEmpty() || selectedSize.isEmpty()) {
                Toast.makeText(this, "Please select shape, color, and size", Toast.LENGTH_SHORT).show();
                return;
            }

            dbHelper.insertPoopLog(date, time, selectedShape, selectedColor, selectedSize, notes);
            Toast.makeText(this, "Log saved!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, CalendarActivity.class));
            finish();
        });
    }

    private void setDateToNow(Calendar now) {
        dayText.setText(String.format("%02d", now.get(Calendar.DAY_OF_MONTH)));
        monthText.setText(String.format("%02d", now.get(Calendar.MONTH) + 1));
        yearText.setText(String.valueOf(now.get(Calendar.YEAR)));
    }

    private void setTimeToNow(Calendar now) {
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        boolean isPM = hour >= 12;
        int hour12 = hour % 12;
        if (hour12 == 0) hour12 = 12;

        hourText.setText(String.format("%02d", hour12));
        minuteText.setText(String.format("%02d", minute));
        ampmText.setText(isPM ? "PM" : "AM");
    }

    private void resetBackgrounds(int[] viewIds) {
        for (int id : viewIds) {
            View view = findViewById(id);
            int resId = getOriginalBackgroundResId(id);
            if (resId != 0) {
                Drawable original = ContextCompat.getDrawable(this, resId);
                if (original != null) view.setBackground(original);
            }
        }
    }

    private void applyOutline(View view, int outlineResId) {
        int baseResId = getOriginalBackgroundResId(view.getId());
        Drawable base = ContextCompat.getDrawable(this, baseResId);
        Drawable outline = ContextCompat.getDrawable(this, outlineResId);

        if (base != null && outline != null) {
            Drawable[] layers = new Drawable[]{base, outline};
            view.setBackground(new LayerDrawable(layers));
        }
    }

    private int getOriginalBackgroundResId(int viewId) {
        // Color
        if (viewId == R.id.colorYellow) return R.drawable.color_circle_yellow;
        else if (viewId == R.id.colorBrown) return R.drawable.color_circle_brown;
        else if (viewId == R.id.colorRed) return R.drawable.color_circle_red;
        else if (viewId == R.id.colorBlack) return R.drawable.color_circle_black;
        else if (viewId == R.id.colorGreen) return R.drawable.color_circle_green;

            // Size
        else if (viewId == R.id.size1 || viewId == R.id.size2 || viewId == R.id.size3 ||
                viewId == R.id.size4 || viewId == R.id.size5) return R.drawable.size_background;

            // Shape
        else if (viewId == R.id.poopshape1) return R.drawable.shape1;
        else if (viewId == R.id.poopshape2) return R.drawable.shape2;
        else if (viewId == R.id.poopshape3) return R.drawable.shape3;
        else if (viewId == R.id.poopshape4) return R.drawable.shape4;
        else if (viewId == R.id.poopshape5) return R.drawable.shape5;
        else if (viewId == R.id.poopshape6) return R.drawable.shape6;

        // Default
        return android.R.color.transparent;
    }
    }

