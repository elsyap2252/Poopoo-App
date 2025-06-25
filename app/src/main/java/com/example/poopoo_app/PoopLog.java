package com.example.poopoo_app;

public class PoopLog {
    private int id;
    private String date;
    private String time;
    private String shape;
    private String color;
    private String size;
    private String notes;

    // Constructor tanpa id (untuk insert baru)
    public PoopLog(String date, String time, String shape, String color, String size, String notes) {
        this.date = date;
        this.time = time;
        this.shape = shape;
        this.color = color;
        this.size = size;
        this.notes = notes;
    }

    // Constructor lengkap (untuk ambil dari database)
    public PoopLog(int id, String date, String time, String shape, String color, String size, String notes) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.shape = shape;
        this.color = color;
        this.size = size;
        this.notes = notes;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
