package com.example.poopoo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Onboarding3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding3);

        Button btnNext = findViewById(R.id.btnMulai);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding3.this, CalendarActivity.class);
            SharedPreferences prefs = getSharedPreferences("PoopooPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("onboarding_completed", true);
            editor.apply();

            startActivity(intent);
            finish(); // supaya onboarding3 gak bisa di-back lagi
        });
    }
}
