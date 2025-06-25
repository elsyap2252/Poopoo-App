package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private RecyclerView poopLogRecyclerView;
    private PoopLogAdapter poopLogAdapter;
    private PoopDatabaseHelper dbHelper;
    private MaterialCalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dbHelper = new PoopDatabaseHelper(this);

        // Bottom nav & tombol
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        Button btnAddPoop = findViewById(R.id.btnAddEntry);
        btnAddPoop.setOnClickListener(v -> {
            CalendarDay selectedDay = calendarView.getSelectedDate();
            Intent intent = new Intent(CalendarActivity.this, AddPoopActivity.class);

            if (selectedDay != null) {
                String selectedDate = String.format("%02d/%02d/%04d",
                        selectedDay.getDay(), selectedDay.getMonth() + 1, selectedDay.getYear());
                intent.putExtra("selected_date", selectedDate);
            }

            startActivity(intent);
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            if (item.getItemId() == R.id.nav_calendar) {
                return true;
            } else if (item.getItemId() == R.id.nav_statistic) {
                intent = new Intent(CalendarActivity.this, StatisticActivity.class);
            } else {
                return false;
            }

            if (intent != null) {
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        // Calendar logic
        calendarView = findViewById(R.id.calendarView);
        calendarView.setSelectedDate(CalendarDay.today()); // ✅ Default: hari ini

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String selectedDate = String.format("%02d/%02d/%04d",
                    date.getDay(), date.getMonth() + 1, date.getYear());
            loadPoopLogsByDate(selectedDate);
        });

        poopLogRecyclerView = findViewById(R.id.poopLogRecyclerView);
        poopLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data awal (semua)
        loadPoopLogsAll();
    }

    private void loadPoopLogsByDate(String selectedDate) {
        List<PoopLog> logs = dbHelper.getLogsByDate(selectedDate);
        poopLogAdapter = new PoopLogAdapter(logs);
        poopLogRecyclerView.setAdapter(poopLogAdapter);
    }

    private void loadPoopLogsAll() {
        List<PoopLog> logs = dbHelper.getAllPoopLogs();
        poopLogAdapter = new PoopLogAdapter(logs);
        poopLogRecyclerView.setAdapter(poopLogAdapter);
    }
}