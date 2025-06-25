package com.example.poopoo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.poopoo_app.R;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "PoopooPrefs";
    private static final String ONBOARDING_COMPLETED = "onboarding_completed";

    // ... bagian atas tetap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean onboardingDone = prefs.getBoolean(ONBOARDING_COMPLETED, false);

        if (!onboardingDone) {
            startActivity(new Intent(MainActivity.this, Onboarding1.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);
        BottomNavigationView navbar = findViewById(R.id.navbar);
        loadFragment(new AlarmFragment());
        navbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(android.view.MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.menu_calendar) {
                    fragment = new CalendarFragment();
                } else if (itemId == R.id.menu_statistic) {
                    fragment = new StatisticFragment();
                } else if (itemId == R.id.menu_alarm) {
                    fragment = new AlarmFragment();
                }

                if (fragment != null) {
                    loadFragment(fragment);
                    return true;
                }
                return false;
            }
        });
    }
    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}