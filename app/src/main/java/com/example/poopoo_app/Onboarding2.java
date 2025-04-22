package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Onboarding2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding2);

        Button btnNext = findViewById(R.id.btnMulai);
        btnNext.setOnClickListener(v -> {
            Intent intent = new Intent(Onboarding2.this, Onboarding3.class);
            startActivity(intent);
        });
    }
}
