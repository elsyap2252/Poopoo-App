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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.poopoo_app.EventDecorator;
import android.graphics.Color;

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

        // Tombol Setting
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        // Tombol Add Entry
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

        // Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            if (item.getItemId() == R.id.nav_calendar) {
                return true;
            } else if (item.getItemId() == R.id.nav_statistic) {
                intent = new Intent(CalendarActivity.this, StatisticActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });

        // Calendar Setup
        calendarView = findViewById(R.id.calendarView);
        calendarView.setSelectedDate(CalendarDay.today());

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            String selectedDate = String.format("%02d/%02d/%04d",
                    date.getDay(), date.getMonth() + 1, date.getYear());
            loadPoopLogsByDate(selectedDate);
        });

        // RecyclerView Setup
        poopLogRecyclerView = findViewById(R.id.poopLogRecyclerView);
        poopLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load data awal
        loadPoopLogsAll();

        // Tambahkan titik indikator untuk tanggal yang memiliki data poop
        addPoopIndicatorsToCalendar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        addPoopIndicatorsToCalendar(); // Refresh indikator titik
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

    private void addPoopIndicatorsToCalendar() {
        List<PoopLog> allLogs = dbHelper.getAllPoopLogs();
        HashSet<CalendarDay> eventDates = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (PoopLog log : allLogs) {
            try {
                Date date = sdf.parse(log.getDate());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                CalendarDay calendarDay = CalendarDay.from(cal);
                eventDates.add(calendarDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        calendarView.addDecorator(new EventDecorator(android.graphics.Color.parseColor("#018786"), eventDates));
    }
}