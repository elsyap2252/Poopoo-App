package com.example.poopoo_app;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Menambahkan titik berwarna di bawah tanggal yang memiliki log poop.
 */
public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;

    /**
     * Konstruktor
     * @param color Warna titik
     * @param dates Set tanggal yang akan diberi titik
     */
    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // Tambahkan titik dan (opsional) ubah warna teks
        view.addSpan(new ForegroundColorSpan(Color.BLACK)); // Ubah warna angka kalau mau
        view.addSpan(new DotSpan(8, Color.parseColor("#018786"))); // Ukuran titik dan warna
    }
}