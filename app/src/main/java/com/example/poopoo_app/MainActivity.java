package com.example.poopoo_app;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Selalu masuk ke onboarding1 saat aplikasi dibuka
        startActivity(new Intent(MainActivity.this, Onboarding1.class));
        finish(); // Supaya MainActivity langsung selesai dan ga bisa di-back
    }
}
