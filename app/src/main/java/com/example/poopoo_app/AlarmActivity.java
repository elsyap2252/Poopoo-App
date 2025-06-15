package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlarmActivity extends AppCompatActivity {
    private Switch switchEnable;
    private Button btnEdit;
    private TextView textTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_alarm);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = null;

                if (item.getItemId() == R.id.nav_calendar) {
                    intent = new Intent(AlarmActivity.this, CalendarActivity.class);
                } else if (item.getItemId() == R.id.nav_statistic) {
                    intent = new Intent(AlarmActivity.this, StatisticActivity.class);
                } else if (item.getItemId() == R.id.nav_alarm) {
                    return true;
                }

                if (intent != null) {
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        switchEnable = findViewById(R.id.switchEnable);
        btnEdit = findViewById(R.id.buttonEditAlarm);
        textTime = findViewById(R.id.textAlarmTime);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlarmActivity.this, EditAlarmActivity.class);
                startActivity(intent);
            }
        });
    }
}