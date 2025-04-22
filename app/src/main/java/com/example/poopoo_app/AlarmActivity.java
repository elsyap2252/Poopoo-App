package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class AlarmActivity extends AppCompatActivity {
    private NumberPicker numberPickerDays;
    private EditText messageReminder;
    private Switch switchEnable;
    private Button btnEdit, btnAm, btnPm;
    private TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        // Menetapkan BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);

        // Menetapkan item yang dipilih di navbar saat ini
        bottomNavigationView.setSelectedItemId(R.id.nav_alarm);  // Menandakan Alarm sebagai halaman yang sedang dibuka

        // Set OnNavigationItemSelectedListener untuk menangani perpindahan antar halaman
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = null;

                // Cek item yang dipilih
                if (item.getItemId() == R.id.nav_calendar) {
                    intent = new Intent(AlarmActivity.this, CalendarActivity.class);
                } else if (item.getItemId() == R.id.nav_statistic) {
                    intent = new Intent(AlarmActivity.this, StatisticActivity.class);
                } else if (item.getItemId() == R.id.nav_alarm) {
                    // Sudah di AlarmActivity, tidak perlu berpindah
                    return true;
                }

                // Jika intent tidak null, pindah ke activity yang sesuai
                if (intent != null) {
                    startActivity(intent);  // Mulai Activity baru
                    return true;
                }
                return false;
            }
        });

        // Inisialisasi view
        numberPickerDays = findViewById(R.id.numberPickerDays);
        messageReminder = findViewById(R.id.messageReminder);
        switchEnable = findViewById(R.id.switchEnable);
        btnEdit = findViewById(R.id.btnEdit);
        btnAm = findViewById(R.id.btnAm);
        btnPm = findViewById(R.id.btnPm);
        textTime = findViewById(R.id.textTime);

        // Setup picker
        numberPickerDays.setMinValue(1);
        numberPickerDays.setMaxValue(28);
        numberPickerDays.setValue(3);
        numberPickerDays.setWrapSelectorWheel(true);

        // Button AM dan PM
        btnAm.setOnClickListener(v -> {
            btnAm.setEnabled(false);
            btnPm.setEnabled(true);
        });

        btnPm.setOnClickListener(v -> {
            btnPm.setEnabled(false);
            btnAm.setEnabled(true);
        });
    }
}