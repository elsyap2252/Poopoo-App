package com.example.poopoo_app;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import android.graphics.Color;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;


public class StatisticActivity extends AppCompatActivity {
    private BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        // ========== Bar Chart ==========
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1, 10)); // jam 4
        barEntries.add(new BarEntry(2, 20)); // jam 8
        barEntries.add(new BarEntry(3, 5));  // jam 12
        barEntries.add(new BarEntry(4, 15)); // jam 16
        barEntries.add(new BarEntry(5, 0));  // jam 20

        BarDataSet barDataSet = new BarDataSet(barEntries, "Time of Day");
        barDataSet.setColor(Color.parseColor("#03DAC5"));
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(1000);

// ========== Pie Chart ==========
        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry(50f, "Type 2"));
        pieEntries.add(new PieEntry(30f, "Type 3"));
        pieEntries.add(new PieEntry(20f, "Type 4"));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, "");
        pieDataSet.setColors(new int[]{
                Color.parseColor("#A28BD4"),
                Color.parseColor("#F4A896"),
                Color.parseColor("#C7F2A4")
        });
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);

        // Menetapkan item yang dipilih di navbar saat ini
        bottomNavigationView.setSelectedItemId(R.id.nav_statistic); // Menandakan Statistic sebagai halaman yang sedang dibuka

        // Menambahkan listener untuk perpindahan antar halaman
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = null;

                // Menangani navigasi berdasarkan item yang dipilih
                // Menangani navigasi berdasarkan item yang dipilih
                if (item.getItemId() == R.id.nav_calendar) {
                    intent = new Intent(StatisticActivity.this, CalendarActivity.class);
                } else if (item.getItemId() == R.id.nav_statistic) {
                    // Sudah berada di halaman Statistic, jadi tidak perlu pindah
                    return true;
                } else {
                    return false;
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
