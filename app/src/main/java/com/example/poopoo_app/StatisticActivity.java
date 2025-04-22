package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

public class StatisticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);

        // Menetapkan item yang dipilih di navbar saat ini
        bottomNavigationView.setSelectedItemId(R.id.nav_statistic); // Menandakan Statistic sebagai halaman yang sedang dibuka

        // Menambahkan listener untuk perpindahan antar halaman
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = null;

                // Menangani navigasi berdasarkan item yang dipilih
                if (item.getItemId() == R.id.nav_calendar) {
                    intent = new Intent(StatisticActivity.this, CalendarActivity.class);
                } else if (item.getItemId() == R.id.nav_alarm) {
                    intent = new Intent(StatisticActivity.this, AlarmActivity.class);
                } else if (item.getItemId() == R.id.nav_statistic) {
                    // Sudah berada di halaman Statistic, jadi tidak perlu pindah
                    return true;
                }

                // Jika ada intent, lakukan navigasi
                if (intent != null) {
                    startActivity(intent);
                    finish(); // Menutup activity sebelumnya
                    return true;
                }
                return false;
            }
        });
    }
}
