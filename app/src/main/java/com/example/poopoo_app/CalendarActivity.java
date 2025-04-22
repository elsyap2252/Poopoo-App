package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        ImageButton btnSettings = findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        Button btnAddPoop = findViewById(R.id.btnAddEntry);
        btnAddPoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, AddPoopActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            // Periksa item yang dipilih di BottomNavigationView menggunakan if
            if (item.getItemId() == R.id.nav_calendar) {
                return true;
            } else if (item.getItemId() == R.id.nav_statistic) {
                intent = new Intent(CalendarActivity.this, StatisticActivity.class);
            } else if (item.getItemId() == R.id.nav_alarm) {
                intent = new Intent(CalendarActivity.this, AlarmActivity.class);
            }
            // Jika ada intent, mulai activity terkait
            if (intent != null) {
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
    }
}