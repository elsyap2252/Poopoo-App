package com.example.poopoo_app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.Comparator;

public class EditAlarmActivity extends AppCompatActivity {
    private Map<String, TextView> dayMap = new LinkedHashMap<>();
    private Set<String> selectedDays = new LinkedHashSet<>();
    private TextView everyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_alarm);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.editAlarmLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- Inisialisasi NumberPickers ---
        NumberPicker pickerHour = findViewById(R.id.pickerHour);
        NumberPicker pickerMinute = findViewById(R.id.pickerMinute);
        NumberPicker pickerAmPm = findViewById(R.id.pickerAmPm);

        pickerHour.setMinValue(1);
        pickerHour.setMaxValue(12);
        pickerHour.setWrapSelectorWheel(true);

        pickerMinute.setMinValue(0);
        pickerMinute.setMaxValue(59);
        pickerMinute.setFormatter(i -> String.format("%02d", i));
        pickerMinute.setWrapSelectorWheel(true);

        String[] amPmValues = {"AM", "PM"};
        pickerAmPm.setMinValue(0);
        pickerAmPm.setMaxValue(amPmValues.length - 1);
        pickerAmPm.setDisplayedValues(amPmValues);
        pickerAmPm.setWrapSelectorWheel(false);

        // --- Inisialisasi dan Logika Hari ---
        Map<String, TextView> dayMap = new LinkedHashMap<>();
        Set<String> selectedDays = new LinkedHashSet<>();

        dayMap.put("Sun", findViewById(R.id.daySun));
        dayMap.put("Mon", findViewById(R.id.dayMon));
        dayMap.put("Tue", findViewById(R.id.dayTue));
        dayMap.put("Wed", findViewById(R.id.dayWed));
        dayMap.put("Thu", findViewById(R.id.dayThu));
        dayMap.put("Fri", findViewById(R.id.dayFri));
        dayMap.put("Sat", findViewById(R.id.daySat));

        TextView everyTextView = findViewById(R.id.textEveryDay);

        for (Map.Entry<String, TextView> entry : dayMap.entrySet()) {
            TextView dayView = entry.getValue();
            String dayKey = entry.getKey();

            dayView.setOnClickListener(v -> {
                if (selectedDays.contains(dayKey)) {
                    selectedDays.remove(dayKey);
                    dayView.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    selectedDays.add(dayKey);
                    dayView.setBackgroundColor(Color.parseColor("#EBDCF9"));
                }
                updateEveryText(everyTextView, selectedDays);
            });
        }

        // Tombol Save
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("selectedDays", new ArrayList<>(selectedDays));
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Tombol Cancel
        Button cancelButton = findViewById(R.id.buttonCancel);
        cancelButton.setOnClickListener(v -> finish());
    }

    // --- Fungsi untuk update teks "Every ..."
    private void updateEveryText(TextView everyTextView, Set<String> selectedDays) {
        if (selectedDays.isEmpty()) {
            everyTextView.setText("No days selected");
        } else {
            List<String> dayList = new ArrayList<>(selectedDays);
            List<String> ordered = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
            Collections.sort(dayList, new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    return Integer.compare(ordered.indexOf(a), ordered.indexOf(b));
                }
            });
            String text = "Every " + TextUtils.join(", ", dayList);
            everyTextView.setText(text);
        }
    }
}