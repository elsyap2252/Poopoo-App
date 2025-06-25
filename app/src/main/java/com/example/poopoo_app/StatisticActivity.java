package com.example.poopoo_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

public class StatisticActivity extends AppCompatActivity {

    private BarChart barChart;
    private PieChart pieChartShape, pieChartSize, pieChartColor;
    private PoopDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        // Init
        dbHelper = new PoopDatabaseHelper(this);
        barChart = findViewById(R.id.barChart);
        pieChartShape = findViewById(R.id.pieChartShape);
        pieChartSize = findViewById(R.id.pieChartSize);
        pieChartColor = findViewById(R.id.pieChartColor);

        setupBarChart();
        setupPieChart(pieChartShape, "shape");
        setupPieChart(pieChartSize, "size");
        setupPieChart(pieChartColor, "color");

        setupBottomNav();
    }

    private void setupBarChart() {
        List<PoopLog> logs = dbHelper.getAllPoopLogs();

        int pagi = 0, siang = 0, sore = 0, malam = 0;

        for (PoopLog log : logs) {
            String time = log.getTime();
            int hour = parseHourFromTime(time);

            if (hour >= 5 && hour < 11) pagi++;
            else if (hour >= 11 && hour < 15) siang++;
            else if (hour >= 15 && hour < 18) sore++;
            else malam++;
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, pagi));
        entries.add(new BarEntry(1, siang));
        entries.add(new BarEntry(2, sore));
        entries.add(new BarEntry(3, malam));

        BarDataSet dataSet = new BarDataSet(entries, "Waktu Buang Air");
        dataSet.setColors(Color.parseColor("#FFBB86FC"), Color.parseColor("#FF6200EE"),
                Color.parseColor("#FF03DAC5"), Color.parseColor("#FF3700B3"));

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);
        barChart.setData(barData);

        String[] labels = new String[]{"Pagi", "Siang", "Sore", "Malam"};
        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        barChart.getAxisRight().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }

    private void setupPieChart(PieChart chart, String fieldType) {
        List<PoopLog> logs = dbHelper.getAllPoopLogs();
        Map<String, Integer> map = new HashMap<>();

        for (PoopLog log : logs) {
            String key;
            switch (fieldType) {
                case "shape":
                    key = log.getShape();
                    break;
                case "size":
                    key = log.getSize();
                    break;
                case "color":
                    key = log.getColor();
                    break;
                default:
                    key = "Unknown";
            }
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        if (entries.isEmpty()) {
            entries.add(new PieEntry(1f, "No Data"));
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(Color.parseColor("#A28BD4"), Color.parseColor("#F4A896"),
                Color.parseColor("#C7F2A4"), Color.parseColor("#F7D08A"),
                Color.parseColor("#9AD1D4"), Color.parseColor("#FF8C94"));
        PieData pieData = new PieData(pieDataSet);

        chart.setData(pieData);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTextSize(12f);
        chart.animateY(1000);
        chart.invalidate();
    }

    private int parseHourFromTime(String time) {
        try {
            String[] parts = time.split(" ");
            String[] hm = parts[0].split(":");
            int hour = Integer.parseInt(hm[0]);
            if (parts[1].equalsIgnoreCase("PM") && hour != 12) hour += 12;
            if (parts[1].equalsIgnoreCase("AM") && hour == 12) hour = 0;
            return hour;
        } catch (Exception e) {
            return 0;
        }
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);
        bottomNavigationView.setSelectedItemId(R.id.nav_statistic);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent = null;
            if (item.getItemId() == R.id.nav_calendar) {
                intent = new Intent(StatisticActivity.this, CalendarActivity.class);
            } else if (item.getItemId() == R.id.nav_statistic) {
                return true;
            }

            if (intent != null) {
                startActivity(intent);
                finish();
                return true;
            }
            return false;
        });
    }
}